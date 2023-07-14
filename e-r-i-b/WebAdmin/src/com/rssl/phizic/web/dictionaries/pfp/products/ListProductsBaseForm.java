package com.rssl.phizic.web.dictionaries.pfp.products;

import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Map;
import java.util.HashMap;

/**
 * @author akrenev
 * @ created 25.06.2013
 * @ $Author$
 * @ $Revision$
 *
 * Базовая форма списка продуктов ПФП
 */

public class ListProductsBaseForm extends ListFormBase
{
	private static final Map<String, PortfolioType> portfolioTypes = new HashMap<String, PortfolioType>();

	static
	{
		for (PortfolioType type : PortfolioType.values())
			portfolioTypes.put(type.name(), type);
	}

	/**
	 * @return мапа с типами портфелей
	 */
	public Map<String, PortfolioType> getPortfolioTypes()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return portfolioTypes;
	}
}
