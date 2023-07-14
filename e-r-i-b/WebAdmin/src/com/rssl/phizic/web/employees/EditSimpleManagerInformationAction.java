package com.rssl.phizic.web.employees;

import com.rssl.phizic.gate.employee.Employee;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.Map;

/**
 * @author akrenev
 * @ created 12.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Базовый экшен настроек менеджера
 */

public abstract class EditSimpleManagerInformationAction extends EditActionBase
{
	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
	 	Employee employee = (Employee)entity;
		employee.setManagerEMail((String)data.get(EditPersonalManagerInformationForm.EMAIL_FIELD_NAME));
		employee.setManagerPhone((String)data.get(EditPersonalManagerInformationForm.PHONE_FIELD_NAME));
		employee.setManagerLeadEMail((String)data.get(EditPersonalManagerInformationForm.LEAD_EMAIL_FIELD_NAME));
		employee.setManagerChannel((String)data.get(EditPersonalManagerInformationForm.CHANNEL_FIELD_NAME));
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		Employee employee = (Employee)entity;

		frm.setField(EditPersonalManagerInformationForm.CHANNEL_FIELD_NAME,     employee.getManagerChannel());
		frm.setField(EditPersonalManagerInformationForm.ID_FIELD_NAME,          employee.getManagerId());
		frm.setField(EditPersonalManagerInformationForm.EMAIL_FIELD_NAME,       employee.getManagerEMail());
		frm.setField(EditPersonalManagerInformationForm.PHONE_FIELD_NAME,       employee.getManagerPhone());
		frm.setField(EditPersonalManagerInformationForm.LEAD_EMAIL_FIELD_NAME,  employee.getManagerLeadEMail());
	}
}
