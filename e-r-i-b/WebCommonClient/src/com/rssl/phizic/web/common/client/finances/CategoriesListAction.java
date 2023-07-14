package com.rssl.phizic.web.common.client.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.finances.CategoriesListOperation;
import com.rssl.phizic.operations.finances.FinanceFilterData;
import com.rssl.phizic.operations.finances.FinancesOperationBase;

import java.util.Map;

/**
 * @author Pankin
 * @ created 15.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class CategoriesListAction extends FinanceFilterActionBase
{

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keyMethodMap = super.getKeyMethodMap();
		keyMethodMap.put("button.category.edit", "filter");
		return keyMethodMap;
	}

	public FinancesOperationBase createFinancesOperation(FinanceFormBase frm) throws BusinessLogicException, BusinessException
	{
		CategoriesListOperation operation = createOperation(CategoriesListOperation.class);
		operation.initialize();
		return operation;
	}

	public void doFilter(FinanceFilterData filterData, FinancesOperationBase operation, FinanceFormBase frm) throws BusinessException
	{
		CategoriesListOperation op = (CategoriesListOperation) operation;
		frm.setData(op.getCategories(filterData));
	}
}
