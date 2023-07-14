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
 * ����������� ��������� �������
 */
public class ComplexInsuranceProduct extends ComplexProductBase
{
	private BigDecimal minSum;
	private List<InsuranceProduct> insuranceProducts;
	private BigDecimal minSumInsurance;

	/**
	 * @return ����������� �����
	 */
	public BigDecimal getMinSum()
	{
		return minSum;
	}

	/**
	 * ������ ����������� �����
	 * @param minSum ����������� �����
	 */
	public void setMinSum(BigDecimal minSum)
	{
		this.minSum = minSum;
	}

	/**
	 * @return ��������� ��������
	 */
	public List<InsuranceProduct> getInsuranceProducts()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return insuranceProducts;
	}

	/**
	 * ������ ��������� ��������
	 * @param insuranceProducts ��������� ��������
	 */
	public void setInsuranceProducts(List<InsuranceProduct> insuranceProducts)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.insuranceProducts = insuranceProducts;
	}

	/**
	 * �������� ������ ��������� ���������
	 * @param insuranceProducts ������ ��������� ���������
	 */
	public void addInsuranceProducts(List<InsuranceProduct> insuranceProducts)
	{
		this.insuranceProducts.addAll(insuranceProducts);
	}

	/**
	 * �������� ������ ��������� ���������
	 */
	public void clearInsuranceProducts()
	{
		insuranceProducts.clear();
	}

	/**
	 * @return ����������� ��� ����������� ����� ���������� ������
	 */
	public BigDecimal getMinSumInsurance()
	{
		return minSumInsurance;
	}

	/**
	 * ������ ����������� ��� ����������� ����� ���������� ������
	 * @param minSumInsurance �����������
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
