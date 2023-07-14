package com.rssl.phizic.web.common.mobile.finances.categories;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.finances.CategoriesGraphOperation;
import com.rssl.phizic.operations.finances.FinanceFilterData;
import com.rssl.phizic.operations.finances.FinancesOperationBase;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.client.finances.FinanceFormBase;
import com.rssl.phizic.web.component.DatePeriodFilter;
import org.apache.struts.action.ActionForm;

import java.util.*;

/**
 * @author Balovtsev
 * @since 28.12.2014.
 */
public class ShowDiagramMobileAction extends ShowOperationCategoriesMobileAction
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("diagram", "filter");
		return map;
	}

	@Override
	protected Form getFilterForm(ActionForm frm)
	{
		return ShowDiagramMobileForm.DIAGRAM_FILTER_FORM;
	}

	@Override
	protected MapValuesSource getMapValueSource(FinanceFormBase frm)
	{
		ShowDiagramMobileForm form   = (ShowDiagramMobileForm) frm;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("to",         DateHelper.formatDateToStringOnPattern(Calendar.getInstance(), "MM.yyyy"));
		map.put("from",             form.getFrom());
		map.put("selectedId",       form.getSelectedId());
		map.put("onlyCash",         form.isOnlyCash());
		map.put("internalTransfer", form.isInternalTransfer());
		map.put("transfer",         form.isTransfer());
		map.put("noTransfer",       form.isNotTransfer());
		return new MapValuesSource(map);
	}

	protected void doFilter(Map filterParams, FinancesOperationBase operation, FinanceFormBase frm) throws BusinessException, BusinessLogicException
	{
		FinanceFilterData filterData = new FinanceFilterData();
		filterData.setFromDate(DateHelper.toCalendar((Date) filterParams.get(DatePeriodFilter.FROM_DATE)));
		filterData.setToDate  (Calendar.getInstance());

		filterData.setOnlyCash((Boolean) filterParams.get("onlyCash"));
		filterData.setTransfer((Boolean) filterParams.get("transfer"));
		filterData.setNoTransfer((Boolean) filterParams.get("noTransfer"));
		filterData.setInternalTransfer((Boolean) filterParams.get("internalTransfer"));

		doFilter(filterData, operation, frm);
	}

	protected void doFilter(FinanceFilterData filterData, FinancesOperationBase operation, FinanceFormBase frm) throws BusinessException
	{
		ShowDiagramMobileForm    form = (ShowDiagramMobileForm) frm;
		CategoriesGraphOperation o    = (CategoriesGraphOperation) operation;

		if (!form.isHasErrors())
		{
			form.setDiagramAbstracts( o.getDiagramAbstracts(filterData, StringHelper.isEmpty(form.getSelectedId())));
		}
	}
}
