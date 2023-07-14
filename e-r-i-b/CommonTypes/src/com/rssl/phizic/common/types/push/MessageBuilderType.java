package com.rssl.phizic.common.types.push;

/**
 * @author osminin
 * @ created 09.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Тип сообщений для построения push уведомлений
 */
public enum MessageBuilderType
{
	SHORT_MESSAGE("short"),
	FULL_MESSAGE("full"),
	SMS_MESSAGE("sms"),
	ATTRIBUTES("attributes", false);

	private String description;
	private boolean required;

	private MessageBuilderType(String description)
	{
		this(description, true);
	}

	private MessageBuilderType(String description, boolean required)
	{
		this.required = required;
		this.description = description;
	}

	/**
	 * @return обязательно ли сообщение
	 */
	public boolean isRequired()
	{
		return required;
	}

	/**
	 * @return описание сообщения
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @return выражение для шаблонов
	 */
	public String getTemplateRegexp()
	{
		return "^.+\\|" + description + "$";
	}
}
