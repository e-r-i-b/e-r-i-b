package com.rssl.phizic.web.common.socialApi.payments;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.web.actions.FilterPeriodHelper;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.common.client.payments.ClientDocumentsListActionBase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rydvanskiy
 * @ created 03.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowMobilePaymentListAction extends ClientDocumentsListActionBase
{
	//формируем поля фильтрации для валидации
	protected FieldValuesSource getFilterValuesSource(ListFormBase form)
	{
		ShowMobilePaymentListForm frm = (ShowMobilePaymentListForm) form;
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put(FilterPeriodHelper.FROM_DATE_FIELD_NAME, frm.getFrom());
		filter.put(FilterPeriodHelper.TO_DATE_FIELD_NAME, frm.getTo());
		return new MapValuesSource(filter);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ShowMobilePaymentListForm.FORM;
	}

	protected Map<String,Object> getDefaultFilterParameters(ListFormBase frm, ListEntitiesOperation operation)
	{
		Map<String,Object> parameters = new HashMap<String, Object>();
		parameters.put(FilterPeriodHelper.PERIOD_TYPE_FIELD_NAME, FilterPeriodHelper.PERIOD_TYPE_MONTH);
		return parameters;
	}
}
