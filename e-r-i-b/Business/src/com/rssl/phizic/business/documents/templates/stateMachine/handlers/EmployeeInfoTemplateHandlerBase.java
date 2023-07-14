package com.rssl.phizic.business.documents.templates.stateMachine.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizgate.common.documents.payments.EmployeeInfoImpl;
import com.rssl.phizgate.common.documents.payments.PersonNameImpl;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;
import com.rssl.phizic.gate.payments.owner.PersonName;

/**
 * Базовый класс хендлеров шаблонов документов (сотрудников)
 *
 * @author khudyakov
 * @ created 15.07.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class EmployeeInfoTemplateHandlerBase<SO extends StateObject> extends TemplateHandlerBase<SO>
{
	protected EmployeeInfo getEmployeeInfo() throws DocumentException
	{
		Employee employee = getEmployee();
		return new EmployeeInfoImpl(employee.getLogin().getId().toString(), getPersonName(employee));
	}

	private Employee getEmployee()
	{
		return EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
	}

	private PersonName getPersonName(Employee employee)
	{
		return new PersonNameImpl(employee.getSurName(), employee.getFirstName(), employee.getPatrName());
	}
}
