package com.rssl.phizic.gate.depo;

/**
 * @author mihaylov
 * @ created 16.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Статус счета ДЕПО клиента
 */
public enum DepoAccountState
{
	/**
	 * Открытый счет ДЕПО
	 */
	open("Открыт"),
	/**
	 * Закрытый счет ДЕПО
	 */
	closed("Закрыт");

	private String description;

	DepoAccountState(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
