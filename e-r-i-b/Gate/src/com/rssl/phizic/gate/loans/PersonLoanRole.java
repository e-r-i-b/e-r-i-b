package com.rssl.phizic.gate.loans;

/**
 * @author mihaylov
 * @ created 15.07.2010
 * @ $Author$
 * @ $Revision$
 */

public enum PersonLoanRole
{
	/**
	 * Заемщик,Созаемщик
	 */
	borrower("заемщик/созаемщик"),
	/**
	 * Поручитель,Залогодатель
	 */
	guarantor("поручитель/залогодатель");

	private String description;

	PersonLoanRole(String str)
	{
		this.description = str;
	}

	public String toValue() { return description; }

	public static PersonLoanRole fromValue(String value)
	{
		if( value.equals(borrower.description)) return borrower;
		if( value.equals(guarantor.description)) return guarantor;
		throw new IllegalArgumentException("Неизвестный тип роли [" + value + "]");
	}

	public String getDescription()
	{
		return description;
	}
}
