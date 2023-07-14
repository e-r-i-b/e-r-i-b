package com.rssl.phizicgate.esberibgate.documents.senders;

/**
 * �������� � ��������� ���� ������
 * (������ ��������, ������ �������, ������ ����������� ����)
 *
 * @author hudyakov
 * @ created 21.09.2010
 * @ $Author$
 * @ $Revision$
 */
public enum XferMethodType
{
	OUR_TERBANK("0"),         //������ ��������
	EXTERNAL_TERBANK("1"),    //������ �������
	EXTERNAL_BANK("2");       //������ ���. ����

	XferMethodType(String value)
	{
		this.value = value;
	}

	private final String value;

	public String toValue()
	{
		return value;
	}
}
