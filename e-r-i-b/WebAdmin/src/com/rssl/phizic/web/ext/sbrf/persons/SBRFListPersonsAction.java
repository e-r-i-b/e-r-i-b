package com.rssl.phizic.web.ext.sbrf.persons;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.person.GetPersonsListOperation;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.persons.ListPersonsAction;
import com.rssl.phizic.web.persons.ListPersonsForm;

import java.util.Collections;
import java.util.Map;

/**
 * @author malafeevsky
 * @ created 17.08.2009
 * @ $Author$
 * @ $Revision$
 */
public class SBRFListPersonsAction extends ListPersonsAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		GetPersonsListOperation operation = null;
		if (checkAccess(GetPersonsListOperation.class, "PersonsViewing"))
			operation = createOperation(GetPersonsListOperation.class, "PersonsViewing");
		else if (checkAccess(GetPersonsListOperation.class, "PersonManagement"))
			operation = createOperation(GetPersonsListOperation.class, "PersonManagement");
		else
			operation = createOperation(GetPersonsListOperation.class, "MailManagment");
		return operation;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		super.fillQuery(query, filterParams);
		query.setParameter("fio", filterParams.get("fio"));
		query.setParameter("mobile_phone", filterParams.get("mobile_phone"));
		query.setParameter("loginId", filterParams.get("loginId"));
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws BusinessException
	{
		ListPersonsForm frm = (ListPersonsForm) form;
		GetPersonsListOperation op = (GetPersonsListOperation) operation;

		frm.setField("isEsbSupported", op.isEsbSupported());
		frm.setAllowedTB(op.getAllowedTB());
		super.updateFormAdditionalData(frm, operation);
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		if (frm.isFromStart() && frm.getField("excludeStartTest") == null)
		{
			frm.setData( Collections.emptyList() );
			frm.setFilters( filterParams );
			updateFormAdditionalData(frm, operation);	
		}
		else
		{
			super.doFilter(filterParams, operation, frm);
		}
	}
}
