package com.rssl.phizic.business.ermb.sms.messaging.imsi;

import com.rssl.phizic.utils.StringHelper;

/**
 * @author EgorovaA
 * @ created 08.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Enum ��� �������� ���������� �������� IMSI. �� ������ ������ �������� ��� ��������.
 */
public enum CheckIMSIState
{
	OK("0"),
	ERROR("1");

	private String type;

	CheckIMSIState(String type)
	{
		this.type = type;
	}

	/**
	 * @return �������� ��� �������
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * �������� boolean-�������� �� ���������� �������� �������
	 * @param type
	 * @return
	 */
	public static boolean getBooleanFromType(String type)
	{
		if (StringHelper.equals(type, OK.type))
			return true;
		if (StringHelper.equals(type, ERROR.type))
			return false;

		throw new IllegalArgumentException("����������� �������� ������� [" + type + "]");
	}
}
