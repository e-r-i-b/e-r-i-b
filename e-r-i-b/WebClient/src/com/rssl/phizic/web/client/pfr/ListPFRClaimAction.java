package com.rssl.phizic.web.client.pfr;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.Period;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.pfr.ListPFRClaimOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.FilterPeriodHelper;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.*;

/**
 * @author Dorzhinov
 * @ created 18.02.2011
 * @ $Author $
 * @ $Revision $
 */
public class ListPFRClaimAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ListPFRClaimOperation operation = createOperation(ListPFRClaimOperation.class);
		operation.initialize();
		return operation;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListPFRClaimForm.FILTER_FORM;
	}

	protected Map<String,Object> getDefaultFilterParameters(ListFormBase frm, ListEntitiesOperation operation)
	{
		Map<String,Object> parameters = new HashMap<String, Object>();
		parameters.put(FilterPeriodHelper.PERIOD_TYPE_FIELD_NAME, FilterPeriodHelper.PERIOD_TYPE_WEEK);

		Calendar toDate   = new GregorianCalendar();
		Calendar fromDate = DateHelper.getPreviousWeek(toDate);

		parameters.put("fromDate", fromDate.getTime());
		parameters.put("toDate",   toDate.getTime());
		return parameters;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		Period period = FilterPeriodHelper.getPeriod(
				(String) filterParams.get(FilterPeriodHelper.PERIOD_TYPE_FIELD_NAME),
				(Date) filterParams.get(FilterPeriodHelper.FROM_DATE_FIELD_NAME),
				(Date) filterParams.get(FilterPeriodHelper.TO_DATE_FIELD_NAME)
		);

		query.setParameter("dateFrom", period.getFromDate());
		query.setParameter("dateTo", period.getToDate());

		query.setMaxResults(webPageConfig().getListLimit() + 1);
	}

	protected void updateFormAdditionalData(ListFormBase frm, ListEntitiesOperation op) throws Exception
	{
		ListPFRClaimForm form = (ListPFRClaimForm) frm;
		ListPFRClaimOperation operation = (ListPFRClaimOperation) op;
		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		form.setUser(person);
		form.setPfrLink(operation.getPFRLink());
		form.setError(operation.isError());
	}
}
