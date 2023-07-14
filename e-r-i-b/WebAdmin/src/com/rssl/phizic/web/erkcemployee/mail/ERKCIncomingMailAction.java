package com.rssl.phizic.web.erkcemployee.mail;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ERKCNotFoundClientBusinessException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.ext.sbrf.mail.ViewMailOperation;
import com.rssl.phizic.operations.mail.ListMailOperation;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.mail.ListMailAction;
import com.rssl.phizic.web.util.ERKCEmployeeUtil;

import java.util.HashMap;
import java.util.Map;


/**
 * @author akrenev
 * @ created 02.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class ERKCIncomingMailAction extends ListMailAction
{
	protected Map<String, Object> getDefaultFilterParameters(ListFormBase frm, ListEntitiesOperation operation)
				throws BusinessException, BusinessLogicException
	{
		Map<String, Object> filterParameters = new HashMap<String, Object>();
		filterParameters.put("showNew", true);
		filterParameters.put("showAnswer", false);
		filterParameters.put("showReceived", true);
		filterParameters.put("showDraft", false);

		Employee employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
		filterParameters.put("firstNameEmpl", employee.getFirstName());
		filterParameters.put("surNameEmpl", employee.getSurName());
		filterParameters.put("patrNameEmpl", employee.getPatrName());

		return filterParameters;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListMailOperation.class, "MailManagementUseClientForm");
	}

	protected ViewMailOperation createViewOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ViewMailOperation.class, "MailManagementUseClientForm");
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ERKCIncomingMailForm.ERKC_FILTER_FORM;
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation)
	{
		ERKCEmployeeUtil.addUserDataForERKC(form, ERKCEmployeeUtil.getUserInfo());
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws Exception
	{
		Map<String, Object> filterParameters = ERKCEmployeeUtil.getUserInfo();
		if (filterParameters.isEmpty())
		{
			throw new ERKCNotFoundClientBusinessException("Для того чтобы выполнить поиск записей, перейдите к списку входящих писем из анкеты клиента.");
		}
		filterParams.putAll(filterParameters);
		super.doFilter(filterParams, operation, form);
	}
}
