package com.rssl.phizic.web.loans.products;

import org.apache.struts.action.ActionForm;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.business.loans.products.LoanProduct;

/**
 * @author gladishev
 * @ created 04.12.2007
 * @ $Author$
 * @ $Revision$
 */

public class ListLoanProductForm extends ListFormBase<LoanProduct>
{
	private Map<Long, String> loanKinds;

	public Map<Long, String> getLoanKinds()
	{
		return loanKinds;
	}

	public void setLoanKinds(Map<Long, String> loanKinds)
	{
		this.loanKinds = loanKinds;
	}
}

