package com.rssl.phizic.common.types.push;

/**
 * @author osminin
 * @ created 09.10.14
 * @ $Author$
 * @ $Revision$
 *
 * ��� ��������� ��� ���������� push �����������
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
	 * @return ����������� �� ���������
	 */
	public boolean isRequired()
	{
		return required;
	}

	/**
	 * @return �������� ���������
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @return ��������� ��� ��������
	 */
	public String getTemplateRegexp()
	{
		return "^.+\\|" + description + "$";
	}
}
