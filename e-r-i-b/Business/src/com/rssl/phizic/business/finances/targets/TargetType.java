package com.rssl.phizic.business.finances.targets;

/**
 * Типы целей для открытия вклада
 * При изменении значений оповестить разработчиков mAPI, т.к. эти значения применяются в МП для получения иконок целей, хранящихся в самом МП.
 * @author lepihina
 * @ created 19.03.2013
 * @ $Author$
 * @ $Revision$
 */
public enum TargetType
{
	OTHER("Другая цель"),
	AUTO("Автомобиль"),
	EDUCATION("Образование"),
	RESERVE("Финансовый резерв"),
	RENOVATION("Ремонт"),
	VACATION("Отдых"),
	APPLIANCE("Бытовая техника"),
	FURNITURE("Мебель"),
	BUSINESS("Свое дело"),
	ESTATE("Недвижимость"),
	HOLIDAYS("Праздник");

	private final String description;
	
	TargetType(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
