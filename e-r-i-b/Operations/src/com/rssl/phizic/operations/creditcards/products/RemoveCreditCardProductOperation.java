package com.rssl.phizic.operations.creditcards.products;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.creditcards.products.CreditCardProduct;
import com.rssl.phizic.business.creditcards.products.CreditCardProductService;
import com.rssl.phizic.business.loans.products.Publicity;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;

/**
 * @author Dorzhinov
 * @ created 15.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class RemoveCreditCardProductOperation extends OperationBase implements RemoveEntityOperation
{
	private static final CreditCardProductService creditCardProductService = new CreditCardProductService();

	private CreditCardProduct product;

	/**
	 * Инициализация
	 * @param productId ID карточного кредитного продукта для удаления
	 */
	public void initialize(Long productId) throws BusinessException
	{
		product = creditCardProductService.findById(productId);

		if(product == null)
			throw new BusinessException("Карточный кредитный продукт с Id = " + productId + " не найден");
	}

	/**
	 * Удалить карточный кредитный продукт
	 */
	public void remove() throws BusinessException, BusinessLogicException
	{
		if(product.getPublicity() == Publicity.PUBLISHED)
			throw new BusinessLogicException("Вы не можете удалить данный карточный кредитный продукт, так как он доступен клиентам для оформления заявки. Чтобы удалить карточный кредитный продукт, снимите его с публикации и повторите операцию.");
		if(!product.getConditions().isEmpty())
			throw new BusinessLogicException("Вы не можете удалить продукт, для которого указаны условия предоставления в валюте. Удалите условия для каждой валюты и повторите операцию.");

		creditCardProductService.remove(product);
	}

	public Object getEntity()
	{
		return product;
	}
}
