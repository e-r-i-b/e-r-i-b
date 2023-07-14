package com.rssl.phizic.business.loanclaim.creditProduct.type;

import com.rssl.phizic.common.types.annotation.JsonExclusion;

/**
 * @author Moshenko
 * @ created 26.12.2013
 * @ $Author$
 * @ $Revision$
 * Тип кредитного продукта
 */
public class CreditProductType
{
	@JsonExclusion
	private  Long id;
	/**
	 * Название
	 */
	private String name;

	/**
	 * Код типа продукта
	 */
	private String code;

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

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof CreditProductType))
		{
			return false;
		}

		CreditProductType that = (CreditProductType) o;

		if (code != null ? !code.equals(that.code) : that.code != null)
		{
			return false;
		}

		return true;
	}

	public int hashCode()
	{
		return code != null ? code.hashCode() : 0;
	}
}
