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
 * доступность простых продуктов комплексным продуктов
 */
public enum ForComplexProductDiscriminator
{
	none(Product.class),                       // не для комплексного продукта
	insurance(ComplexInsuranceProduct.class),  // комплексный страховой продукт
	fund(ComplexFundInvestmentProduct.class),  // комплексный инвестиционный продукт (депозит + ПИФ)
	ima(ComplexIMAInvestmentProduct.class);    // комплексный инвестиционный продукт (депозит + ПИФ + ОМС)

	private final Class productClass;

	ForComplexProductDiscriminator(Class productClass)
	{
		this.productClass = productClass;
	}

	/**
	 * @return класс для которого допустим продукт
	 */
	public Class getProductClass()
	{
		return productClass;
	}
}
