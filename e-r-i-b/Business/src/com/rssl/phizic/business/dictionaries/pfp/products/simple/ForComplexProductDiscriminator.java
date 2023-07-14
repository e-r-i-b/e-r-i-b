package com.rssl.phizic.business.dictionaries.pfp.products.simple;

import com.rssl.phizic.business.dictionaries.pfp.products.simple.Product;
import com.rssl.phizic.business.dictionaries.pfp.products.complex.ComplexInsuranceProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.complex.ComplexFundInvestmentProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.complex.ComplexIMAInvestmentProduct;

/**
 * @author akrenev
 * @ created 24.04.2012
 * @ $Author$
 * @ $Revision$
 *
 * ����������� ������� ��������� ����������� ���������
 */
public enum ForComplexProductDiscriminator
{
	none(Product.class),                       // �� ��� ������������ ��������
	insurance(ComplexInsuranceProduct.class),  // ����������� ��������� �������
	fund(ComplexFundInvestmentProduct.class),  // ����������� �������������� ������� (������� + ���)
	ima(ComplexIMAInvestmentProduct.class);    // ����������� �������������� ������� (������� + ��� + ���)

	private final Class productClass;

	ForComplexProductDiscriminator(Class productClass)
	{
		this.productClass = productClass;
	}

	/**
	 * @return ����� ��� �������� �������� �������
	 */
	public Class getProductClass()
	{
		return productClass;
	}
}
