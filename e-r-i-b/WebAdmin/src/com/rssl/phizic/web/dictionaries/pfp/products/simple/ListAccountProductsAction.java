package com.rssl.phizic.web.dictionaries.pfp.products.simple;

import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author akrenev
 * @ created 22.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListAccountProductsAction extends ListProductsAction
{
	protected String getListOperationName()
	{
		return "ListAccountProductsOperation";
	}

	protected String getRemoveOperationName()
	{
		return "RemoveAccountProductOperation";
	}

	protected String getQueryName(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return "account.list";
	}
}
