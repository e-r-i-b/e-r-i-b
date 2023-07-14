package com.rssl.phizic.operations.dictionaries.pfp.products.loan;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.pfp.products.loan.LoanKindProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.loan.LoanKindProductService;
import com.rssl.phizic.operations.dictionaries.synchronization.RemoveDictionaryEntityWithImageOperationBase;

/**
 * @author akrenev
 * @ created 29.03.2012
 * @ $Author$
 * @ $Revision$
 *
 * ќпераци€ удалени€ кредитных продуктов
 */

public class RemoveLoanKindProductOperation extends RemoveDictionaryEntityWithImageOperationBase
{
	private static final LoanKindProductService service = new LoanKindProductService();
	private LoanKindProduct loanKindProduct;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		loanKindProduct = service.getById(id, getInstanceName());
		if (loanKindProduct == null)
			throw new ResourceNotFoundBusinessException("¬ системе не найден кредитный продукт с id: " + id, LoanKindProduct.class);
	}

	protected void doRemove() throws BusinessException, BusinessLogicException
	{
		service.remove(loanKindProduct, getInstanceName());
	}

	public Object getEntity()
	{
		return loanKindProduct;
	}
}
