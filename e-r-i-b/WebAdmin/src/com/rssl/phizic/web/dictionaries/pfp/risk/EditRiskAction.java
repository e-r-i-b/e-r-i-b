package com.rssl.phizic.web.dictionaries.pfp.risk;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.risk.Risk;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.risk.EditRiskOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.Map;

/**
 * @author akrenev
 * @ created 15.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Ёкшен редактировани€ риска
 */

public class EditRiskAction extends EditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditRiskOperation operation = createOperation(EditRiskOperation.class);
		Long id = form.getId();
		if (id == null)
			operation.initializeNew();
		else
			operation.initialize(id);
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditRiskForm.EDIT_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		Risk risk = (Risk) entity;
		risk.setName((String) data.get(EditRiskForm.NAME_FIELD_NAME));
	}

	protected void updateForm(EditFormBase form, Object entity) throws Exception
	{
		Risk risk = (Risk) entity;
		form.setField(EditRiskForm.NAME_FIELD_NAME, risk.getName());

	}
}
