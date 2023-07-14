package com.rssl.phizic.web.employees;

import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.employees.PrintEmployeeInfoOperation;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.web.common.ViewActionBase;
import com.rssl.phizic.web.common.EditFormBase;


/**
 * @author Roshka
 * @ created 29.09.2005
 * @ $Author$
 * @ $Revision$
 */
public class EmployeePrintAction extends ViewActionBase
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException
	{
		EmployeeEditForm frm = (EmployeeEditForm) form;
		PrintEmployeeInfoOperation operation  = createOperation(PrintEmployeeInfoOperation.class);
		operation.initialize(frm.getEmployeeId());

		return operation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form)
			throws BusinessException, BusinessLogicException
	{
            EmployeeEditForm frm = (EmployeeEditForm) form;

			Employee employee = (Employee) operation.getEntity();
            frm.setField("firstName", employee.getFirstName());
            frm.setField("patrName", employee.getPatrName());
            frm.setField("surName", employee.getSurName());
            frm.setField("info", employee.getInfo());
            frm.setField("login", employee.getLogin());
    }
}
