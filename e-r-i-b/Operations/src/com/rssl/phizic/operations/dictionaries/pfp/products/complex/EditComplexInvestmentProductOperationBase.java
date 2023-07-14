package com.rssl.phizic.operations.dictionaries.pfp.products.complex;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.products.complex.ComplexInvestmentProductBase;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.FundProduct;
import org.apache.commons.lang.ArrayUtils;

/**
 * @author akrenev
 * @ created 01.03.2012
 * @ $Author$
 * @ $Revision$
 *
 * Базовая операция редактирования и удаления комплексного инвестиционного продукта
 */

public abstract class EditComplexInvestmentProductOperationBase<P extends ComplexInvestmentProductBase> extends EditComplexProductOperation<P>
{
	/**
	 * задать список ПИФов
	 * @param fundProductIds идентификаторы ПИФов
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void setFundProducts(Long[] fundProductIds) throws BusinessLogicException, BusinessException
	{
		P product = getEntity();
		product.clearFundProducts();
		if (!ArrayUtils.isEmpty(fundProductIds))
			product.addFundProducts(productService.getListByIds(fundProductIds, FundProduct.class, getInstanceName()));
	}
}
