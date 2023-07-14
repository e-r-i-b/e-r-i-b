package com.rssl.phizic.web.common.dictionaries;

import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.operations.dictionaries.EditBankOperation;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.common.forms.Form;

import java.util.Map;

import org.apache.struts.action.*;

/**
 * @author Pakhomova
 * @ created 15.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class EditBankAction extends EditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditBankForm frm = (EditBankForm) form;
		EditBankOperation operation = createOperation("EditBankOperation");

		if (frm.getSynchKey() == null)
			operation.initializeNew();
		else
		{
			operation.initialize(frm.getSynchKey());
		}
		return operation;
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
		ResidentBank bank = (ResidentBank) entity;

		frm.setField("name", bank.getName());
		frm.setField("bic", bank.getBIC());
		frm.setField("corrAccount", bank.getAccount());
		frm.setField("shortName", bank.getShortName());
		frm.setField("place", bank.getPlace());

	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditBankForm.EDIT_BANK_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data)
	{
		ResidentBank bank = (ResidentBank) entity;

		bank.setName((String) data.get("name"));
		bank.setAccount((String) data.get("corrAccount"));
		bank.setBIC((String) data.get("bic"));
		bank.setShortName((String) data.get("shortName"));
		bank.setPlace((String) data.get("place"));
	}

	@Override
	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		EditBankOperation op = (EditBankOperation)operation;
		ResidentBank bank = op.getEntity();
		ActionForward forward = getCurrentMapping().findForward(FORWARD_SUCCESS);
		return new ActionForward(forward.getPath() +"?synchKey=" + bank.getSynchKey(), true);
	}

	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm)
	{
		try
		{
			return super.doSave(operation, mapping, frm);
		}
		catch (Exception ignore)
		{
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Такой банк уже существует в системе", false));
			saveErrors(currentRequest(), msgs);

			return mapping.findForward(FORWARD_START);
		}
	}

}