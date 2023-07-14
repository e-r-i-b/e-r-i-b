package com.rssl.phizicgate.manager.routing;

/**
 * Тип интегрированного адаптера
 * @author komarov
 * @ created 25.10.13 
 * @ $Author$
 * @ $Revision$
 */

public enum AdapterType
{
	NONE("Внешний шлюз"), //Не интегрирован
	ESB("Взаимодействие через шину"), //адаптер является шинны(интегрирован с ESB)
	//Integrated GateWay
	IGW("Интегрированный шлюз"); //интегрирован с ипс

	private final String description;

	AdapterType(String description)
	{
		this.description = description;
	}

	/**
	 * @return описание
	 */
	public String getDescription()
	{
		return description;
	}
}
