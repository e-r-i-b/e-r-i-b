package com.rssl.phizic.operations.creditcards.products;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.creditcards.products.CreditCardProduct;
import com.rssl.phizic.business.creditcards.products.CreditCardProductService;
import com.rssl.phizic.business.loans.products.Publicity;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author Dorzhinov
 * @ created 15.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class PublishCreditCardProductOperation extends OperationBase
{
	private static final CreditCardProductService service = new CreditCardProductService();

	private CreditCardProduct product;

	public void initialize(Long productId) throws BusinessException
	{
		product = service.findById(productId);
	}

	public void publish() throws Exception
	{
		if(product.getPublicity() == Publicity.PUBLISHED)
			throw new BusinessLogicException("ƒанный продукт уже опубликован.");
		if(product.getConditions().isEmpty())
			throw new BusinessLogicException("¬ы не можете опубликовать карточный кредитный продукт, дл€ которого не заданы услови€ предоставлени€ в одной или нескольких валютах. ѕожалуйста, добавьте услови€.");
		product.setPublicity(Publicity.PUBLISHED);
		service.update(product);
	}

	public void unpublish() throws Exception
	{
		if(product.getPublicity() == Publicity.UNPUBLISHED)
			throw new BusinessLogicException("ƒанный продукт уже сн€т с публикации.");
		product.setPublicity(Publicity.UNPUBLISHED);
		service.update(product);
	}
}
