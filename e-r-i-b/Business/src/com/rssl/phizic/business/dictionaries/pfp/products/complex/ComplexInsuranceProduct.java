package com.rssl.phizic.business.dictionaries.pfp.products.complex;

import com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceProduct;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

import java.util.List;
import java.math.BigDecimal;

/**
 * @author akrenev
 * @ created 24.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * Комплексный страховой продукт
 */
public class ComplexInsuranceProduct extends ComplexProductBase
{
	private BigDecimal minSum;
	private List<InsuranceProduct> insuranceProducts;
	private BigDecimal minSumInsurance;

	/**
	 * @return минимальная сумма
	 */
	public BigDecimal getMinSum()
	{
		return minSum;
	}

	/**
	 * задать минимальную сумму
	 * @param minSum минимальная сумма
	 */
	public void setMinSum(BigDecimal minSum)
	{
		this.minSum = minSum;
	}

	/**
	 * @return страховые продукты
	 */
	public List<InsuranceProduct> getInsuranceProducts()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return insuranceProducts;
	}

	/**
	 * задать страховые продукты
	 * @param insuranceProducts страховые продукты
	 */
	public void setInsuranceProducts(List<InsuranceProduct> insuranceProducts)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.insuranceProducts = insuranceProducts;
	}

	/**
	 * добавить список страховых продуктов
	 * @param insuranceProducts список страховых продуктов
	 */
	public void addInsuranceProducts(List<InsuranceProduct> insuranceProducts)
	{
		this.insuranceProducts.addAll(insuranceProducts);
	}

	/**
	 * очистить список страховых продуктов
	 */
	public void clearInsuranceProducts()
	{
		insuranceProducts.clear();
	}

	/**
	 * @return коэффициент для минимальной суммы страхового взноса
	 */
	public BigDecimal getMinSumInsurance()
	{
		return minSumInsurance;
	}

	/**
	 * задать коэффициент для минимальной суммы страхового взноса
	 * @param minSumInsurance коэффициент
	 */
	public void setMinSumInsurance(BigDecimal minSumInsurance)
	{
		this.minSumInsurance = minSumInsurance;
	}

	public void updateFrom(DictionaryRecord that)
	{
		super.updateFrom(that);
		ComplexInsuranceProduct source = (ComplexInsuranceProduct) that;
		setMinSum(source.getMinSum());
		setMinSumInsurance(source.getMinSumInsurance());
		clearInsuranceProducts();
		addInsuranceProducts(source.getInsuranceProducts());
	}

	public DictionaryProductType getProductType()
	{
		return DictionaryProductType.COMPLEX_INSURANCE;
	}
}
