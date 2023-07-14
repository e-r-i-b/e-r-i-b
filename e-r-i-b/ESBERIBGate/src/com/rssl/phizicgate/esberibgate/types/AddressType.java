package com.rssl.phizicgate.esberibgate.types;

/**
 * ���� �������.
 * 1 � ����� �����������,
 * 2 � ����� ����������,
 * 3 � ����� ��� ��������� ������ �������� ������������,
 * 4 - ����� ��� �������� �����������
 * 5 � ����� ����� ������
 *
 * @author egorova
 * @ created 18.11.2010
 * @ $Author$
 * @ $Revision$
 */

public enum AddressType
{
	REGISTRATION("1"),
	RESIDENCE("2"),
	MILITARY_PENSION("3"),
	POST_NOTIFICATION("4"),
	JOB("5");

	private String type;

	AddressType(String type)
	{
		this.type = type;
	}

	/**
	 * @return �������� ��� ������
	 */
	public String getType()
	{
		return type;
	}
}
