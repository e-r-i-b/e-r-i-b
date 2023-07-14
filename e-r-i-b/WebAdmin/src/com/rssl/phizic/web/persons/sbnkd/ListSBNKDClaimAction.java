package com.rssl.phizic.web.persons.sbnkd;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.sberbankForEveryDay.ListSBNKDClaimOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * список за€вок —бербанк на каждый день дл€ клиента
 * @author basharin
 * @ created 19.02.15
 * @ $Author$
 * @ $Revision$
 */

public class ListSBNKDClaimAction extends SaveFilterParameterAction
{
	@Override
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ListSBNKDClaimOperation operation = createOperation(ListSBNKDClaimOperation.class);
		ListSBNKDClaimForm form = (ListSBNKDClaimForm) frm;
		operation.initialize(form.getPerson());
		return operation;
	}

	@Override
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListSBNKDClaimForm.FILTER_FORM;
	}

	@Override
	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("fromDate", filterParams.get(ListSBNKDClaimForm.FROM_DATE_FIELD));
		Date toDate = (Date)filterParams.get(ListSBNKDClaimForm.TO_DATE_FIELD);
		if (toDate != null)
			toDate = DateHelper.add(DateHelper.setTime(toDate, 0, 0, 0, 0), 0, 0, 1);
		query.setParameter("toDate", toDate);
	}

	@Override
	protected Map<String, Object> getDefaultFilterParameters(ListFormBase frm, ListEntitiesOperation operation)
			throws BusinessException, BusinessLogicException
	{
		Map<String, Object> params = new HashMap<String, Object>();
		Calendar calendar = Calendar.getInstance();
		params.put(ListSBNKDClaimForm.TO_DATE_FIELD, calendar.getTime());
		calendar.add(Calendar.WEEK_OF_MONTH, -1);
		params.put(ListSBNKDClaimForm.FROM_DATE_FIELD, calendar.getTime());
		return params;
	}

	@Override
	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws BusinessException, BusinessLogicException
	{
		ListSBNKDClaimForm frm = (ListSBNKDClaimForm)form;
		ListSBNKDClaimOperation op = (ListSBNKDClaimOperation) operation;

		frm.setActivePerson(op.getActivePerson());
	}

}