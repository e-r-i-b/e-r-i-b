package com.rssl.phizic.common.types;

import com.rssl.phizic.common.types.annotation.Immutable;
import org.apache.commons.lang.StringUtils;

/**
 * @author Erkin
 * @ created 13.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Текстовка
 */
@Immutable
public class TextMessage
{
	private final String text;

	private final String textToLog;

	private final Long priority;     //приоритет отправки сообщения

	public static final Long DEFAULT_PRIORITY = Long.valueOf(0); //значение приоритета по умолчанию

	///////////////////////////////////////////////////////////////////////////

	public TextMessage()
	{
		this("");
	}
	/**
	 * ctor
	 * @param text - текст сообщения
	 */
	public TextMessage(String text)
	{
		this(text, text, DEFAULT_PRIORITY);
	}

	/**
	 * ctor
	 * @param text - текст сообщения
	 * @param textToLog - текст сообщения для логирования
	 */
	public TextMessage(String text, String textToLog)
	{
		this(text,textToLog,DEFAULT_PRIORITY);
	}

	public TextMessage(String text, String textToLog, Long priority)
	{
		if (StringUtils.isEmpty(text))
			throw new IllegalArgumentException("Аргумент 'text' не может быть пустым");
		this.text = text;
		this.textToLog = textToLog;
		if (priority==null)
		{
			this.priority=DEFAULT_PRIORITY;
		}
		else
		{
			this.priority = priority;
		}
	}

	/**
	 * @return текст сообщения
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * @return текст сообщения для логирования
	 */
	public String getTextToLog()
	{
		return textToLog;
	}

	public Long getPriority()
	{
		return priority;
	}
	@Override
	public int hashCode()
	{
		return text.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
						return false;

		TextMessage that = (TextMessage) o;

		return text.equals(that.text);
	}

	@Override
	public String toString()
	{
		return getTextToLog();
	}
}
