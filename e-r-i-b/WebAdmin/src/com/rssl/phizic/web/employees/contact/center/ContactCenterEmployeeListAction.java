package com.rssl.phizic.web.employees.contact.center;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.employees.GetEmployeeListOperation;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionForward;

import java.util.Calendar;
import java.util.Map;

/**
 * @author komarov
 * @ created 10.10.13 
 * @ $Author$
 * @ $Revision$
 *
 * Ёкшен получени€ списка сотрудников контактного центра
 */

public class ContactCenterEmployeeListAction extends SaveFilterParameterAction
{
	private static final String MULTI_BLOCK_LIST_FORWARD_NAME = "MultiBlockList";
	private static final String CONTACT_CENTER_EMPLOYEE_LIST_OPERATION = "GetContactCenterEmployeeListOperation";

	@Override
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException
	{
		return createOperation(CONTACT_CENTER_EMPLOYEE_LIST_OPERATION);
	}

	@Override
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ContactCenterEmployeeListForm.FILTER_FORM;
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws BusinessException
	{
		ContactCenterEmployeeListForm frm = (ContactCenterEmployeeListForm) form;
		GetEmployeeListOperation op = (GetEmployeeListOperation) operation;
		frm.setContactCenterAreas(op.getAreas());
	}

	@Override
	protected String getQueryName(ListFormBase frm)
	{
		return GetEmployeeListOperation.CONTACT_CENTER_EMPLOYEE_LIST_QUERY_NAME;
	}

	@Override
	protected void fillQuery(Query query, Map<String, Object> filterParams)
	{
		query.setParameter(GetEmployeeListOperation.BLOCKED_UNTIL_CONTACT_CENTER_EMPLOYEE_LIST_QUERY_PARAMETER, Calendar.getInstance().getTime());
		query.setParameter(GetEmployeeListOperation.EMPLOYEE_ID_CONTACT_CENTER_EMPLOYEE_LIST_QUERY_PARAMETER,   filterParams.get(ContactCenterEmployeeListForm.EMPLOYEE_ID_PARAMETER_NAME));
		query.setParameter(GetEmployeeListOperation.FIO_CONTACT_CENTER_EMPLOYEE_LIST_QUERY_PARAMETER,           filterParams.get(ContactCenterEmployeeListForm.FIO_PARAMETER_NAME));
		query.setParameter(GetEmployeeListOperation.AREA_ID_CONTACT_CENTER_EMPLOYEE_LIST_QUERY_PARAMETER,       filterParams.get(ContactCenterEmployeeListForm.AREA_ID_PARAMETER_NAME));
	}

	@Override
	protected ActionForward createActionForward(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		if (isMailMultiBlockMode())
			return getCurrentMapping().findForward(MULTI_BLOCK_LIST_FORWARD_NAME);
		return super.createActionForward(frm);
	}

	private boolean isMailMultiBlockMode()
	{
		return MultiBlockModeDictionaryHelper.isMailMultiBlockMode();
	}
}
