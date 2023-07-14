package com.rssl.common.forms.doc;

import com.rssl.phizic.utils.StringHelper;

/**
 * ����� ��������
 * @author gladishev
 * @ created 14.10.2008
 * @ $Author$
 * @ $Revision$
 */

//TODO ������������� � ChannelType
public enum CreationType
{
	system("0"),
	internet("1"),
	sms("2"),
	mobile("3"),
	atm("4"),
	social("5");

	private String value;

	CreationType(String value)
	{
		this.value = value;
	}

	public String toValue() { return value; }

	public static CreationType fromValue(String value)
	{
		if( value.equals(internet.value)) return internet;
		if( value.equals(sms.value)) return sms;
		if( value.equals(mobile.value)) return mobile;
		if (value.equals(atm.value)) return atm;
		if (value.equals(social.value)) return social;
		if (value.equals(system.value)) return system;
		throw new IllegalArgumentException("����������� ��� �������� ��������� [" + value + "]");
	}

	/**
	 * ������������� ��������� �������� � �������� ���� CreationType
	 * @param value ��������� ��������
	 * @return �������� ���� CreationType
	 */
	public static CreationType toCreationType(String value)
	{
		if (StringHelper.isEmpty(value))
		{
			return null;
		}
		return CreationType.valueOf(value);
	}
}
