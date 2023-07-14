package com.rssl.phizic.gate.einvoicing;

/**
 * ��������� ������.
 *
 * @author bogdanov
 * @ created 10.02.14
 * @ $Author$
 * @ $Revision$
 */

public enum OrderState
{
	CREATED ("������"),
	RELATED ("�������� � �������"),
	CANCELED ("������� ��������"),
	PAYMENT ("������"),
	WRITE_OFF ("�������� �������"),
	ERROR ("������ ����������"),
	EXECUTED ("�������"),
	REFUSED ("�������"),
	PARTIAL_REFUND ("��������� �������"),
	REFUND ("�������"),
	DELAYED("������� ��������");

	final String description;

	OrderState(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
