package com.rssl.phizic.web.common.mobile.finances.lt7;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.finances.CategoriesGraphOperation;
import com.rssl.phizic.operations.finances.FinanceFilterData;
import com.rssl.phizic.operations.finances.FinancesOperationBase;
import com.rssl.phizic.web.common.client.finances.FinanceFormBase;

/**
 * @author Mescheryakova
 * @ created 30.03.2012
 * @ $Author$
 * @ $Revision$
 */

public class CategoriesGraphMobileAction extends FinanceFilterMobileActionBase
{
	public FinancesOperationBase createFinancesOperation(FinanceFormBase frm) throws BusinessLogicException, BusinessException
	{
		CategoriesGraphOperation op = createOperation(CategoriesGraphOperation.class);
		op.initialize();
		return op;
	}

	public void doFilter(FinanceFilterData filterData, FinancesOperationBase operation, FinanceFormBase frm) throws BusinessException
	{
		CategoriesGraphOperation op = (CategoriesGraphOperation)operation;
		frm.setData(op.getCategoriesGraphData(filterData));
	}
}
