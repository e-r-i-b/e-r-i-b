package com.rssl.phizic.gate.depo;

/**
 * @author mihaylov
 * @ created 17.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Метод хранения ценной бумаги на счете ДЕПО
 */
public enum DepoAccountSecurityStorageMethod
{
	open("Открытый"),

	closed("Закрытый"),

	markByNominal("Маркированный по номиналу"),

	markByCoupon("Маркированный по купону"),

	markByNominalAndCoupon("Маркированный по номиналу и купону");

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
