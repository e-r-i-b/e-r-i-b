package com.rssl.phizic.dataaccess.query;

/**
 * @author mihaylov
 * @ created 30.07.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����������� ���������� � SQL �������
 */
public enum OrderDirection
{
	ASC(1),// �� �����������
	DESC(-1); // �� ��������

	private int sign;

	private OrderDirection(int sign)
	{
		this.sign = sign;
	}

	public static OrderDirection value(String str)
	{
		return valueOf(str.toUpperCase());
    }

	/**
	 * @return ����������� ����������
	 */
	public int getSign()
	{
		return sign;
	}
}
