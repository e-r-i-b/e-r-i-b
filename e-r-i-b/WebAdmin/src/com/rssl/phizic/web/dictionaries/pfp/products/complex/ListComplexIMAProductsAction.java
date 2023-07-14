package com.rssl.phizic.web.dictionaries.pfp.products.complex;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author akrenev
 * @ created 11.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListComplexIMAProductsAction extends ListComplexProductsAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation("ListComplexIMAInvestmentProductsOperation");
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation("RemoveComplexIMAInvestmentProductOperation");
	}

	protected String getQueryName(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return "imaList";
	}
}