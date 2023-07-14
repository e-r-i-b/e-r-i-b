package com.rssl.phizic.business.dictionaries.pages.staticmessages;

/**
 * Статические сообщения
 * @author gladishev
 * @ created 22.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class StaticMessage
{
	private String key; //ключ для поиска сообщения.
	private String text; //текст.

	public StaticMessage()
	{
	}

	public StaticMessage(String key, String text)
	{
		this.key = key;
		this.text = text;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}
}
