package com.rssl.phizic.web.persons;

import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.person.GetDeletedPersonListOperation;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.common.forms.Form;
import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 27.10.2006
 * @ $Author$
 * @ $Revision$
 */
public class ListArchivePersonsAction  extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(GetDeletedPersonListOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListPersonsForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
		query.setParameter("employeeLoginId", employeeData.getEmployee().getLogin().getId());
		
		query.setParameter("documentSeries", filterParams.get("documentSeries") );
		query.setParameter("documentNumber", filterParams.get("documentNumber"));
		query.setParameter("documentType", filterParams.get("documentType"));

		query.setParameter("agreementNumber", filterParams.get("agreementNumber"));
		query.setParameter("patrName", filterParams.get("patrName"));
		query.setParameter("firstName", filterParams.get("firstName"));
		query.setParameter("surName", filterParams.get("surName"));

		query.setParameter("pinEnvelopeNumber",filterParams.get("pinEnvelopeNumber"));
		query.setParameter("state", Person.DELETED);

		query.setMaxResults(webPageConfig().getListLimit() + 1);
    }
}
