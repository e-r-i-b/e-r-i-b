package com.rssl.phizic.messaging;

import com.rssl.phizic.common.types.TextMessage;

/**
 * @author Erkin
 * @ created 14.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Персональный СМС-мессенжер клиента
 */
public interface PersonSmsMessanger
{
	/**
	 * Отправляет СМС клиенту
	 * @param message - текст СМС
	 */
	void sendSms(TextMessage message);

	/**
	 * Отправка СМС клиенту по номеру телефона
	 * @param message - текст смс
	 * @param phoneNumber - номер телефона
	 */
	void sendSms(TextMessage message, String phoneNumber);

	/**
	 * @return номер по умолчанию, на который отправляются сообщения
	 */
	String getDefaultPhone();
}
