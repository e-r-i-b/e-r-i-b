package com.rssl.phizic.business.dictionaries.pfp.products.simple;

import com.rssl.phizic.business.dictionaries.pfp.products.ProductBase;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductParameters;
import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;

import java.util.Map;

/**
 * @author akrenev
 * @ created 15.07.2013
 * @ $Author$
 * @ $Revision$
 */

public abstract class SimpleProductBase extends ProductBase
{
	private Map<PortfolioType, ProductParameters> parameters;

	/**
	 * @return параметры, уникатьные в рамках портфеля
	 */
	public Map<PortfolioType, ProductParameters> getParameters()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return parameters;
	}

	/**
	 * задать параметры, уникатьные в рамках портфеля
	 * @param parameters параметры, уникатьные в рамках портфеля
	 */
	public void setParameters(Map<PortfolioType, ProductParameters> parameters)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.parameters = parameters;
	}

	/**
	 * @param type тип портфеля
	 * @return параметры, уникатьные в рамках портфеля
	 */
	public ProductParameters getParameters(PortfolioType type)
	{
		return parameters.get(type);
	}

}
