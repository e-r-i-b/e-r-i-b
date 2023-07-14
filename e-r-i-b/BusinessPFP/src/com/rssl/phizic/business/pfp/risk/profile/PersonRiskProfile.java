package com.rssl.phizic.business.pfp.risk.profile;

import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.ProductType;

import java.util.List;
import java.util.Map;

/**
 * @author komarov
 * @ created 25.06.2013 
 * @ $Author$
 * @ $Revision$
 */

public class PersonRiskProfile
{
	private Map<ProductType, Long> productsWeights; // процентное соотношение по продуктам
	private List<DictionaryProductType> availableProducts;

	/**
	 * @return процентное соотношение по продуктам
	 */
	public Map<ProductType, Long> getProductsWeights()
	{
		return productsWeights;
	}

	/**
	 * @param productsWeights процентное соотношение по продуктам
	 */
	public void setProductsWeights(Map<ProductType, Long> productsWeights)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.productsWeights = productsWeights;
	}

	/**
	 * @return доступные типы продуктов
	 */
	public List<DictionaryProductType> getAvailableProducts()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return availableProducts;
	}

	/**
	 * задать доступные типы продуктов
	 * @param availableProducts доступные типы продуктов
	 */
	public void setAvailableProducts(List<DictionaryProductType> availableProducts)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.availableProducts = availableProducts;
	}
}
