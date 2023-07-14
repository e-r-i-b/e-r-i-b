package com.rssl.phizic.web.schemes;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.schemes.SharedAccessScheme;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.scheme.EditSchemeOperation;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.Map;

/**
 * @author akrenev
 * @ created 22.08.2014
 * @ $Author$
 * @ $Revision$
 *
 * экшен редактирования схемы прав сотрудника
 */

public class EditEmployeeSchemeAction extends EditSchemeAction
{
	@Override
	protected void updateFormAdditionalData(EditFormBase editForm, EditEntityOperation editOperation) throws BusinessException
	{
		EditSchemeOperation operation = (EditSchemeOperation) editOperation;
		EditEmployeeSchemeForm form = (EditEmployeeSchemeForm) editForm;

		SharedAccessScheme scheme = operation.getEntity();

		updateMainInformation(form, operation, scheme);

		form.setGroups(operation.getServicesGroups());
		form.setAllowEditCASchemes(operation.isAllowEditCASchemes());
		form.setVspEmployee(operation.isVSPEmployee());
	}

	@Override
	protected void updateForm(EditFormBase frm, Object entity)
	{
		SharedAccessScheme scheme = (SharedAccessScheme) entity;
		frm.setField(EditSchemeForm.CA_ADMIN_SCHEME, scheme.isCAAdminScheme());
		frm.setField(EditSchemeForm.VSP_EMPLOYEE_SCHEME, scheme.isVSPEmployeeScheme());
	}

	@Override
	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase form, Map<String, Object> validationResult) throws BusinessException
	{
		super.updateOperationAdditionalData(editOperation, form, validationResult);

		EditSchemeOperation operation = (EditSchemeOperation) editOperation;
		SharedAccessScheme scheme = operation.getEntity();

		// Изменять настройку может только администратор ЦА
		if (operation.isAllowEditCASchemes())
		{
			scheme.setCAAdminScheme((Boolean) validationResult.get(EditSchemeForm.CA_ADMIN_SCHEME));
		}

		//устанавливать признак: Доступно сотрудникам ВСП может сотрудник без пометки: Сотрудник ВСП.
		if (!operation.isVSPEmployee())
		{
			scheme.setVSPEmployeeScheme((Boolean) validationResult.get(EditSchemeForm.VSP_EMPLOYEE_SCHEME));
		}
	}
}
