package com.rssl.phizic.web.common.mobile.finances.categories;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.operations.finances.FinanceFilterData;
import com.rssl.phizic.operations.finances.FinancesOperationBase;
import com.rssl.phizic.web.common.client.finances.CategoriesListAction;
import com.rssl.phizic.web.common.client.finances.FinanceFormBase;
import org.apache.struts.action.ActionForm;

import java.util.HashMap;
import java.util.Map;

/**
 * Справочник категорий
 * @author Dorzhinov
 * @ created 30.09.2013
 * @ $Author$
 * @ $Revision$
 */
public class CategoriesListMobileAction extends CategoriesListAction
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("filter", "filter");
		return map;
	}

	protected Form getFilterForm(ActionForm frm)
	{
		return CategoriesListMobileForm.FORM;
	}

	protected MapValuesSource getMapValueSource(FinanceFormBase frm)
	{
		CategoriesListMobileForm form = (CategoriesListMobileForm) frm;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("incomeType", form.getIncomeType());
		return new MapValuesSource(map);
	}

	protected void doFilter(Map filterParams, FinancesOperationBase operation, FinanceFormBase frm) throws Exception
	{
		FinanceFilterData filterData = new FinanceFilterData();
		filterData.setIncome(filterParams.get("incomeType").equals(FinanceFormBase.INCOME_TYPE));

		doFilter(filterData, operation, frm);
	}
}
