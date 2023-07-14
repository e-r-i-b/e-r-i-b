package com.rssl.phizic.web.common.mobile.finances.lt7;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.finances.FinanceFilterData;
import com.rssl.phizic.operations.finances.FinancesOperationBase;
import com.rssl.phizic.operations.finances.ShowCategoryAbstractOperation;
import com.rssl.phizic.web.common.client.finances.FinanceFormBase;
import com.rssl.phizic.web.common.client.finances.ShowCategoryAbstractFormInterface;

/**
 * @author Mescheryakova
 * @ created 02.04.2012
 * @ $Author$
 * @ $Revision$
 */

public class ShowCategoryAbstractMobileAction extends FinanceFilterMobileActionBase
{
	public FinancesOperationBase createFinancesOperation(FinanceFormBase frm) throws BusinessLogicException, BusinessException
	{
		ShowCategoryAbstractFormInterface form = (ShowCategoryAbstractFormInterface) frm;
		Long categoryId = form.getCategoryId();
		if (categoryId == null)
			throw new BusinessException("Не указан categoryId");

		ShowCategoryAbstractOperation operation = createOperation(ShowCategoryAbstractOperation.class);
		operation.initialize(categoryId);
		return operation;
	}

	protected void doFilter(FinanceFilterData filterData, FinancesOperationBase operation, FinanceFormBase frm) throws BusinessException
	{
		ShowCategoryAbstractFormInterface form = (ShowCategoryAbstractFormInterface) frm;
		ShowCategoryAbstractOperation showOperation = (ShowCategoryAbstractOperation) operation;

		form.setCategory(showOperation.getCategory());
		form.setData(showOperation.getCardOperations(filterData));
	}
}
