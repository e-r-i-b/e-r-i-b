package com.rssl.phizic.business.loanclaim.creditProduct;

import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditSubProductType;
import com.rssl.phizic.common.types.annotation.JsonExclusion;

import java.util.Set;

/**
 * @author Moshenko
 * @ created 04.01.2014
 * @ $Author$
 * @ $Revision$
 * Кредитный продукт
 */
public class CreditProduct
{
	@JsonExclusion
	private Long id;
	/**
	 * Название
	 */
	private String name;

	/**
	 * Код продукта
	 */
	private String code;

	/**
	 * Коментарий к коду продутка
	 */
	@JsonExclusion
	private String codeDescription;

	/**
	 * Обеспечение
	 */
	private boolean ensuring;

	/**
	 * Типы кредитного суб продукта
	 */
	private Set<CreditSubProductType> creditSubProductTypes;

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

	public String getCodeDescription()
	{
		return codeDescription;
	}

	public void setCodeDescription(String codeDescription)
	{
		this.codeDescription = codeDescription;
	}

	public boolean isEnsuring()
	{
		return ensuring;
	}

	public void setEnsuring(boolean ensuring)
	{
		this.ensuring = ensuring;
	}

	public Set<CreditSubProductType> getCreditSubProductTypes()
	{
		return creditSubProductTypes;
	}

	public void setCreditSubProductTypes(Set<CreditSubProductType> creditSubProductTypes)
	{
		this.creditSubProductTypes = creditSubProductTypes;
	}
}
