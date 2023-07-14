package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizgate.common.documents.payments.EmployeeInfoImpl;
import com.rssl.phizgate.common.documents.payments.PersonNameImpl;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;

import java.util.Calendar;

/**
 * @ author: Vagin
 * @ created: 21.05.2013
 * @ $Author
 * @ $Revision
 * ’ендлер установки информации о подтверждении шаблона
 */
public class SetConfirmEmployeeInfoTemplateHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if(!(document instanceof TemplateDocument))
			throw new DocumentException("ѕодтверждаемый документ не €вл€етс€ шаблоном.");
		TemplateDocument template = (TemplateDocument) document;
		try
		{
			template.setConfirmedEmployeeInfo(getEmployeeInfo());
			template.setAdditionalOperationChannel(CreationType.internet);     //всегда internet
			template.setAdditionalOperationDate(Calendar.getInstance());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	private EmployeeInfo getEmployeeInfo() throws BusinessException
	{
		Employee empl = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
		PersonNameImpl personName = new PersonNameImpl();
		personName.setFullName(empl.getFullName());
		EmployeeInfoImpl info = new EmployeeInfoImpl();
		info.setGuid(empl.getLogin().getId().toString());
		info.setPersonName(personName);
		return info;
	}
}
