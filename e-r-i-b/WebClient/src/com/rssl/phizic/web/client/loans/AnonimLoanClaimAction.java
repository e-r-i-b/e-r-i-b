package com.rssl.phizic.web.client.loans;

import com.rssl.phizic.business.loans.products.LoanProduct;
import com.rssl.phizic.business.loans.products.LoanProductService;
import com.rssl.phizic.business.BusinessException;

import java.util.Map;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 09.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class AnonimLoanClaimAction extends LoanClaimActionBase
{

	protected Map<String, LoanProduct> buildLoanProductsMap() throws BusinessException
	{
		Map<String, LoanProduct> products = new HashMap<String, LoanProduct>();
		LoanProductService loanProductService = new LoanProductService();
		for (LoanProduct product : loanProductService.getAllProducts())
		{
			if(product.getAnonymousClaim())
				products.put(product.getId().toString(), product);
		}
		return products;
	}

}
