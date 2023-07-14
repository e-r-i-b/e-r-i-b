package com.rssl.phizic.messaging;

/**
 * формат отправки сообщений клиенту на электронную почту
 * ¬озможны два варианта: обычный текст или html
 *
 * @author tisov
 * @ created 11.10.13
 * @ $Author$
 * @ $Revision$
 */

public enum MailFormat
{
	/**
	 * отправл€ть сообщени€ обычным текстам - значение по умолчанию
	 */
	PLAIN_TEXT("plainText", "“екст"),

	/**
	 * отправл€ть сообщени€ в формате html шаблона
	 */
	HTML("htmlText", "HTML-письмо");

	private String key;
	private String description;

	private MailFormat(String key, String description)
	{
		this.key = key;
		this.description = description;
	}

	public String getKey()
	{
		return key;
	}

	public String getDescription()
	{
		return description;
	}
}
