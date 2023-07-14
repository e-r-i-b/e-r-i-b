package com.rssl.phizic.gate.loans;

/**
 * @author gladishev
 * @ created 06.05.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * База для начисления коммиссии.
 */
public enum CommissionBase
{
	/**
	 * зависит от суммы кредита
	 */
	loanAmount("1) сумма кредита"),

	/**
	 * зависит от суммы долга
	 */
	loanRest("2) остаток основного долга");

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
		throw new IllegalArgumentException("Неизвестная база комиссии [" + value + "]");
	}
}
