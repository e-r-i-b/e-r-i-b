package com.rssl.phizic.web.quick.pay;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.advertising.AdvertisingState;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.quick.pay.ListQuickPaymentPanelOperation;
import com.rssl.phizic.operations.quick.pay.RemoveQuickPaymentPanelOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Список панелей быстрой оплаты
 * @author komarov
 * @ created 12.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListQuickPaymentPanelAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListQuickPaymentPanelOperation.class);  
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListQuickPaymentPanelForm.FILTER_FORM;
	}

	protected Map<String, Object> getDefaultFilterParameters(ListFormBase frm, ListEntitiesOperation operation)	throws BusinessException, BusinessLogicException
	{
		Map<String, Object> filterParameters = new HashMap<String, Object>();
		filterParameters.put("fromDate", Calendar.getInstance().getTime());

		return filterParameters;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveQuickPaymentPanelOperation.class);
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("fromDate", filterParams.get("fromDate"));
		Date toDate = (Date)filterParams.get("toDate");
		query.setParameter("toDate", toDate);

		String state = (String) filterParams.get("state");
		if (!StringHelper.isEmpty(state))
			query.setParameter("state", AdvertisingState.valueOf(state).toString());
		else
			query.setParameter("state", null);
		query.setParameter("name", filterParams.get("name"));
		query.setParameter("departmentId", filterParams.get("departmentId"));
	}
}
