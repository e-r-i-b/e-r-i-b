package com.rssl.phizic.web.pfp;

import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author mihaylov
 * @ created 15.04.2012
 * @ $Author$
 * @ $Revision$
 */

public class ChoosePfpProductForm extends ListFormBase implements PassingPFPFormInterface
{
	private Long id;
	private Long portfolioId;
	private String portfolioType;
	private String dictionaryProductType;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getPortfolioId()
	{
		return portfolioId;
	}

	public void setPortfolioId(Long portfolioId)
	{
		this.portfolioId = portfolioId;
	}

	public String getPortfolioType()
	{
		return portfolioType;
	}

	public void setPortfolioType(String portfolioType)
	{
		this.portfolioType = portfolioType;
	}

	public String getDictionaryProductType()
	{
		return dictionaryProductType;
	}

	public void setDictionaryProductType(String dictionaryProductType)
	{
		this.dictionaryProductType = dictionaryProductType;
	}

	public DictionaryProductType getDictionaryProductTypeEnum()
	{
		return DictionaryProductType.valueOf(dictionaryProductType);
	}
}
