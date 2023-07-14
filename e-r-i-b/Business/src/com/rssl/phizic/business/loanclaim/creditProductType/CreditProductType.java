package com.rssl.phizic.business.loanclaim.creditProductType;

/**
 * @author Moshenko
 * @ created 26.12.2013
 * @ $Author$
 * @ $Revision$
 * Тип кредитного продукта
 */
public class CreditProductType
{
	private Long id;
	/**
	 * Название
	 */
	private String name;

	/**
	 * Код типа продукта
	 */
	private Long code;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Long getCode()
	{
		return code;
	}

	public void setCode(Long code)
	{
		this.code = code;
	}
}
