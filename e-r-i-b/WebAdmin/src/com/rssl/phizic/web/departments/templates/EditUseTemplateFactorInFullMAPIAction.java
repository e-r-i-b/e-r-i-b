package com.rssl.phizic.web.departments.templates;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.departments.templates.EditUseTemplateFactorInFullMAPIOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;
import org.apache.struts.action.ActionForward;

/**
 * Настройка использования кратности суммы при оплате по шаблону в full-версии mAPI
 * @author Dorzhinov
 * @ created 15.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class EditUseTemplateFactorInFullMAPIAction extends EditPropertiesActionBase<EditUseTemplateFactorInFullMAPIOperation>
{
	@Override
	protected EditUseTemplateFactorInFullMAPIOperation getEditOperation() throws BusinessException
	{
		return createOperation(EditUseTemplateFactorInFullMAPIOperation.class);
	}

	@Override
	protected void initializeViewOperation(EditUseTemplateFactorInFullMAPIOperation operation, EditPropertiesFormBase form) throws BusinessException, BusinessLogicException
	{
		operation.initialize(getCategory(form), form.getFieldKeys(), form.getId());
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm)
	{
		ActionForward forward = new ActionForward(getCurrentMapping().findForward(FORWARD_SUCCESS));
		forward.setPath(forward.getPath() + "?id=" + frm.getId());
		return forward;
	}
}
