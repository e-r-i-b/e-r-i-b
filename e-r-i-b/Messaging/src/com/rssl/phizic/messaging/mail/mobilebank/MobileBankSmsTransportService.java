package com.rssl.phizic.messaging.mail.mobilebank;

import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.common.counters.CounterService;
import com.rssl.phizic.dataaccess.common.counters.Counters;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mobilebank.MessageInfo;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.gate.mobilebank.SendMessageError;
import com.rssl.phizic.messaging.TranslitMode;
import com.rssl.phizic.messaging.mail.SmsMessage;
import com.rssl.phizic.messaging.mail.SmsTransportService;
import com.rssl.phizic.messaging.mobilebank.MobileBankSmsConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.StringUtils;

import java.util.Map;

/**
 * @author Erkin
 * @ created 08.05.2010
 * @ $Author$
 * @ $Revision$
 * @deprecated ���������� �� ���
 */
@Deprecated
//todo CHG059738 �������
public class MobileBankSmsTransportService implements SmsTransportService
{
	private static final CounterService counterService = new CounterService();

	/**
	 * ������������ ����� ���������, ����������� ��
	 */
	private static final int MESSAGE_MAX_LENGTH = 160;

	///////////////////////////////////////////////////////////////////////////

	public void sendSms(String toPhone, String text, String textToLog,Long priority)
			throws IKFLMessagingException
	{
		if (StringHelper.isEmpty(toPhone))
			throw new IllegalArgumentException("�������� 'toPhone' �� ����� ���� ������");
		if (StringHelper.isEmpty(text))
			throw new IllegalArgumentException("�������� 'text' �� ����� ���� ������");

		MobileBankSmsConfig mobileBankSmsConfig = ConfigFactory.getConfig(MobileBankSmsConfig.class);

		if (mobileBankSmsConfig.isSmsTranslit())
			text = StringUtils.translit(text);

		try
		{
			MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);

			if (mobileBankSmsConfig.isSmsSplit())
				for (String chunk : splitToLines(text))
					mobileBankService.sendSMS(chunk, textToLog, generateId(), toPhone);
			else
				mobileBankService.sendSMS(text, textToLog, generateId(), toPhone);
		}
		catch (GateException ex)
		{
			throw new IKFLMessagingException(ex.getMessage(), ex);
		}
		catch (GateLogicException e)
		{
			throw new IKFLMessagingException(e.getMessage(), e);
		}
	}

	private String[] splitToLines(String text) throws GateException, IKFLMessagingException
	{
		MobileBankSmsConfig mobileBankSmsConfig = ConfigFactory.getConfig(MobileBankSmsConfig.class);

		String wordOf = mobileBankSmsConfig.getWordOf();
		int lineMaxLength = MESSAGE_MAX_LENGTH - wordOf.length() - 6;//6 �������� �� �����, ������� � �����
		String chunks[] = StringHelper.splitToLines(text, lineMaxLength, true);

		if(chunks.length > 1)
			for (int i = 0; i < chunks.length; i++)
				chunks[i] = (i + 1) + " " + wordOf + " " + chunks.length + ". " + chunks[i];
		return chunks;
	}

	public void sendSms(String toPhone, TranslitMode translit, String text, String textToLog, Long priority) throws IKFLMessagingException
	{
		if (StringHelper.isEmpty(toPhone))
			throw new IllegalArgumentException("�������� 'toPhone' �� ����� ���� ������");
		if (translit == null)
			throw new NullPointerException("�������� 'translit' �� ����� ���� null");
		if (StringHelper.isEmpty(text))
			throw new IllegalArgumentException("�������� 'text' �� ����� ���� ������");

		// ������ � �� �� ��������������, ������� ������ ��������������
//		if (translit.equalsIgnoreCase("TRANSLIT"))
//			text = translit(text);
		MobileBankSmsConfig mobileBankSmsConfig = ConfigFactory.getConfig(MobileBankSmsConfig.class);
		if (mobileBankSmsConfig.isSmsTranslit())
			text = StringUtils.translit(text);

		try
		{
			MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
			
			if (mobileBankSmsConfig.isSmsSplit())
				for (String chunk : splitToLines(text))
					mobileBankService.sendSMS(chunk, textToLog, generateId(), toPhone);
			else
				mobileBankService.sendSMS(text, textToLog, generateId(), toPhone);
		}
		catch (GateException ex)
		{
			throw new IKFLMessagingException(ex.getMessage(), ex);
		}
		catch (GateLogicException e)
		{
			throw new IKFLMessagingException(e.getMessage(), e);
		}
	}

	public Map<String, SendMessageError> sendSmsWithIMSICheck(MessageInfo messageInfo, Long priority, String... toPhones) throws IKFLMessagingException
	{
		MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
		try
		{
			return mobileBankService.sendSMSWithIMSICheck(messageInfo, toPhones);
		}
		catch (GateException ex)
		{
			throw new IKFLMessagingException(ex.getMessage(), ex);
		}
		catch (GateLogicException e)
		{
			throw new IKFLMessagingException(e.getMessage(), e);
		}
	}

	public SmsMessage receive()
	{
		throw new UnsupportedOperationException("�������� '��������� SMS' " +
				"�� �������������� � �� '��'");
	}

	private int generateId() throws IKFLMessagingException
	{
		try
		{
			Long id = counterService.getNext(Counters.MB_SMS_ID);
			id %= Integer.MAX_VALUE;
			return id.intValue();
		}
		catch (Exception ex)
		{
			throw new IKFLMessagingException(ex.getMessage(), ex);
		}
	}
}
