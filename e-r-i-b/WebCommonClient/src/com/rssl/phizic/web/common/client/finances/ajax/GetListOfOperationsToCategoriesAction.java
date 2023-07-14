package com.rssl.phizic.web.common.client.finances.ajax;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.finances.GetListOfOperationsOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.component.MonthPeriodFilter;

import java.util.Map;

/**
 * Получение списка операций на странице "Расходы"
 * @author lepihina
 * @ created 21.01.14
 * @ $Author$
 * @ $Revision$
 */
public class GetListOfOperationsToCategoriesAction extends ListActionBase
{
	protected boolean isAjax()
	{
		return true;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		GetListOfOperationsOperation operation = createOperation(GetListOfOperationsOperation.class, "CategoriesCostsService");
		operation.initialize();
		return operation;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return GetListOfOperationsForm.FILTER_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		GetListOfOperationsOperation op = (GetListOfOperationsOperation)operation;

		filterParams.put("income", false);
		MonthPeriodFilter dateParams = new MonthPeriodFilter(filterParams);
		Map<String, Object> normalizedFilterParams = dateParams.normalize().getParameters();

		GetListOfOperationsForm form = (GetListOfOperationsForm) frm;
		int currentPage = form.getSearchPage();
		int resOnPage = form.getResOnPage();
		form.setCardOperations(op.getCardOperationsToCategories(normalizedFilterParams, resOnPage + 1, resOnPage * currentPage, false));
		frm.setFilters(normalizedFilterParams);
	}

	protected boolean getEmptyErrorPage()
	{
		return true;
	}
}
