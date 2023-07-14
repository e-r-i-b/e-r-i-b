package com.rssl.phizic.web.employees;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.employees.EditPersonalManagerInformationOperation;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author akrenev
 * @ created 12.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Редактирование своих настроек менеджера
 */

public class EditSelfManagerInformationAction extends EditSimpleManagerInformationAction
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditPersonalManagerInformationForm form = (EditPersonalManagerInformationForm) frm;
		EditPersonalManagerInformationOperation operation = createOperation(EditPersonalManagerInformationOperation.class, "SelfPersonalManagerInformationManagement");
		operation.initialize();
		form.setEmployee(operation.getEntity());
		form.setChannels(operation.getChannels());
		return operation;
	}

	@Override
	protected Form getEditForm(EditFormBase frm)
	{
		return EditPersonalManagerInformationForm.EDIT_SELF_MANAGER_INFORMATION_FORM;
	}
}
