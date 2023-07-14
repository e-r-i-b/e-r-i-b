package com.rssl.phizic.web.common.socialApi.finances.operations;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.finances.GetListOfOperationsOperation;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.common.client.finances.ajax.GetListOfOperationsAction;
import com.rssl.phizic.web.component.DatePeriodFilter;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * АЛФ: История операций
 * @author Dorzhinov
 * @ created 02.10.2013
 * @ $Author$
 * @ $Revision$
 */
public class ListFinanceOperationsMobileAction extends GetListOfOperationsAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		GetListOfOperationsOperation operation = null;
		if (checkAccess(GetListOfOperationsOperation.class, "FinanceOperationsService"))
			operation = createOperation(GetListOfOperationsOperation.class, "FinanceOperationsService");
		else
			operation = createOperation(GetListOfOperationsOperation.class, "CategoriesCostsService");

		operation.initialize();
		return operation;
	}

	protected boolean isAjax()
	{
		return false;
	}

	protected boolean getEmptyErrorPage()
	{
		return false;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListFinanceOperationsMobileForm.FORM;
	}

	protected FieldValuesSource getFilterValuesSource(ListFormBase frm)
	{
		ListFinanceOperationsMobileForm form = (ListFinanceOperationsMobileForm) frm;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("from", form.getFrom());
		map.put("to", form.getTo());
		map.put("categoryId", form.getCategoryId());
		map.put("selectedCardIds", StringUtils.join(form.getSelectedCardId(), ";"));
		map.put("income", form.getIncome());
		map.put("showCash", form.getShowCash());
		map.put("showOtherAccounts", form.getShowOtherAccounts());
		map.put("showCashPayments", form.getShowCashPayments());
		return new MapValuesSource(map);
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		GetListOfOperationsOperation op = (GetListOfOperationsOperation)operation;
		ListFinanceOperationsMobileForm form = (ListFinanceOperationsMobileForm) frm;

		filterParams.put(DatePeriodFilter.TYPE_PERIOD, DatePeriodFilter.TYPE_PERIOD_PERIOD);
		filterParams.put(DatePeriodFilter.FROM_DATE, filterParams.remove("from"));
		filterParams.put(DatePeriodFilter.TO_DATE, filterParams.remove("to"));
		filterParams.put("showCreditCards", "true");
		filterParams.put("openPageDate", Calendar.getInstance().getTime());
		filterParams.put("onlyAvailableCategories", "false");
		filterParams.put("hidden", BooleanUtils.toBooleanObject(form.getHidden()));
		filterParams.put("country",form.getCountry());
		filterParams.put("excludeCategories", form.getExcludeCategories());
		DatePeriodFilter dateParams = new DatePeriodFilter(filterParams);
		Map<String, Object> normalizedFilterParams = dateParams.normalize().getParameters();

		int maxResults = form.getPaginationSize() == 0 ? Integer.MAX_VALUE : form.getPaginationSize();
		form.setCardOperations(op.getCardOperationsToCategories(normalizedFilterParams, maxResults, form.getPaginationOffset(), false));
	}
}
