package com.rssl.phizic.business.dictionaries.pfp.products;

import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;

import java.util.Map;
import java.util.HashMap;

/**
 * @author akrenev
 * @ created 22.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Хелпер работы с продуктами
 */

public final class ProductHelper
{
	/**
	 * @return новая мапа параметров продукта, зависящих от портфеля
	 */
	public static Map<PortfolioType, ProductParameters> getNewProductParametersMap()
	{
		Map<PortfolioType, ProductParameters> parameters = new HashMap<PortfolioType, ProductParameters>();
		for (PortfolioType type : PortfolioType.values())
			parameters.put(type, new ProductParameters());
		return parameters;
	}

}
