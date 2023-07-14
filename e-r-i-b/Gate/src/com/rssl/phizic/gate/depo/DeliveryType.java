package com.rssl.phizic.gate.depo;

/**
 * @author akrenev
 * @ created 15.12.2011
 * @ $Author$
 * @ $Revision$
 */
/**
 * ��� ��������.
 */
public enum DeliveryType
{
	/**
	 * ����������
	 */
	FELD("1"),
	/**
	 * ������ ����������
	 */
	INKASS("2"),
	/**
	 * ��� ��������
	 */
	NOT_DELIVER("3");


	private String value;

	/**
	 * @return ��� ��������
	 */
	public String getValue()
	{
		return value;
	}

	private DeliveryType(String value)
	{
		this.value = value;
	}
}