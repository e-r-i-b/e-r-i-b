package com.rssl.common.forms.types;

/**
 * ���. ��� ���� (������������, �������������)
 *
 * @author khudyakov
 * @ created 31.05.2013
 * @ $Author$
 * @ $Revision$
 */
public enum SubType
{
	DINAMIC("dinamic"),                //������������ ����
	STATIC("static");                  //�����������

	private String value;

	SubType(String value)
	{
		this.value = value;
	}

	/**
	 * ������� �������� ����� �� ������
	 * @param subType ���
	 * @return ��������
	 */
	public static SubType fromValue(String subType)
	{
		if (DINAMIC.getValue().equals(subType))
		{
			return DINAMIC;
		}
		if (STATIC.getValue().equals(subType))
		{
			return STATIC;
		}
		throw new IllegalArgumentException("������������ ��� SubType" + subType);
	}

	/**
	 * @return ��������
	 */
	public String getValue()
	{
		return value;
	}
}
