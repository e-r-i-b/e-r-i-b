package com.rssl.phizic.business.ermb.sms.messaging;

import com.rssl.phizic.messaging.MessagingSingleton;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Gulov
 * @ created 25.06.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Утилиты для отправки смс сообщений
 */
public final class SmsSenderUtils
{
	private static final String SEND_ERROR = "Ошибка отправки сообщения:\n \"%s\" \nпо телефону %s";

	private SmsSenderUtils()
	{
	}

	/**
	 * Отправить уведомление клиенту
	 * @param phone телефон
	 * @param message сообщение
	 */
	public static void notifyClientMessage(String phone, TextMessage message)
	{
		if (message != null)
			notifyClientMessage(phone, message.getText(), message.getTextToLog(), message.getPriority());
	}

	/**
	 * Отправить уведомление клиенту
	 * @param phone телефон
	 * @param text сообщение
	 */
	public static void notifyClientMessage(String phone, String text)
	{
		notifyClientMessage(phone, text, text, TextMessage.DEFAULT_PRIORITY);
	}

	static void notifyClientMessage(PhoneNumber phone, TextMessage message)
	{
		notifyClientMessage(phoneToString(phone), message.getText(), message.getTextToLog(), message.getPriority());
	}

	private static void notifyClientMessage(String phone, String text, String textToLog, Long priority)
	{
		if (phone == null)
			throw new IllegalArgumentException("Не указан номер телефона");
		if (StringHelper.isEmpty(text))
			throw new IllegalArgumentException("Не указано сообщение для отправки");
		try
		{
			MessagingSingleton.getInstance().getErmbSmsTransportService().sendSms(phone, text, textToLog, priority);
		}
		catch (IKFLMessagingException e)
		{
			throw new InternalErrorException(String.format(SEND_ERROR, text, phone), e);
		}
	}

	private static String phoneToString(PhoneNumber phone)
	{
		return PhoneNumberFormat.MOBILE_INTERANTIONAL.format(phone);
	}
}
