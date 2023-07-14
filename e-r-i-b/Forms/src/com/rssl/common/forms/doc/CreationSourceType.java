package com.rssl.common.forms.doc;

/**
 * ������ �������� ���������
 @author Pankin
 @ created 02.12.2010
 @ $Author$
 @ $Revision$
 */
public enum CreationSourceType
{
	// �������, ������ ��� �������� ����
	ordinary("0"),

	// �������� ������ �� �������
	template("1"),

	// �������� ������ �� ������� ���������� �����
	mobiletemplate("2"),

	// �������� ������ �� �����
	copy("3"),

	// �������� ������ ������������� (��� ��������� ������� �� ��)
	system("4");

	private String value;

	CreationSourceType(String value)
	{
		this.value = value;
	}

	public String toValue() { return value; }

	public static CreationSourceType fromValue(String value)
	{
		if (value.equals(ordinary.value))
			return ordinary;
		if (value.equals(template.value))
			return template;
		if (value.equals(mobiletemplate.value))
			return mobiletemplate;
		if (value.equals(copy.value))
			return copy;
		if (value.equals(system.value))
			return system;
		throw new IllegalArgumentException("����������� ��� �������� ��������� [" + value + "]");
	}
}
