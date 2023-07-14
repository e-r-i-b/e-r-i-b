package com.rssl.phizic.gate.messaging;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.configuration.MessagingConfig;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;

/**
 * @author Roshka
 * @ created 23.10.2006
 * @ $Author$
 * @ $Revision$
 */

public class MessageInfoParser
{
	private final MessagingConfig messagingConfig;

	public MessageInfoParser(MessagingConfig messagingConfig)
	{
		this.messagingConfig = messagingConfig;
	}

	/**
	 * Получение информации о сообщении
	 * @param message сообщение
	 * @return
	 * @throws GateException
	 */
	public MessageInfo parse(Document message) throws GateException
	{
		Element documentElement = message.getDocumentElement();
		String requestType = documentElement.getNodeName();

		MessageInfo messageInfo = messagingConfig.getMessageInfo(requestType);

		if (messageInfo == null)
			throw new GateException("Не найдено описание для - " + requestType);

		return messageInfo;
	}

	/**
	 * Получение типа запроса
	 * @param message сообщение
	 * @return
	 */
	public String getRequestType(Document message)
	{
		Element documentElement = message.getDocumentElement();
		return documentElement.getNodeName();
	}
}