package com.rssl.phizic.web.common.socialApi.finances.categories;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategoryGraphAbstract;
import com.rssl.phizic.operations.finances.CategoriesGraphOperation;
import com.rssl.phizic.operations.finances.FinanceFilterData;
import com.rssl.phizic.operations.finances.FinancesOperationBase;
import com.rssl.phizic.utils.BooleanHelper;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.client.finances.FinanceFilterActionBase;
import com.rssl.phizic.web.common.client.finances.FinanceFormBase;
import com.rssl.phizic.web.component.DatePeriodFilter;
import com.rssl.phizic.web.component.PeriodFilter;
import org.apache.struts.action.ActionForm;

import java.util.*;

/**
 * Структура расходов по категориям
 * @author Dorzhinov
 * @ created 01.10.2013
 * @ $Author$
 * @ $Revision$
 */
public class ShowOperationCategoriesMobileAction extends FinanceFilterActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("filter", "filter");
		return map;
	}

	protected FinancesOperationBase createFinancesOperation(FinanceFormBase frm) throws BusinessLogicException, BusinessException
	{
		CategoriesGraphOperation operation = createOperation(CategoriesGraphOperation.class);
		ShowOperationCategoriesMobileForm form = (ShowOperationCategoriesMobileForm) frm;
		operation.initialize(form.getSelectedId());
		return operation;
	}

	protected Form getFilterForm(ActionForm frm)
	{
		return ShowOperationCategoriesMobileForm.createMobileFilterForm();
	}

	protected MapValuesSource getMapValueSource(FinanceFormBase frm)
	{
		ShowOperationCategoriesMobileForm form = (ShowOperationCategoriesMobileForm) frm;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("from", form.getFrom());
		map.put("to", form.getTo());
		map.put("showCash", form.getShowCash());
		map.put("selectedId", form.getSelectedId());
		map.put("excludeCategories", form.getExcludeCategories());
		map.put("showCashPayments", form.getShowCashPayments());
		map.put("country", form.getCountry());
		return new MapValuesSource(map);
	}

	protected PeriodFilter getDefaultPeriodFilter(Map<String, Object> filterParams)
	{
		filterParams.put(DatePeriodFilter.FROM_DATE, filterParams.remove("from"));
		filterParams.put(DatePeriodFilter.TO_DATE, filterParams.remove("to"));
		filterParams.put(DatePeriodFilter.TYPE_PERIOD, DatePeriodFilter.TYPE_PERIOD_PERIOD);
		return new DatePeriodFilter(filterParams);
	}

	protected Map<String, Object> getDefaultFilterParameters(FinanceFormBase frm, FinancesOperationBase operation)
	{
		Map<String, Object> filterParams = new HashMap<String, Object>();
		Calendar onDate = DateHelper.endOfDay(Calendar.getInstance());
		filterParams.put(DatePeriodFilter.FROM_DATE, DateHelper.getFirstDayOfMonth(onDate).getTime());
		filterParams.put(DatePeriodFilter.TO_DATE, onDate.getTime());
		filterParams.put(DatePeriodFilter.TYPE_PERIOD, DatePeriodFilter.TYPE_PERIOD_PERIOD);
		filterParams.put("showCash", "true");
		filterParams.put("showCashPayments", "true");
		return filterParams;
	}

	protected void doFilter(Map filterParams, FinancesOperationBase operation, FinanceFormBase frm) throws BusinessException, BusinessLogicException
	{
		FinanceFilterData filterData = new FinanceFilterData();
		filterData.setFromDate(DateHelper.toCalendar((Date) filterParams.get(DatePeriodFilter.FROM_DATE)));
		filterData.setToDate(DateHelper.toCalendar((Date) filterParams.get(DatePeriodFilter.TO_DATE)));
		Object showCash = filterParams.get("showCash");
		filterData.setCash(showCash instanceof Boolean ? (Boolean) showCash : Boolean.parseBoolean((String) showCash));
		filterData.setIncome(false);
		filterData.setTransfer(true);
		Object showCashPayments = filterParams.get("showCashPayments");
		filterData.setShowCashPayments(showCashPayments != null ? BooleanHelper.asBoolean(showCashPayments) : null);
		filterData.setOperationCountry((String)filterParams.get("country"));
		doFilter(filterData, operation, frm);
	}

	protected void doFilter(FinanceFilterData filterData, FinancesOperationBase operation, FinanceFormBase frm) throws BusinessException
	{
		ShowOperationCategoriesMobileForm form = (ShowOperationCategoriesMobileForm) frm;
		CategoriesGraphOperation op = (CategoriesGraphOperation)operation;

		List<CardOperationCategoryGraphAbstract> outcomeOperations = op.getCategoriesGraphData(filterData, op.getRoot().getSelectedIds(), form.getExcludeCategories());
		form.setOutcomeOperations(outcomeOperations);
		form.setElement(op.getRoot().getList().get(0));
	}
}
