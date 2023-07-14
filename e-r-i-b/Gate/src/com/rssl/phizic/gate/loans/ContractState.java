package com.rssl.phizic.gate.loans;

/**
 * @author gladishev
 * @ created 06.05.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * Статус договора
 */
public enum ContractState
{
	/**
    * закрыт
    */
   closed("4"),

	/**
    * открыт
    */
   open("0");


	private String value;

	ContractState(String value)
	{
		this.value = value;
	}

	public String toValue() { return value; }

	public static ContractState fromValue(String value)
	{
		if( value.equals(closed.value)) return closed;
		else return open;
	}
}
