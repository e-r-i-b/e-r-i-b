package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.context.EmployeeContext;

/**
 * @author osminin
 * @ created 25.12.2012
 * @ $Author$
 * @ $Revision$
 * Хандлер для установки id сотрудника, подтверждающего платеж
 */
public class SetConfirmEmployeeIdHandler extends BusinessDocumentHandlerBase<BusinessDocument>
{
	public void process(BusinessDocument document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		Employee employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
		document.setConfirmedEmployeeLoginId(employee.getLogin().getId());
	}
}