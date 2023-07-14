package com.rssl.auth.csa.back.integration.mobilebank;

import com.rssl.auth.csa.back.exceptions.IMSICheckException;
import com.rssl.auth.csa.back.exceptions.MobileBankRegistrationNotFoundException;
import com.rssl.auth.csa.back.exceptions.MobileBankSendSmsMessageException;
import com.rssl.phizgate.mobilebank.MobileBankConfig;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mobilebank.*;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.source.JDBCActionExecutor;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.PhoneNumberUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.mobilebank.GetCardNumberByPhoneJDBCAction;
import com.rssl.phizicgate.mobilebank.MobileBankIMSIHelper;
import com.rssl.phizicgate.mobilebank.MobileBankRegistrationHelper;
import com.rssl.phizicgate.mobilebank.SendSmsJDBCAction;
import com.rssl.phizicgate.mobilebank.csa.ChangePassByCardNumberForErmbClientJDBCAction;
import com.rssl.phizicgate.mobilebank.csa.ChangePassByCardNumberJDBCAction;
import com.rssl.phizicgate.mobilebank.csa.GetClientByCardNumberJDBCAction;
import com.rssl.phizicgate.mobilebank.csa.GetClientByLoginJDBCAction;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * @author krenev
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class MobileBankService
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	private final JDBCActionExecutor executor = new JDBCActionExecutor("jdbc/MobileBank", System.jdbc);
	private final JDBCActionExecutor executor2 = new JDBCActionExecutor("jdbc/MobileBank2", System.jdbc);
	private static final MobileBankService INSTANCE = new MobileBankService();
	private static final Random random = new Random();

	private MobileBankRegistrationHelper registrationHelper;
	private MobileBankIMSIHelper imsiHelper;

	private MobileBankService()
	{
		registrationHelper = new MobileBankRegistrationHelper(executor);
		imsiHelper = new MobileBankIMSIHelper(executor);
	}

	/**
	 * @return ������� ������� ������� � ��.
	 */
	public static MobileBankService getInstance()
	{
		return INSTANCE;
	}

	/**
	 * �������� ���������� � ������������ �� ������ �����
	 *
	 * @param cardNumber ����� �����
	 * @return ���������� � ������������ ��� null, ���� ������������ �� ������
	 * @throws SystemException
	 */
	public UserInfo getClientByCardNumber(String cardNumber) throws SystemException, GateLogicException
	{
		return (ConfigFactory.getConfig(MobileBankConfig.class).isUseMobilebankAsApp()) ?
				GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class).getClientByCardNumber(cardNumber) :
				executor2.execute(new GetClientByCardNumberJDBCAction(cardNumber));
	}

	/**
	 * �������� ����� ����� �� ��������
	 * @param phoneNumber ����� ��������
	 * @return ����� �����
	 * @throws SystemException
	 */
	public String getCardByPhone(String phoneNumber) throws SystemException, GateLogicException
	{
		if (ConfigFactory.getConfig(MobileBankConfig.class).isUseMobilebankAsApp())
			return GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class).getCardNumberByPhone(null, phoneNumber);
		else
		{
			if (StringHelper.isEmpty(phoneNumber))
			{
				throw new IllegalArgumentException("����� �������� �� ����� ���� null");
			}
			return executor.execute(new GetCardNumberByPhoneJDBCAction(phoneNumber, null, null));
		}
}

	/**
	 * �������� ���������� � ������������ �� ������ iPas
	 *
	 * @param userId ����� iPas
	 * @return ���������� � ������������ ��� null, ���� ������������ �� ������
	 * @throws SystemException
	 */
	public UserInfo getClientByLogin(String userId) throws SystemException, GateLogicException
	{
		return (ConfigFactory.getConfig(MobileBankConfig.class).isUseMobilebankAsApp()) ?
				GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class).getClientByLogin(userId) :
				executor2.execute(new GetClientByLoginJDBCAction(userId));
	}

	/**
	 * @deprecated
	 * ������������� � ������� ������ �� ����� � iPas
	 * @param cardNumber ����� �����
	 * @return ������� ��� ���
	 * @throws SystemException
	 */
	public Boolean generatePassword(String cardNumber) throws SystemException
	{
		return executor.execute(new ChangePassByCardNumberJDBCAction(cardNumber));
	}

	/**
	 * @deprecated
	 * ������������� � ������� ������ �� ����� � iPas ��� ����-�������
	 * @param cardNumber ����� �����
	 * @param phoneNumber ����� ��������
	 * @return ������� ��� ���
	 * @throws SystemException
	 */
	public Boolean generatePasswordForErmbClient(String cardNumber,  String  phoneNumber) throws SystemException
	{
		return executor.execute(new ChangePassByCardNumberForErmbClientJDBCAction(cardNumber, phoneNumber));
	}

	/**
	 * �� ������������ ����� ��������, �������� ��������� ��������������� ����� SendMessageRouter
	 *
	 * ��������� ��� �� ������ �����, ������������������ � ��
	 * @param sendMessageInfo ���������� � ���������� ���������
	 * @throws Exception
	 */
	void sendSMSByCardNumber(SendMessageInfo sendMessageInfo, Set<String> excludedPhones) throws Exception
	{
		Set<String> phones = null;
		List<String> cardNumbers = new ArrayList<String>();
		cardNumbers.add(sendMessageInfo.getCardNumber());
		if (ConfigFactory.getConfig(MobileBankConfig.class).isUseMobilebankAsApp())
		{
			phones = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class).getRegPhonesByCardNumbers(cardNumbers, sendMessageInfo.getMode());
		}
		else
		{
			phones = registrationHelper.getRegPhonesByCardNumbers(cardNumbers, sendMessageInfo.getMode());
		}

		if (CollectionUtils.isEmpty(phones))
			throw new MobileBankRegistrationNotFoundException("�� ������� �� ������ ��������");
		if (excludedPhones != null && !excludedPhones.isEmpty())
		{
			Iterator<String> iterator = phones.iterator();
			while (iterator.hasNext())
			{
				if (excludedPhones.contains(iterator.next()))
					iterator.remove();
			}
		}
		// ���������� ���'��
		sendSmsByPhones(phones, sendMessageInfo.getMessageInfo(), sendMessageInfo.isCheckIMSI());
	}

	/**
	 * �������� ������ ������� ��������� �� ������ �����
	 * @param clientCardNumber ����� �����
	 * @param mode ����� ��������� �����������
	 * @return ������ ������� ��������� �� ������ �����
	 */
	public Collection<String> getClientPhonesByCard(String clientCardNumber, GetRegistrationMode mode) throws GateLogicException, GateException
	{
		try
		{
			List<String> cardNumbers = new ArrayList<String>();
			cardNumbers.add(clientCardNumber);
			return (ConfigFactory.getConfig(MobileBankConfig.class).isUseMobilebankAsApp()) ?
					GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class).getRegPhonesByCardNumbers(cardNumbers, mode) :
					registrationHelper.getRegPhonesByCardNumbers(cardNumbers, mode);
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * ��������� ����������� ����� � �� ��. ������������ �������� ��������� getRegistrations3
	 * @param cardNumber ����� �����
	 * @return ������ ����������� �����
	 */
	public List<MobileBankRegistration3> getRegistrations3(String cardNumber) throws GateLogicException, GateException
	{
		if ((ConfigFactory.getConfig(MobileBankConfig.class).isUseMobilebankAsApp()))
			return GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class).getRegistrations3(cardNumber);
		else
		{
			try
			{
				return registrationHelper.getRegistrations3(cardNumber);
			}
			catch (SystemException e)
			{
				log.error("������ ��� ��������� ������ ����������� �� �����: " + cardNumber, e);
				return Collections.emptyList();
			}
		}
	}

	/**
	 * ��������� ��������� �� ��������� ���������
	 * @param phones ��������� ���������
	 * @param messageInfo ���������� � ���������
	 * @param isCheckIMSI true - � ��������� IMSI
	 * @throws MobileBankSendSmsMessageException
	 */
	public void sendSmsByPhones(Set<String> phones, MessageInfo messageInfo, boolean isCheckIMSI) throws MobileBankSendSmsMessageException, IMSICheckException
	{
		// ��������� ��������������� ����, � ����������� �� ����, ����� �� �������� imsi.
		Collection<String> errorPhones = new HashSet<String>();
		if(isCheckIMSI)
		{
			Map<String, SendMessageError> errors = new HashMap<String, SendMessageError>();
			if (ConfigFactory.getConfig(MobileBankConfig.class).isUseMobilebankAsApp())
				try
				{
					errors = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class).sendSMSWithIMSICheck(messageInfo, phones.toArray(new String[phones.size()]));
				}
				catch (GateException e)
				{
					throw new MobileBankSendSmsMessageException(e.getMessage());
				}
				catch (GateLogicException e)
				{
					throw new MobileBankSendSmsMessageException(e.getMessage());
				}
			else
			{
				String mbSystemId = ConfigFactory.getConfig(MobileBankConfig.class).getMbSystemId();
				errors = imsiHelper.sendSmsWithIMSICheck(messageInfo, mbSystemId, phones.toArray(new String[phones.size()]));
			}

			errorPhones.addAll(errors.keySet());
			if (errorPhones.size() == phones.size())
			{
				int imsiCheckError = 0;
				for (SendMessageError sendMessageError : errors.values())
				{
					if (SendMessageError.imsi_error == sendMessageError)
						imsiCheckError++;
				}
				if (phones.size() == imsiCheckError)
					throw new IMSICheckException("��� �������� SMS ���������� ����� SIM-�����", phones);
				else
					throw new MobileBankSendSmsMessageException("�� ������� ��������� ��� �� �� ���� �� ������������������ �������", errorPhones);
			}
		}
		else
		{
			// ���������� ���'��
			for(String phone : phones)
			{
				try
				{
					if (ConfigFactory.getConfig(MobileBankConfig.class).isUseMobilebankAsApp())
						GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class).sendSMS(messageInfo.getText(), messageInfo.getTextToLog(), Math.abs(random.nextInt()), phone);
					else
					{
						// �� �������������� � ���������� ��������� �������������
						String phoneMB = PhoneNumberFormat.MOBILE_INTERANTIONAL.translate(phone);
						String mbSystemId = ConfigFactory.getConfig(MobileBankConfig.class).getMbSystemId();
						executor2.execute(new SendSmsJDBCAction(phoneMB, messageInfo.getText(), messageInfo.getTextToLog(), Math.abs(random.nextInt()), mbSystemId));
					}
				}
				catch(Exception e)
				{
					log.error("�� �������� ��������� ��� �� ����� " + PhoneNumberUtil.getCutPhoneNumber(phone), e);
					errorPhones.add(phone);
				}
			}
			if (errorPhones.size() == phones.size())
				throw new MobileBankSendSmsMessageException("�� ������� ��������� ��� �� �� ���� �� ������������������ �������", errorPhones);
		}

	}

	/**
	 * �������� ������ ��������� ��� �������� sms ��������� � ������ ������� � push ����������
	 * @param sendMessageInfo ���������� � ���������
	 * @return
	 * @throws MobileBankRegistrationNotFoundException
	 */
	public List<String> getPhones(SendMessageInfo sendMessageInfo) throws MobileBankRegistrationNotFoundException, GateLogicException, GateException
	{
		Set<String> phones = null;
		List<String> cardNumbers = new ArrayList<String>();
		cardNumbers.add(sendMessageInfo.getCardNumber());
		if (ConfigFactory.getConfig(MobileBankConfig.class).isUseMobilebankAsApp())
			phones = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class).getRegPhonesByCardNumbers(cardNumbers, sendMessageInfo.getMode());
		else
			try
			{
				phones = registrationHelper.getRegPhonesByCardNumbers(cardNumbers, sendMessageInfo.getMode());
			}
			catch (SystemException e)
			{
				throw new GateException(e);
			}
		return new ArrayList(phones);
	}
}
