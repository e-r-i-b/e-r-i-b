package com.rssl.phizic.web.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.dictionaries.receivers.EditPaymentReceiverOperationBase;
import com.rssl.phizic.operations.dictionaries.receivers.EditPaymentReceiverOperationAdmin;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.web.common.dictionaries.EditPaymentReceiverActionBase;
import com.rssl.phizic.web.common.dictionaries.EditPaymentReceiverForm;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForward;

/**
 * @author Krenev
 * @ created 12.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class EditPaymentReceiverAdminAction extends EditPaymentReceiverActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditPaymentReceiverForm frm = (EditPaymentReceiverForm) form;
		EditPaymentReceiverOperationBase operation = createOperation(EditPaymentReceiverOperationAdmin.class, getCurrentMapping().getParameter());

		Long id = frm.getId();
		if (id != null && id != 0)
		{
			operation.initialize(id, frm.getPerson());
		}
		else
		{
			operation.initializeNew(frm.getKind(), frm.getPerson());
		}
		return operation;
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm)
	{
		EditPaymentReceiverOperationBase op = (EditPaymentReceiverOperationBase) operation;

		String kind = op.getEntity().getKind();
		if (kind.equals("B") || kind.equals("J"))
		{
			kind = "JB";
		}
		return new ActionForward(getCurrentMapping().findForward(FORWARD_SUCCESS).getPath() + "?person=" + op.getPerson().getId() + "&" + "kind=" + kind, true);
	}
}
