package com.rssl.phizic.gate.loans;

/**
 * @author gladishev
 * @ created 06.05.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���� ��� ���������� ���������.
 */
public enum CommissionBase
{
	/**
	 * ������� �� ����� �������
	 */
	loanAmount("1) ����� �������"),

	/**
	 * ������� �� ����� �����
	 */
	loanRest("2) ������� ��������� �����");

	private String value;

	CommissionBase(String value)
	{
		this.value = value;
	}

	public String toValue() { return value; }

	public static CommissionBase fromValue(String value)
	{
		if( value.equals(loanAmount.value)) return loanAmount;
		if( value.equals(loanRest.value)) return loanRest;
		throw new IllegalArgumentException("����������� ���� �������� [" + value + "]");
	}
}
