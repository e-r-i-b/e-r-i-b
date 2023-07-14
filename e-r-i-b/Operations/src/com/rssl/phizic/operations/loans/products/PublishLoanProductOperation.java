package com.rssl.phizic.operations.loans.products;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loans.products.ModifiableLoanProduct;
import com.rssl.phizic.business.loans.products.ModifiableLoanProductService;
import com.rssl.phizic.business.loans.products.Publicity;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author Dorzhinov
 * @ created 13.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class PublishLoanProductOperation extends OperationBase
{
	private static final ModifiableLoanProductService service = new ModifiableLoanProductService();

	private ModifiableLoanProduct product;

	public void initialize(Long productId) throws BusinessException
	{
		product = service.findById(productId);
	}

	public void publish() throws Exception
	{
		if(product.getPublicity() == Publicity.PUBLISHED)
			throw new BusinessLogicException("ƒанный кредитный продукт уже опубликован.");
		if(product.getConditions().isEmpty())
			throw new BusinessLogicException("¬ы не можете опубликовать кредитный продукт, дл€ которого не заданы услови€ предоставлени€ кредита в одной или нескольких валютах. ѕожалуйста, добавьте услови€ кредитного продукта");
		product.setPublicity(Publicity.PUBLISHED);
		service.update(product);
	}

	public void unpublish() throws Exception
	{
		if(product.getPublicity() == Publicity.UNPUBLISHED)
			throw new BusinessLogicException("ƒанный кредитный продукт уже сн€т с публикации.");
		product.setPublicity(Publicity.UNPUBLISHED);
		service.update(product);
	}
}
