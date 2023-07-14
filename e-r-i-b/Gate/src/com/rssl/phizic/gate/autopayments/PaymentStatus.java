package com.rssl.phizic.gate.autopayments;

/**
 * @author bogdanov
 * @ created 27.01.2012
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������� �� ��������.
 */

public enum PaymentStatus
{
	NEW("�����"),
	CANCELED("������"),
	DONE("��������");

	private String description;

	PaymentStatus(String description)
	{
		this.description = description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
