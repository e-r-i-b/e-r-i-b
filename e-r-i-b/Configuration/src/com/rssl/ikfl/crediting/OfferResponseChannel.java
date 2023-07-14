package com.rssl.ikfl.crediting;

/**
 * @author Erkin
 * @ created 29.12.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����� �������
 */
public enum OfferResponseChannel
{
	/**
	 * �������� ���@��
	 */
	SBOL ("����-����"),

	/**
	 * ��������� ���������� ����
	 */
	MP ("����-��"),

	/**
	 * ���������� ����������������
	 */
	US ("����-��"),

	/**
	 * ��������� ����
	 */
	MB ("����-��"),

	/**
	 * �������� ����
	 */
	SBOL_GUEST ("����-��������"),

	;

	/**
	 * ��� ������ � CRM
	 */
	public final String crmCode;

	private OfferResponseChannel(String crmCode)
	{
		this.crmCode = crmCode;
	}

	public String getCrmCode()
	{
		return crmCode;
	}
}
