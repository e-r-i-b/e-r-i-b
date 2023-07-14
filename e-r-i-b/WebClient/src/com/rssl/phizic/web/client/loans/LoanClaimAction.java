package com.rssl.phizic.web.client.loans;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loans.products.LoanProduct;
import com.rssl.phizic.business.loans.products.LoanProductService;

import java.util.*;

/**
 * @author Krenev
 * @ created 11.01.2008
 * @ $Author$
 * @ $Revision$
 */
public class LoanClaimAction extends LoanClaimActionBase
{
	protected Map<String, LoanProduct> buildLoanProductsMap() throws BusinessException
	{
		Map<String, LoanProduct> products = new HashMap<String, LoanProduct>();
		LoanProductService loanProductService = new LoanProductService();
		for (LoanProduct product : loanProductService.getAllProducts())
		{
				products.put(product.getId().toString(), product);
		}
		return products;
	}
}
