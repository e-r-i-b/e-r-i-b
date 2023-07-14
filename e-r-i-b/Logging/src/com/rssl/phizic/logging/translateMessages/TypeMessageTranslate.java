package com.rssl.phizic.logging.translateMessages;

/**
 * @author Mescheryakova
 * @ created 27.07.2011
 * @ $Author$
 * @ $Revision$
 * ��������� ���� ���������: R - ������, A - �����
 */

public enum TypeMessageTranslate
{
	R("REQUEST"),   // ������
	A("ANSWER");   //�����

	private String value;
	TypeMessageTranslate(String value)
	{
		this.value = value;
	}

	public String toString()
	{
		return this.value;
	}

}
