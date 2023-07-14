package com.rssl.phizic.gate.loans;

/**
 * @author mihaylov
 * @ created 19.07.2010
 * @ $Author$
 * @ $Revision$
 */

public enum LoanPaymentState
{
	/**
	 * ���������� ������
	 */
	paid("�������"),
	/**
	 * ������� ������
	 */
	current("�������"),
	/**
	 * �������� ������(� ������)
	 */
	future("� ������"),
	/**
	 * ������������ ������
	 */
	fail("���������");

	private String description;

	LoanPaymentState(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}

	public static LoanPaymentState fromDescription(String description)
	{
		if( description.equals(paid.description)) return paid;
		else if( description.equals(current.description)) return current;
		else if( description.equals(future.description)) return future;
		else if( description.equals(fail.description)) return fail;
		throw new IllegalArgumentException("������������ ������ ������� [" + description + "]");
	}


}
