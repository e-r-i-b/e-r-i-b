package com.rssl.phizic.web.templatesconfirmsetting;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.payment.templatesconfirmsetting.TemplatesConfirmSettingsOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.ext.sbrf.DepartmentViewUtil;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;
import org.apache.struts.action.ActionForward;

/**
 * @author vagin
 * @ created 02.10.2012
 * @ $Author$
 * @ $Revision$
 * Настройка подтверждения операция по шаблону СМС-паролем
 */
public class TemplatesConfirmSettingsAction extends EditPropertiesActionBase<TemplatesConfirmSettingsOperation>
{
	@Override
	protected TemplatesConfirmSettingsOperation getEditOperation() throws BusinessException
	{
		return createOperation("TemplatesConfirmSettingsOperation","TemplatesConfirmSettings");
	}

	@Override
	protected void initializeViewOperation(TemplatesConfirmSettingsOperation operation, EditPropertiesFormBase form) throws BusinessException, BusinessLogicException
	{
		((TemplatesConfirmSettingsForm) form).setRegion(DepartmentViewUtil.getTbNumberByIdConsiderMultiBlock(form.getId()));
		operation.initialize(getCategory(form), form.getFieldKeys(), form.getId());
	}

	@Override
	protected Form getEditForm(EditFormBase frm)
	{
		((TemplatesConfirmSettingsForm) frm).setRegion(DepartmentViewUtil.getTbNumberByIdConsiderMultiBlock(frm.getId()));
		return super.getEditForm(frm);
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm)
	{
		ActionForward forward = new ActionForward(getCurrentMapping().findForward(FORWARD_SUCCESS));
		forward.setPath(forward.getPath() + "?id=" + frm.getId());
		return forward;
	}
}
