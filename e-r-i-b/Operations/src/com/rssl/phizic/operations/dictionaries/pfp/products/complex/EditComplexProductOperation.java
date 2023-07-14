package com.rssl.phizic.operations.dictionaries.pfp.products.complex;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.products.complex.ComplexProductBase;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.AccountProduct;
import com.rssl.phizic.operations.dictionaries.pfp.products.EditProductOperationBase;

/**
 * @author akrenev
 * @ created 01.03.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class EditComplexProductOperation<P extends ComplexProductBase> extends EditProductOperationBase<P>
{
	/**
	 * задать вклад
	 * @param accountId идентификатор вклада
	 * @throws BusinessException
	 */
	public void setAccount(Long accountId) throws BusinessException, BusinessLogicException
	{
		ComplexProductBase product = getEntity();
		if (product.getAccount() != null && accountId.equals(product.getAccount().getId()))
			return;

		AccountProduct account = productService.getById(accountId, AccountProduct.class, getInstanceName());
		if (!(product.getClass().equals(account.getForComplex().getProductClass())))
			throw new BusinessLogicException("Невозможно использовать этот вклад для комплексного рподукта");

		product.setAccount(account);
	}
}
