package com.rssl.phizic.web.advertising;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.advertising.AdvertisingState;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.advertising.ListAdvertisingBlockOperation;
import com.rssl.phizic.operations.advertising.RemoveAdvertisingBlockOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lepihina
 * @ created 23.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class ListAdvertisingBlockAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListAdvertisingBlockOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListAdvertisingBlockForm.FILTER_FORM;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveAdvertisingBlockOperation.class);
	}

	protected Map<String, Object> getDefaultFilterParameters(ListFormBase frm, ListEntitiesOperation operation)
			throws BusinessException, BusinessLogicException
	{
		Map<String, Object> filterParameters = new HashMap<String, Object>();
		filterParameters.put("fromDate", Calendar.getInstance().getTime());

		return filterParameters;
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
		query.setParameter("orderIndex", filterParams.get("orderIndex"));
		query.setParameter("departmentId", filterParams.get("departmentId"));
		Employee employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
		query.setParameter("loginId", employee.getLogin().getId());
	}

}
