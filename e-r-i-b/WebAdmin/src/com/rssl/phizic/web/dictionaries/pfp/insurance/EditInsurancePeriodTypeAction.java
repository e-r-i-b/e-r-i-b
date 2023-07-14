package com.rssl.phizic.web.dictionaries.pfp.insurance;

import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.insurance.EditInsurancePeriodTypeOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.PeriodType;
import com.rssl.common.forms.Form;

import java.util.Map;

/**
 * @author akrenev
 * @ created 20.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditInsurancePeriodTypeAction extends EditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditInsurancePeriodTypeOperation operation = createOperation(EditInsurancePeriodTypeOperation.class);
		operation.initialize(frm.getId());
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditInsurancePeriodTypeForm.EDIT_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		PeriodType type = (PeriodType) entity;
		type.setName((String) data.get("name"));
		type.setMonths((Long) data.get("months"));
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		PeriodType type = (PeriodType) entity;
		frm.setField("name", type.getName());
		frm.setField("months", type.getMonths());
	}
}
