package com.rssl.phizic.business.exception.locale;

/**
 * @author akrenev
 * @ created 31.10.2014
 * @ $Author$
 * @ $Revision$
 *
 * Локалезависимые маппируемые сообщения
 */

public class LocalizingExceptionMapping
{
	private Long id;
	private String messageKey;
	private String errorKey;
	private String pattern;
	private String formatter;

	/**
	 * @return идентификатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * задать идентификатор
	 * @param id идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ключ сообщения
	 */
	public String getMessageKey()
	{
		return messageKey;
	}

	/**
	 * задать ключ сообщения
	 * @param messageKey ключ сообщения
	 */
	public void setMessageKey(String messageKey)
	{
		this.messageKey = messageKey;
	}

	/**
	 * @return код ошибки сообщения
	 */
	public String getErrorKey()
	{
		return errorKey;
	}

	/**
	 * задать код ошибки сообщения
	 * @param errorKey код ошибки сообщения
	 */
	public void setErrorKey(String errorKey)
	{
		this.errorKey = errorKey;
	}

	/**
	 * @return шаблон сообщения
	 */
	public String getPattern()
	{
		return pattern;
	}

	/**
	 * задать шаблон сообщения
	 * @param pattern шаблон сообщения
	 */
	public void setPattern(String pattern)
	{
		this.pattern = pattern;
	}

	/**
	 * @return формат сообщения
	 */
	public String getFormatter()
	{
		return formatter;
	}

	/**
	 * задать формат сообщения
	 * @param formatter формат сообщения
	 */
	public void setFormatter(String formatter)
	{
		this.formatter = formatter;
	}
}
