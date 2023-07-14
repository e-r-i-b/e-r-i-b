package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.logging.source.JDBCActionExecutor;
import com.rssl.phizic.gate.mobilebank.ImsiCheckResult;
import com.rssl.phizic.gate.mobilebank.MessageInfo;
import com.rssl.phizic.gate.mobilebank.SendMessageError;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

import static com.rssl.phizgate.mobilebank.GatePhoneHelper.hidePhoneNumber;
import static com.rssl.phizicgate.mobilebank.MBKConstants.MBK_PHONE_NUMBER_FORMAT;

/**
 * @author niculichev
 * @ created 27.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class MobileBankIMSIHelper
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private static final int BEFORE_IMSI_CHECK_FIRST_DELAY  = 3000; //���������� �� ��� ������ ����� ����� ��������� IMSI
	private static final int BEFORE_IMSI_CHECK_DELAY        = 1000; //���������� �� ��� ����������� ���� ����� ��������� IMSI
	private static final int COUNT_ITERATE_IMSI_CHECK       = 10 + 1; //���������� ������� ���������� �������� IMSI

	private JDBCActionExecutor executor;

	public MobileBankIMSIHelper(JDBCActionExecutor executor)
	{
		this.executor = executor;
	}

	/**
	 * �������� ��� � ��������� IMSI
	 * @param messageInfo ���������� � ���������
	 * @param phones ������ ��������� �� ������� ������������ ���������
	 * @return ��� ������(������� - ������)
	 */
	public Map<String, SendMessageError> sendSmsWithIMSICheck(MessageInfo messageInfo,String mbSystemId, String... phones)
	{
		if (ArrayUtils.isEmpty(phones))
			throw new IllegalArgumentException("�������� 'toPhone' �� ����� ���� ������");

		if (StringHelper.isEmpty(messageInfo.getText()) || StringUtils.isBlank(messageInfo.getText()))
			throw new IllegalArgumentException("�������� 'text' �� ����� ���� ������ ��� blank");

		// ����� �������� - ��� ������
		Map<String, SendMessageError> errorPhones = new HashMap<String, SendMessageError>();

		//messageId ������������ ���������
		Map<Integer, String> messageIds = new HashMap<Integer, String>();

		//1. ���������� �� ��� �������� ���
		for (String phone : phones)
		{
			String phoneMB = MBK_PHONE_NUMBER_FORMAT.translate(phone);

			try
			{
				String hidePhone = hidePhoneNumber(phone);
				log.trace("�������� ��� � ��������� IMSI ��� ������ �������� '" + hidePhone + "' ...");

				Integer messId = executor.execute(new SendSmsWithIMSICheckJDBCAction(phoneMB, messageInfo, mbSystemId));
				messageIds.put(messId, phone);

				log.trace("��� � ��������� IMSI ��� ������ �������� " + hidePhone + " ���������� � ��������� ����");
			}
			catch (SystemException e)
			{
				log.error(e.getMessage(), e);
				errorPhones.put(phone, SendMessageError.error);
			}
		}

		// �� ���������� ��������� �� �� ���� �����
		if (messageIds.isEmpty())
			return errorPhones;

		//2. �������� �������� ���������� �������� IMSI ����� ��������� �����
		sleep(BEFORE_IMSI_CHECK_FIRST_DELAY);

		//3. ��������� �������� IMSI
		errorPhones.putAll(checkIMSI(messageIds));

		return errorPhones;
	}

	private Map<String, SendMessageError> checkIMSI(Map<Integer, String> messageIds)
	{
		// ����� �������� - ��� ������
		Map<String, SendMessageError> errorPhones = new HashMap<String, SendMessageError>();

		// ��������� ������� ��� ���������
		Map<Integer, ImsiCheckResult> checkResults = new HashMap<Integer, ImsiCheckResult>();

		// ��������� ������� ��� �� ���������
		List<Integer> notCheckedMessages = new ArrayList<Integer>(messageIds.keySet());

		int checkNumber = 0;

		// ���������� � ���������� �����, ����� ��������� ��� ���������� � ����� ���������
		do
		{
			List<Integer> messIdsTmp = new ArrayList<Integer>(notCheckedMessages);
			for (Integer messageId : messIdsTmp)
			{
				ImsiCheckResult result = null;

				try
				{
					log.trace("�������� ��������� �������� IMSI ��� ��������� � ID='" + messageId + "' ...");

					String phoneMB = MBK_PHONE_NUMBER_FORMAT.translate(messageIds.get(messageId));
					result = executor.execute(
							new GetCheckIMSIResultJDBCAction(messageId, phoneMB));

					log.trace("��������� �������� IMSI ��� ��������� � ID=" + messageId + " ������� ��������: " + result.getDescription());
				}
				catch (SystemException e)
				{
					log.error(e.getMessage(), e);
					errorPhones.put(messageIds.get(messageId), SendMessageError.error);
				}

				if (result != ImsiCheckResult.yet_not_send)
				{
					checkResults.put(messageId, result);
					notCheckedMessages.remove(messageId);
				}
			}

			if (++checkNumber >= COUNT_ITERATE_IMSI_CHECK)
				break;

			if (!notCheckedMessages.isEmpty()) //������ ����� ����� ��������� ���������
			{
				sleep(BEFORE_IMSI_CHECK_DELAY);
			}

		} while (!notCheckedMessages.isEmpty());


		// ������� ��� ��������� ����������, ������������ ���������, ������� ������ ��������� ����
		List<String> corruptIMSIPhones = new ArrayList<String>();
		List<String> clientErrorPhones = new ArrayList<String>();

		for (Integer messageId : checkResults.keySet())
		{
			ImsiCheckResult res = checkResults.get(messageId);
			if (res == ImsiCheckResult.imsi_error)
				corruptIMSIPhones.add(messageIds.get(messageId));

			if (res == ImsiCheckResult.client_error)
				clientErrorPhones.add(messageIds.get(messageId));
		}

		//�) ���� �� ��� �������� ������� ��������� ��������� - �� �������, ������ ������ ������ �� ����
		if (corruptIMSIPhones.isEmpty() && notCheckedMessages.isEmpty() && errorPhones.isEmpty() && clientErrorPhones.isEmpty())
			return Collections.emptyMap();

		for (String phone : corruptIMSIPhones)
			errorPhones.put(phone, SendMessageError.imsi_error);

		for (String phone : clientErrorPhones)
			errorPhones.put(phone, SendMessageError.error);

		for (Integer id : notCheckedMessages)
			errorPhones.put(messageIds.get(id), SendMessageError.error);

		return errorPhones;
	}

	private void sleep(int delay)
	{
		try
		{
			Thread.sleep(delay);
		}
		catch (InterruptedException e)
		{
			//������ ���������
		}
	}

	/**
	 * �������� ������� �������� IMSI ��� �������� ���
	 * @param phones ������ ��������� �� ������� ������������ ���������
	 * @return ��� ������(������� - ������)
	 */
	public Map<String, SendMessageError> sendIMSICheck(String mbSystemId, String... phones)
	{
		if (ArrayUtils.isEmpty(phones))
			throw new IllegalArgumentException("�������� 'toPhone' �� ����� ���� ������");

		// ����� �������� - ��� ������
		Map<String, SendMessageError> errorPhones = new HashMap<String, SendMessageError>();

		//messageId ������������ ���������
		Map<Integer, String> messageIds = new HashMap<Integer, String>();

		//1. ���������� �� ��� �������� ���
		for (String phone : phones)
		{
			String phoneMB = MBK_PHONE_NUMBER_FORMAT.translate(phone);

			try
			{
				String hidePhone = hidePhoneNumber(phone);
				log.trace("�������� ������� �������� IMSI ��� ������ ��������" + hidePhone);

				Integer messId = executor.execute(new CheckIMSIJDBCAction(phoneMB, mbSystemId));
				messageIds.put(messId, phone);

				log.trace("������� �������� IMSI ��� ������ �������� " + hidePhone + " ��������� � ��������� ����");
			}
			catch (SystemException e)
			{
				log.error(e.getMessage(), e);
				errorPhones.put(phone, SendMessageError.error);
			}
		}

		// �� ���������� ��������� �� �� ���� �����
		if (messageIds.isEmpty())
			return errorPhones;

		//2. �������� �������� ���������� �������� IMSI ����� ��������� �����
		sleep(BEFORE_IMSI_CHECK_FIRST_DELAY);

		//3. ��������� �������� IMSI
		errorPhones.putAll(checkIMSIWithoutSms(messageIds));

		return errorPhones;
	}

	private Map<String, SendMessageError> checkIMSIWithoutSms(Map<Integer, String> messageIds)
	{
		// ����� �������� - ��� ������
		Map<String, SendMessageError> errorPhones = new HashMap<String, SendMessageError>();

		// ��������� ������� ��� ���������
		Map<Integer, ImsiCheckResult> checkResults = new HashMap<Integer, ImsiCheckResult>();

		// ��������� ������� ��� �� ���������
		List<Integer> notCheckedMessages = new ArrayList<Integer>(messageIds.keySet());

		int checkNumber = 0;

		// ���������� � ���������� �����, ����� ��������� ��� ���������� � ����� ���������
		do
		{
			List<Integer> messIdsTmp = new ArrayList<Integer>(notCheckedMessages);
			for (Integer messageId : messIdsTmp)
			{
				ImsiCheckResult result = null;

				try
				{
					log.trace("�������� ��������� �������� IMSI ��� ��������� � ID='" + messageId + "' ...");

					String phoneMB = MBK_PHONE_NUMBER_FORMAT.translate(messageIds.get(messageId));
					result = executor.execute(
							new GetCheckIMSIResultExJDBCAction(messageId, phoneMB));

					log.trace("��������� �������� IMSI ��� ��������� � ID=" + messageId + " ������� ��������: " + result.getDescription());
				}
				catch (SystemException e)
				{
					log.error(e.getMessage(), e);
					errorPhones.put(messageIds.get(messageId), SendMessageError.error);
				}

				if (result != ImsiCheckResult.yet_not_check)
				{
					checkResults.put(messageId, result);
					notCheckedMessages.remove(messageId);
				}
			}

			if (++checkNumber >= COUNT_ITERATE_IMSI_CHECK)
				break;

			if (!notCheckedMessages.isEmpty()) //������ ����� ����� ��������� ���������
			{
				sleep(BEFORE_IMSI_CHECK_DELAY);
			}
		}
		while (!notCheckedMessages.isEmpty());

		// ������� ��� ��������� ����������, ������������ ���������, ������� ������ ��������� ����
		List<String> corruptIMSIPhones = new ArrayList<String>();
		List<String> clientErrorPhones = new ArrayList<String>();

		for (Integer messageId : checkResults.keySet())
		{
			ImsiCheckResult res = checkResults.get(messageId);
			if (res == ImsiCheckResult.imsi_fail)
				corruptIMSIPhones.add(messageIds.get(messageId));

			if (res == ImsiCheckResult.client_error)
				clientErrorPhones.add(messageIds.get(messageId));
		}

		//�) ���� �� ��� �������� ������� ��������� ��������� - �� �������, ������ ������ ������ �� ����
		if (corruptIMSIPhones.isEmpty() && notCheckedMessages.isEmpty() && errorPhones.isEmpty() && clientErrorPhones.isEmpty())
			return Collections.emptyMap();

		for (String phone : corruptIMSIPhones)
			errorPhones.put(phone, SendMessageError.imsi_error);

		for (String phone : clientErrorPhones)
			errorPhones.put(phone, SendMessageError.error);

		for (Integer id : notCheckedMessages)
			errorPhones.put(messageIds.get(id), SendMessageError.error);

		return errorPhones;
	}
}

