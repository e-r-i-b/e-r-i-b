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
 * ������� ����� ������������ ��������������� ��������
 */
public abstract class ComplexInvestmentProductBase extends ComplexProductBase
{
	private Map<PortfolioType, ProductParameters> parameters;
	private List<FundProduct> fundProducts;

	/**
	 * @return ���������, ���������� � ������ ��������
	 */
	public Map<PortfolioType, ProductParameters> getParameters()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return parameters;
	}

	/**
	 * ������ ���������, ���������� � ������ ��������
	 * @param parameters ���������, ���������� � ������ ��������
	 */
	public void setParameters(Map<PortfolioType, ProductParameters> parameters)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.parameters = parameters;
	}

	/**
	 * @param type ��� ��������
	 * @return ���������, ���������� � ������ ��������
	 */
	public ProductParameters getParameters(PortfolioType type)
	{
		return parameters.get(type);
	}

	/**
	 * @return ������ �����
	 */
	public List<FundProduct> getFundProducts()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return fundProducts;
	}

	/**
	 * ������ ������ �����
	 * @param fundProducts ������ �����
	 */
	public void setFundProducts(List<FundProduct> fundProducts)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.fundProducts = fundProducts;
	}

	/**
	 * �������� ������ �����
	 * @param fundProducts ������ �����
	 */
	public void addFundProducts(List<FundProduct> fundProducts)
	{
		this.fundProducts.addAll(fundProducts);
	}

	/**
	 * �������� ������ �����
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
