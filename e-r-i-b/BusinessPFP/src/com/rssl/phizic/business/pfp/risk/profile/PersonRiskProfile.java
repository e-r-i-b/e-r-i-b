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
	private Map<ProductType, Long> productsWeights; // ���������� ����������� �� ���������
	private List<DictionaryProductType> availableProducts;

	/**
	 * @return ���������� ����������� �� ���������
	 */
	public Map<ProductType, Long> getProductsWeights()
	{
		return productsWeights;
	}

	/**
	 * @param productsWeights ���������� ����������� �� ���������
	 */
	public void setProductsWeights(Map<ProductType, Long> productsWeights)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.productsWeights = productsWeights;
	}

	/**
	 * @return ��������� ���� ���������
	 */
	public List<DictionaryProductType> getAvailableProducts()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return availableProducts;
	}

	/**
	 * ������ ��������� ���� ���������
	 * @param availableProducts ��������� ���� ���������
	 */
	public void setAvailableProducts(List<DictionaryProductType> availableProducts)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.availableProducts = availableProducts;
	}
}
