package com.rssl.phizic.web.templatesfactor;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.payment.templatesfactor.TemplatesFactorOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.ext.sbrf.DepartmentViewUtil;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;
import com.rssl.phizic.web.templatesconfirmsetting.TemplatesConfirmSettingsForm;
import org.apache.struts.action.ActionForward;

/**
 * @author vagin
 * @ created 06.06.2012
 * @ $Author$
 * @ $Revision$
 * Ёкшен дл€ задани€ кратности суммы шаблонов
 */
public class TemplatesFactorAction extends EditPropertiesActionBase<TemplatesFactorOperation>
{
	@Override
	protected TemplatesFactorOperation getEditOperation() throws BusinessException
	{
		return createOperation(TemplatesFactorOperation.class, "TemplatesFactorManagement");
	}

	@Override
	protected void initializeViewOperation(TemplatesFactorOperation operation, EditPropertiesFormBase form) throws BusinessException, BusinessLogicException
	{
		((TemplatesFactorForm) form).setRegion(DepartmentViewUtil.getTbNumberByIdConsiderMultiBlock(form.getId()));
		operation.initialize(getCategory(form), form.getFieldKeys(), form.getId());
	}

	@Override
	protected Form getEditForm(EditFormBase frm)
	{
		((TemplatesFactorForm) frm).setRegion(DepartmentViewUtil.getTbNumberByIdConsiderMultiBlock(frm.getId()));
		return super.getEditForm(frm);
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm)
	{
		ActionForward forward = new ActionForward(getCurrentMapping().findForward(FORWARD_SUCCESS));
		forward.setPath(forward.getPath() + "?id=" + frm.getId());
		return forward;
	}
}
