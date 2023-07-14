package com.rssl.phizic.gate.loans;

/**
 * @author gladishev
 * @ created 06.05.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * Тип договора
 */
public enum ContractType
{
	/**
	 * Поручительство
	 */
	guarantee("1"),

	/**
	 * Залог ценных бумаг
	 */
	pledgeSecurity("2"),

	/**
	 * Залог имущества
	 */
	wadset("3"),

	/**
	 * Залог драг.металлов
	 */
	pledgePrecious("4");


	private String value;

	ContractType(String value)
	{
		this.value = value;
	}

	public String toValue() { return value; }

	public static ContractType fromValue(String value)
	{
		if( value.equals(guarantee.value)) return guarantee;
		if( value.equals(wadset.value)) return wadset;
		if( value.equals(pledgePrecious.value)) return pledgePrecious;
		if( value.equals(pledgeSecurity.value)) return pledgeSecurity;
		throw new IllegalArgumentException("Неизвестный тип контракта [" + value + "]");
	}
}
