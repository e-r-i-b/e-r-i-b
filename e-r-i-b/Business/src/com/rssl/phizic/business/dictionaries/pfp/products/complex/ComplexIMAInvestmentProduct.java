package com.rssl.phizic.business.dictionaries.pfp.products.complex;

import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.IMAProduct;

import java.util.List;

/**
 * @author akrenev
 * @ created 01.03.2012
 * @ $Author$
 * @ $Revision$
 *
 * Комплексный инвестиционный продукт (Депозит + ПИФ + ОМС)
 */
public class ComplexIMAInvestmentProduct extends ComplexInvestmentProductBase
{
	private List<IMAProduct> imaProducts;

	/**
	 * @return список ОМСов
	 */
	public List<IMAProduct> getImaProducts()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return imaProducts;
	}

	/**
	 * задать список ОМСов
	 * @param imaProducts список ОМСов
	 */
	public void setImaProducts(List<IMAProduct> imaProducts)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.imaProducts = imaProducts;
	}

	/**
	 * добавить список ОМСов
	 * @param imaProducts список ОМСов
	 */
	public void addIMAProducts(List<IMAProduct> imaProducts)
	{
		this.imaProducts.addAll(imaProducts);
	}

	/**
	 * очистить список ОМСов
	 */
	public void clearIMAProducts()
	{
		imaProducts.clear();
	}

	public void updateFrom(DictionaryRecord that)
	{
		super.updateFrom(that);
		clearIMAProducts();
		addIMAProducts(((ComplexIMAInvestmentProduct) that).getImaProducts());
	}
	@Override
	public DictionaryProductType getProductType()
	{
		return DictionaryProductType.COMPLEX_INVESTMENT_FUND_IMA;
	}
}
