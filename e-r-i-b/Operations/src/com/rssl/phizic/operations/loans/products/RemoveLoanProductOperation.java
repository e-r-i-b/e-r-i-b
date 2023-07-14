package com.rssl.phizic.operations.loans.products;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loans.products.*;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;

import java.util.List;

/**
 * @author gladishev
 * @ created 19.12.2007
 * @ $Author$
 * @ $Revision$
 */

public class RemoveLoanProductOperation extends OperationBase implements RemoveEntityOperation
{
	private static final LoanProductService loanProductService = new LoanProductService();
	private static final ModifiableLoanProductService modifiableLoanProductService = new ModifiableLoanProductService();

	private LoanProductBase loanProduct;

	/**
	 * Инициализация
	 * @param productId ID кредитный продукт для удаления
	 */
	public void initialize(Long productId) throws BusinessException
	{
		loanProduct = modifiableLoanProductService.findById(productId);

		if (loanProduct == null)
			loanProduct = loanProductService.findById(productId);

		if(loanProduct == null)
			throw new BusinessException("Кредитный продукт с Id = " + productId + " не найден");
	}

	/**
	 * Удалить кредитный продукт
	 */
	public void remove() throws BusinessLogicException, BusinessException
	{
		if(loanProduct instanceof ModifiableLoanProduct)
		{
			ModifiableLoanProduct product = (ModifiableLoanProduct) loanProduct;
			if(product.getPublicity() == Publicity.PUBLISHED)
				throw new BusinessLogicException("Вы не можете удалить данный кредитный продукт, так как он доступен клиентам для оформления кредита. Чтобы удалить кредитный продукт, снимите его с публикации.");
			if(!product.getConditions().isEmpty())
				throw new BusinessLogicException("Вы не можете удалить кредитный продукт, для которого указаны условия предоставления  в разных валютах. Для его удаления, сначала удалите условия для каждой валюты, затем повторите операцию.");

			modifiableLoanProductService.remove(product);
		}
		else if(loanProduct instanceof LoanProduct)
			loanProductService.remove((LoanProduct)loanProduct);
	}

	public Object getEntity()
	{
		return loanProduct;
	}

	public boolean checkBeforeRemove(RemoveLoanProductOperation operation, String id) throws BusinessException, DataAccessException
	{
		Query query = operation.createQuery("list")
				    .setParameter("productId", id);
		List rezult = query.executeList();
		if (rezult.size() == 0)
			return true;
		return false;
	}
}
