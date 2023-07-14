package com.rssl.phizic.web.common.mobile.finances.lt7;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.operations.finances.FinanceFilterData;
import com.rssl.phizic.operations.finances.FinancesOperationBase;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.client.finances.FinanceFilterActionBase;
import com.rssl.phizic.web.common.client.finances.FinanceFormBase;
import com.rssl.phizic.web.component.DatePeriodFilter;
import org.apache.struts.action.ActionForm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mescheryakova
 * @ created 02.04.2012
 * @ $Author$
 * @ $Revision$
 */

public abstract class  FinanceFilterMobileActionBase extends FinanceFilterActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("filter", "filter");
		return map;
	}

	protected Form getFilterForm(ActionForm frm)
	{
		return FinanceMobileFormBase.MOBILE_FILTER_FORM;
	}

	protected MapValuesSource getMapValueSource(FinanceFormBase frm)
	{
		FinanceMobileFormBase form = (FinanceMobileFormBase) frm;
		Map<String, Object> source = new HashMap<String, Object> ();

		source.put(DatePeriodFilter.FROM_DATE, form.getFrom());
		source.put(DatePeriodFilter.TO_DATE, form.getTo());
		source.put(DatePeriodFilter.TYPE_PERIOD, DatePeriodFilter.TYPE_PERIOD_PERIOD);
		source.put("incomeType", form.getIncomeType());
		source.put("showOtherAccounts", form.isShowOtherAccounts());
		source.put("showCash", form.isShowCash());

		return new MapValuesSource(source);
	}

	protected void doFilter(Map filterParams, FinancesOperationBase operation, FinanceFormBase frm) throws Exception
	{
		FinanceFilterData filterData = new FinanceFilterData();
		filterData.setFromDate( DateHelper.toCalendar((Date)filterParams.get(DatePeriodFilter.FROM_DATE)));
		filterData.setToDate(DateHelper.toCalendar((Date)filterParams.get(DatePeriodFilter.TO_DATE)));
		filterData.setCash(filterParams.get("showCash")!= null);
		filterData.setIncome(filterParams.get("incomeType").equals(FinanceFormBase.INCOME_TYPE));
		filterData.setOnlyOwnCards(filterParams.get("showOtherAccounts")== null);

		doFilter(filterData, operation, frm);
	}

}
