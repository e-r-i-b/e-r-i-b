package com.rssl.phizic.web.dictionaries.pfp.products.simple;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author akrenev
 * @ created 22.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListFundProductsAction extends ListProductsAction
{
	protected String getListOperationName()
	{
		return "ListFundProductsOperation";
	}

	protected String getRemoveOperationName()
	{
		return "RemoveFundProductOperation";
	}

	protected String getQueryName(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return "fund.list";
	}
}
