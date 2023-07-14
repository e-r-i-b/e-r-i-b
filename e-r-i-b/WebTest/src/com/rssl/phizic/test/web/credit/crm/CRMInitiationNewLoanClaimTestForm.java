package com.rssl.phizic.test.web.credit.crm;

import org.apache.struts.action.ActionForm;

/**
 * Форма инициации из CRM создания новой заявки в ETSM
 * @ author: Gololobov
 * @ created: 05.03.15
 * @ $Author$
 * @ $Revision$
 */
public class CRMInitiationNewLoanClaimTestForm extends ActionForm
{
	//Уникальный идентификатор сообщения ERIBSendETSMApplRq
	private String EribSendETSMApplRqUID;
	//XML-сообщение ERIBSendETSMApplRq
	private String EribSendETSMApplRqXML;

	public String getEribSendETSMApplRqUID()
	{
		return EribSendETSMApplRqUID;
	}

	public void setEribSendETSMApplRqUID(String eribSendETSMApplRqUID)
	{
		EribSendETSMApplRqUID = eribSendETSMApplRqUID;
	}

	public String getEribSendETSMApplRqXML()
	{
		return EribSendETSMApplRqXML;
	}

	public void setEribSendETSMApplRqXML(String eribSendETSMApplRqXML)
	{
		EribSendETSMApplRqXML = eribSendETSMApplRqXML;
	}
}
