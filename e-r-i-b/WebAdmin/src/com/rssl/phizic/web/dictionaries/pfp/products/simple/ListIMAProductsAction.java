package com.rssl.phizic.web.dictionaries.pfp.products.simple;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author akrenev
 * @ created 24.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListIMAProductsAction extends ListProductsAction
{
	protected String getListOperationName()
	{
		return "ListIMAProductsOperation";
	}

	protected String getRemoveOperationName()
	{
		return "RemoveIMAProductOperation";
	}

	protected String getQueryName(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return "ima.list";
	}
}
