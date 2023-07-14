package com.rssl.phizic.web.payments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.operations.forms.ViewDocumentOperation;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: vagin
 * @ created: 27.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class PrintEmployeePaymentAction extends ViewEmployeePaymentAction
{
	public ActionForward start(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse responce) throws BusinessException, BusinessLogicException
	{
		EditFormBase form = (EditFormBase)frm;
		ViewDocumentOperation operation = createViewEntityOperation(form);
		updateFormData(operation,form);
		Employee employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
		form.setField("employeeDepartmentId", employee.getDepartmentId());
		form.setField("employeeFullName", employee.getFullName());
		ActionForward forward = mapping.findForward("print" + operation.getMetadata().getName());
		return (forward != null)? forward : mapping.findForward(FORWARD_START);
	}
}
