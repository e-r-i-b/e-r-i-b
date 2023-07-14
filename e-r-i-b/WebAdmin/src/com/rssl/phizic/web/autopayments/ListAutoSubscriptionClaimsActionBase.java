package com.rssl.phizic.web.autopayments;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.autosubscription.ListAutoSubscriptionClaimOperation;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.payments.ViewDocumentListActionBase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author khudyakov
 * @ created 08.10.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class ListAutoSubscriptionClaimsActionBase extends ViewDocumentListActionBase
{
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListAutoSubscriptionClaimForm.FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException
	{
		super.fillQuery(query, filterParams);

		query.setParameter("name", filterParams.get("name"));
		query.setParameter("receiver", filterParams.get("receiver"));
		query.setParameterList("state", ((String) filterParams.get("state")).split(","));
		query.setParameterList("сlaimType", ((String) filterParams.get("сlaimType")).split(","));
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws Exception
	{
		ListAutoSubscriptionClaimForm frm = (ListAutoSubscriptionClaimForm) form;

		if ((frm.getFields().size() != 0 || frm.getFilters().size() != 0) && !frm.isErrorFilter())
			frm.setFromStart(false);
		//Грузим данные только если кликнули фильтр и не было ошибок валидации полей фильтра
		if (!frm.isFromStart())
		{
			super.doFilter(filterParams, operation, frm);
		}
		else
		{
			frm.setData(new ArrayList(0));
			frm.setFilters(filterParams);
			updateFormAdditionalData(frm, operation);
		}
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws BusinessException
	{
		ListAutoSubscriptionClaimForm frm = (ListAutoSubscriptionClaimForm) form;
		ListAutoSubscriptionClaimOperation op = (ListAutoSubscriptionClaimOperation) operation;
		frm.setActivePerson(op.getPerson());
		frm.setListLimit(webPageConfig().getListLimit());
	}

	protected void forwardFilterFail(ListFormBase form, ListEntitiesOperation operation) throws Exception
	{
		ListAutoSubscriptionClaimForm frm = (ListAutoSubscriptionClaimForm)form;
		frm.setErrorFilter(true);
		super.forwardFilterFail(form, operation);
	}

	protected Map<String,Object> getDefaultFilterParameters(ListFormBase frm, ListEntitiesOperation operation)
	{
		Map<String, Object> result = new HashMap<String, Object>();

		// две недели назад(14 дней)
		Calendar twoWeekAgo = Calendar.getInstance();
		// вычитаем 13 дней, т.к. fromTime ставится в начало дня, а toTime в конец дня, таким образом получается 14 дней
		twoWeekAgo.add(Calendar.DAY_OF_MONTH, -13);
		result.put("fromDate", String.format("%1$td.%1$tm.%1$tY", twoWeekAgo));

		Calendar currentDate = Calendar.getInstance();
		result.put("toDate", String.format("%1$td.%1$tm.%1$tY", currentDate));

		result.put("state", "ALL");

		return result;
	}
}
