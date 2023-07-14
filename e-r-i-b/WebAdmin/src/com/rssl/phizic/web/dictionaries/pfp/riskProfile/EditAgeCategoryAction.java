package com.rssl.phizic.web.dictionaries.pfp.riskProfile;

import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.riskProfile.EditAgeCategoryOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.AgeCategory;
import com.rssl.common.forms.Form;

import java.util.Map;

/**
 * @author akrenev
 * @ created 02.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditAgeCategoryAction extends EditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditAgeCategoryOperation operation = createOperation(EditAgeCategoryOperation.class);
		operation.initialize(frm.getId());
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditAgeCategoryForm.EDIT_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		AgeCategory category = (AgeCategory) entity;
		category.setMinAge((Long) data.get("minAge"));
		category.setMaxAge((Long) data.get("maxAge"));
		category.setWeight((Long) data.get("weight"));
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		AgeCategory category = (AgeCategory) entity;
		frm.setField("minAge", category.getMinAge());
		frm.setField("maxAge", category.getMaxAge());
		frm.setField("weight", category.getWeight());
	}
}
