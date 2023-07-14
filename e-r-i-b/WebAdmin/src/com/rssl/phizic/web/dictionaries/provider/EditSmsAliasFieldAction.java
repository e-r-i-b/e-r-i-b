package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.provider.ServiceProviderSmsAlias;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.provider.EditSmsAliasFieldOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionRedirect;

import java.util.Map;

/**
 * User: Moshenko
 * Date: 23.05.2013
 * Time: 16:31:48
 * Экшен изменения параметров полей платежа в разрезе смс алиасов 
 */
public class EditSmsAliasFieldAction extends EditActionBase
{

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditSmsAliasFieldActionForm form = (EditSmsAliasFieldActionForm)frm;
		EditSmsAliasFieldOperation op = createOperation("EditSmsAliasFieldOperation");
		op.initialize(form.getId());
		return op;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		EditSmsAliasFieldActionForm form = (EditSmsAliasFieldActionForm) frm;
		return form.createFilterForm(frm);
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
	}

	@Override
	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		EditSmsAliasFieldActionForm form = (EditSmsAliasFieldActionForm) frm;
		EditSmsAliasFieldOperation op = (EditSmsAliasFieldOperation)operation;
		form.setAlias((ServiceProviderSmsAlias)op.getEntity());

	}

	@Override
	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		EditSmsAliasFieldActionForm frm = (EditSmsAliasFieldActionForm) editForm;
		EditSmsAliasFieldOperation op = (EditSmsAliasFieldOperation)editOperation;
		op.updateFields(frm.getAliasFieldEditable(),validationResult);
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		EditSmsAliasFieldActionForm form = (EditSmsAliasFieldActionForm) frm;
		ActionRedirect redirect = new ActionRedirect(getCurrentMapping().findForward(FORWARD_SUCCESS).getPath());
		redirect.addParameter("id", form.getId());
		return redirect;
	}
}
