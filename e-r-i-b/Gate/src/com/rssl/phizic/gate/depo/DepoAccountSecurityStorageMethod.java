package com.rssl.phizic.gate.depo;

/**
 * @author mihaylov
 * @ created 17.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����� �������� ������ ������ �� ����� ����
 */
public enum DepoAccountSecurityStorageMethod
{
	open("��������"),

	closed("��������"),

	markByNominal("������������� �� ��������"),

	markByCoupon("������������� �� ������"),

	markByNominalAndCoupon("������������� �� �������� � ������");

	private String description;

	DepoAccountSecurityStorageMethod(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}

}
