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

}
