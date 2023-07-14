package com.rssl.phizic.web.common.socialApi.finances.lt7;

import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.finances.CategoriesListOperation;
import com.rssl.phizic.operations.finances.FinanceFilterData;
import com.rssl.phizic.operations.finances.FinancesOperationBase;
import com.rssl.phizic.web.common.client.finances.FinanceFormBase;
import com.rssl.phizic.web.component.DatePeriodFilter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dorzhinov
 * @ created 03.05.2012
 * @ $Author$
 * @ $Revision$
 */
public class CategoriesListMobileAction extends FinanceFilterMobileActionBase
{
    protected MapValuesSource getMapValueSource(FinanceFormBase frm)
    {
        FinanceMobileFormBase form = (FinanceMobileFormBase) frm;
		Map<String, Object> source = new HashMap<String, Object>();

        //заглушка для фильтра, кроме incomeType
		source.put(DatePeriodFilter.FROM_DATE, "");
		source.put(DatePeriodFilter.TO_DATE, "");
		source.put(DatePeriodFilter.TYPE_PERIOD, DatePeriodFilter.TYPE_PERIOD_MONTH);
		source.put("incomeType", form.getIncomeType());
		source.put("showOtherAccounts", "");
		source.put("showCash", "");

		return new MapValuesSource(source);
    }

    protected FinancesOperationBase createFinancesOperation(FinanceFormBase frm) throws BusinessLogicException, BusinessException
	{
        CategoriesListOperation operation = createOperation(CategoriesListOperation.class);
		operation.initialize();
		return operation;
	}

    protected void doFilter(FinanceFilterData filterData, FinancesOperationBase operation, FinanceFormBase frm) throws BusinessException
	{
		CategoriesListOperation op = (CategoriesListOperation) operation;
		frm.setData(op.getCategories(filterData));
	}
}
