package com.rssl.phizic.web.dictionaries.billing;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.dictionaries.billing.DublicateBillingException;
import com.rssl.phizic.business.dictionaries.billing.TemplateState;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.billing.EditBillingOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizicgate.manager.routing.Adapter;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.Map;

/**
 * @author akrenev
 * @ created 10.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class EditBillingAction extends EditActionBase
{
	private static final String FORWARD_LIST  = "List";

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditBillingOperation operation = createOperation(EditBillingOperation.class);
		Long id = frm.getId();
		if ((id != null) && (id != 0))
		{
			operation.initialize(id);
		}
		else
		{
			operation.initializeNew();
		}
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditBillingForm.EDIT_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		Billing billing = (Billing) entity;
		billing.setName((String) data.get("name"));
		billing.setCode((String) data.get("code"));
		billing.setSynchKey((String) data.get("code"));
		billing.setNeedUploadJBT((Boolean) data.get("needUploadJBT"));
		billing.setTemplateState(TemplateState.valueOf((String) data.get("templateState")));
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		Billing billing = (Billing) entity;
		frm.setField("name", billing.getName());
		frm.setField("code", billing.getCode());
		frm.setField("needUploadJBT", billing.isNeedUploadJBT());
		frm.setField("templateState", billing.getTemplateState() == null ? TemplateState.ACTIVE : billing.getTemplateState().name());
	}

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		EditBillingOperation op = (EditBillingOperation) operation;
		frm.setField("userName",    op.getUserName());
		frm.setField("connectMode", op.getConnectMode());
		frm.setField("requisites",  op.getRequisites());
		frm.setField("comission",   op.getComission());
		frm.setField("timeout",     op.getTimeout());
		Adapter adapter = op.getAdapter();
		if (adapter != null)
		{
			frm.setField("adapterUUID", adapter.getUUID());
			frm.setField("adapterName", adapter.getName());
		}
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		EditBillingOperation op = (EditBillingOperation) editOperation;
		EditBillingForm form = (EditBillingForm) editForm;
		op.addAdapter((String) form.getField("adapterUUID"));
	}


	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm) throws Exception
	{
		EditBillingOperation op = (EditBillingOperation) operation;
		ActionForward actionForward = new ActionForward();
		try
		{
			//Фиксируем данные, введенные пользователем
			addLogParameters(frm);
			op.save();
			actionForward = mapping.findForward(FORWARD_LIST);
		}
		catch(DublicateBillingException ex)
		{
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ex.getMessage(), false));
			saveErrors(currentRequest(), msgs);
		    actionForward = mapping.findForward(FORWARD_START);
		}
		return actionForward;
	}
}
