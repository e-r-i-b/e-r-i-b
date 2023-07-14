package com.rssl.phizic.gate.monitoring.locale;

import com.rssl.phizic.locale.dynamic.resources.LanguageResource;

import java.io.Serializable;

/**
 * Сущность для хранения многоязычных текстовок
 * @author komarov
 * @ created 29.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class MonitoringServiceGateStateConfigResources extends LanguageResource implements Serializable
{
	private String messageText;

	/**
	 * @return шаблон сообщения клиенту
	 */
	public String getMessageText()
	{
		return messageText;
	}
	/**
	 * задаёт шаблон сообщения клиенту
	 * @param messageText шаблон сообщения клиенту
	 */
	public void setMessageText(String messageText)
	{
		this.messageText = messageText;
	}
}
