package com.rssl.phizic.web.common.socialApi.finances.financeCalendar;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.finances.FinanceFilterData;
import com.rssl.phizic.operations.finances.FinancesOperationBase;
import com.rssl.phizic.operations.finances.financeCalendar.ShowFinanceCalendarOperation;
import com.rssl.phizic.utils.BooleanHelper;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.client.finances.FinanceFormBase;
import com.rssl.phizic.web.common.client.finances.financeCalendar.ShowFinanceCalendarAction;
import com.rssl.phizic.web.component.CalendarMonthPeriodFilter;
import org.apache.struts.action.ActionForm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lepihina
 * @ created 05.05.14
 * $Author$
 * $Revision$
 */
public class ShowFinanceCalendarMobileAction extends ShowFinanceCalendarAction
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("filter", "filter");
		return map;
	}

	protected FinancesOperationBase createFinancesOperation(FinanceFormBase frm) throws BusinessLogicException, BusinessException
	{
		ShowFinanceCalendarOperation operation = createOperation(ShowFinanceCalendarOperation.class);
		ShowFinanceCalendarMobileForm form = (ShowFinanceCalendarMobileForm) frm;
		operation.initialize(form.getSelectedId());
		return operation;
	}

	protected Form getFilterForm(ActionForm frm)
	{
		return ShowFinanceCalendarMobileForm.createFilterForm();
	}

	protected MapValuesSource getMapValueSource(FinanceFormBase frm)
	{
		ShowFinanceCalendarMobileForm form = (ShowFinanceCalendarMobileForm) frm;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("onDate", form.getOnDate());
		map.put("showCash", form.getShowCash());
		map.put("selectedId", form.getSelectedId());
		map.put("excludeCategories", form.getExcludeCategories());
		map.put("showCashPayments", form.getShowCashPayments());
		map.put("country", form.getCountry());
		return new MapValuesSource(map);
	}

	protected Map<String, Object> getDefaultFilterParameters(FinanceFormBase frm, FinancesOperationBase operation)
	{
		Map<String, Object> filterParameters = new HashMap<String, Object>();
		filterParameters.put("onDate", DateHelper.getCurrentDate().getTime());
		CalendarMonthPeriodFilter filter = new CalendarMonthPeriodFilter(filterParameters);
		filterParameters.put("showCash", "true");
		filterParameters.put("showCashPayments", "true");
		return filter.normalize().getParameters();
	}

	protected void doFilter(Map filterParams, FinancesOperationBase operation, FinanceFormBase frm) throws BusinessException, BusinessLogicException
	{
		ShowFinanceCalendarMobileForm form = (ShowFinanceCalendarMobileForm) frm;
		FinanceFilterData filterData = new FinanceFilterData();
		filterData.setFromDate(DateHelper.toCalendar((Date) filterParams.get(CalendarMonthPeriodFilter.FROM_DATE)));
		filterData.setToDate(DateHelper.toCalendar((Date) filterParams.get(CalendarMonthPeriodFilter.TO_DATE)));
		Object showCash = filterParams.get("showCash");
		filterData.setCash(showCash != null ? BooleanHelper.asBoolean(showCash) : null);
		Object showCashPayments = filterParams.get("showCashPayments");
		filterData.setShowCashPayments(showCashPayments != null ? BooleanHelper.asBoolean(showCashPayments) : null);
		filterData.setExcludeCategories(form.getExcludeCategories());
		filterData.setOperationCountry((String)filterParams.get("country"));
		doFilter(filterData, operation, frm);
	}
}
