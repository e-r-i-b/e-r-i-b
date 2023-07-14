package com.rssl.phizic.business.dictionaries.payment.services;

/**
 * @author lukina
 * @ created 17.03.2011
 * @ $Author$
 * @ $Revision$
 */

public enum CategoryServiceType
{
	//Связь, Интернет и телевидение
	COMMUNICATION("Связь, Интернет и телевидение"),
	//Переводы и обмен валют
	TRANSFER("Переводы и обмен валют"),
	//Операции по вкладам и кредитам
	DEPOSITS_AND_LOANS("Операции по вкладам, картам, кредитам и ОМС"),
	//Налоги и штрафы, ГИБДД
	TAX_PAYMENT("Налоги, штрафы, ГИБДД"),
	//Образование
	EDUCATION("Образование"),
	//Пенсионные фонды
	PFR("Пенсионные фонды"),
	//Оплата покупок и услуг
	SERVICE_PAYMENT("Оплата покупок и услуг"),
	//прочее
	OTHER("Прочие...");

	private String value;

	CategoryServiceType(String value)
	{
		this.value = value;
	}

	public String toValue() { return value; }

	public String getValue()
	{
		return value;
	}

	public String getName()
	{
		return name();
	}

    public static CategoryServiceType fromValue(String value)
    {
        for(CategoryServiceType category : values())
            if(category.name().equals(value))
                return category;

        throw new IllegalArgumentException("Неизвестная категория платежей [" + value + "]");
    }
}
