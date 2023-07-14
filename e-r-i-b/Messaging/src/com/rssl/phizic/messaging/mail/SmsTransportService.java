package com.rssl.phizic.messaging.mail;

import com.rssl.phizic.gate.mobilebank.SendMessageError;
import com.rssl.phizic.gate.mobilebank.MessageInfo;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.messaging.TranslitMode;

import java.util.Map;

/**
 * @author Erkin
 * @ created 08.05.2010
 * @ $Author$
 * @ $Revision$
 */
public interface SmsTransportService
{
	/**
	 * на телефон toPhone посылаем сообщение text
	 * @param toPhone
	 * @param text
	 * @param textToLog
	 * @throws IKFLMessagingException
	 */
	void sendSms(String toPhone, String text, String textToLog, Long priority) throws IKFLMessagingException;

	/**
	 * Отправка короткого текстового сообщения на телефон
	 * @param toPhone - номер телефона
	 * @param translit - режим транслитерации
	 * @param text - текст сообщения
	 * @param textToLog - текст для логирования
	 * @throws IKFLMessagingException
	 */
	void sendSms(String toPhone, TranslitMode translit, String text,String textToLog, Long priority) throws IKFLMessagingException;

	/**
	 * Отправка смс с проверкой IMSI на номера toPhones
	 * @param messageInfo информация о сообщении
	 * @param mbSystemId ID ИКФЛ в АС "Мобильный Банк"
	 * @param toPhones телефоны
	 * @return мап (номер телефона - ошибка)
	 */
	Map<String, SendMessageError> sendSmsWithIMSICheck(MessageInfo messageInfo, Long priority, String... toPhones) throws IKFLMessagingException;

	/**
	 * получение смс-сообщения, причем само сообщение удаляется из базы
	 * @return возвращает SmsMessage
	 * @throws IKFLMessagingException
	 */
	SmsMessage receive() throws IKFLMessagingException;
}
