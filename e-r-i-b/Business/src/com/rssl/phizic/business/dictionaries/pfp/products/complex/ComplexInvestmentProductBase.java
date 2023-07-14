package com.rssl.phizic.business.dictionaries.pfp.products.complex;

import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.FundProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductParameters;

import java.util.List;
import java.util.Map;

/**
 * @author akrenev
 * @ created 27.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * базовый класс комплексного инвестиционного продукта
 */
public abstract class ComplexInvestmentProductBase extends ComplexProductBase
{
	private Map<PortfolioType, ProductParameters> parameters;
	private List<FundProduct> fundProducts;

	/**
	 * @return параметры, уникатьные в рамках портфел€
	 */
	public Map<PortfolioType, ProductParameters> getParameters()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return parameters;
	}

	/**
	 * задать параметры, уникатьные в рамках портфел€
	 * @param parameters параметры, уникатьные в рамках портфел€
	 */
	public void setParameters(Map<PortfolioType, ProductParameters> parameters)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.parameters = parameters;
	}

	/**
	 * @param type тип портфел€
	 * @return параметры, уникатьные в рамках портфел€
	 */
	public ProductParameters getParameters(PortfolioType type)
	{
		return parameters.get(type);
	}

	/**
	 * @return список ѕ»‘ов
	 */
	public List<FundProduct> getFundProducts()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return fundProducts;
	}

	/**
	 * задать список ѕ»‘ов
	 * @param fundProducts список ѕ»‘ов
	 */
	public void setFundProducts(List<FundProduct> fundProducts)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.fundProducts = fundProducts;
	}

	/**
	 * добавить список ѕ»‘ов
	 * @param fundProducts список ѕ»‘ов
	 */
	public void addFundProducts(List<FundProduct> fundProducts)
	{
		this.fundProducts.addAll(fundProducts);
	}

	/**
	 * очистить список ѕ»‘ов
	 */
	public void clearFundProducts()
	{
		fundProducts.clear();
	}

	public void updateFrom(DictionaryRecord that)
	{
		super.updateFrom(that);
		clearFundProducts();
		addFundProducts(((ComplexInvestmentProductBase) that).getFundProducts());
	}
}
