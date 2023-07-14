package com.rssl.phizic.web.employees;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.employee.Employee;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.employees.EditPersonalManagerInformationOperation;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.Map;

/**
 * @author komarov
 * @ created 11.09.2012
 * @ $Author$
 * @ $Revision$
 *
 * Редактирование настроек менеджера
 */

public class EditPersonalManagerInformationAction extends EditSimpleManagerInformationAction
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditPersonalManagerInformationForm form = (EditPersonalManagerInformationForm) frm;
		EditPersonalManagerInformationOperation operation = createOperation(EditPersonalManagerInformationOperation.class, "PersonalManagerInformationManagement");
		operation.initialize(form.getEmployeeId());
		form.setEmployee(operation.getEntity());
		form.setChannels(operation.getChannels());
		return operation;
	}

	@Override
	protected Form getEditForm(EditFormBase frm)
	{
		return EditPersonalManagerInformationForm.EDIT_PERSONAL_MANAGER_INFORMATION_FORM;
	}

	@Override
	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		super.updateEntity(entity, data);
		Employee employee = (Employee)entity;
		employee.setManagerId((String)data.get(EditPersonalManagerInformationForm.ID_FIELD_NAME));
	}
}
