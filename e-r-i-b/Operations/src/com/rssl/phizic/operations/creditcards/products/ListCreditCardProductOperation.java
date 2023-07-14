package com.rssl.phizic.operations.creditcards.products;

import com.rssl.phizic.business.creditcards.products.CreditCardProduct;
import com.rssl.phizic.business.creditcards.products.CreditCardProductService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.List;

/**
 * @author Dorzhinov
 * @ created 15.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class ListCreditCardProductOperation extends OperationBase implements ListEntitiesOperation
{
	private static final CreditCardProductService creditCardProductService = new CreditCardProductService();

	public List<CreditCardProduct> getAvailableProducts(String currency, String creditLimit, String include) throws BusinessException
	{
		return creditCardProductService.getAvailableProducts(currency, creditLimit, include);
	}
}
