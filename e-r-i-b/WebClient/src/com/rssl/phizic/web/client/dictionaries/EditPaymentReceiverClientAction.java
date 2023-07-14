package com.rssl.phizic.web.client.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.receivers.personal.PaymentPersonalReceiversDictionary;
import com.rssl.phizic.business.dictionaries.receivers.personal.generated.ReceiverDescriptor;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.receivers.ConfirmPaymentReceiverOperation;
import com.rssl.phizic.operations.dictionaries.receivers.EditPaymentReceiverOperationBase;
import com.rssl.phizic.operations.dictionaries.receivers.EditPaymentReceiverOperationClient;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.dictionaries.EditPaymentReceiverActionBase;
import com.rssl.phizic.web.common.dictionaries.EditPaymentReceiverForm;
import org.apache.struts.action.ActionForward;

/**
 * @author Krenev
 * @ created 12.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class EditPaymentReceiverClientAction extends EditPaymentReceiverActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditPaymentReceiverForm frm = (EditPaymentReceiverForm) form;
		EditPaymentReceiverOperationBase operation = createOperation(EditPaymentReceiverOperationClient.class, getCurrentMapping().getParameter());

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		Long id = frm.getId();
		Long personId = personData.getPerson().getId();
		if (id != null && id != 0)
		{
			operation.initialize(id, personId);
		}
		else
		{
			operation.initializeNew(frm.getKind(), personId);
		}
		return operation;
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		EditPaymentReceiverOperationBase op = (EditPaymentReceiverOperationBase) operation;
		if (checkAccess(ConfirmPaymentReceiverOperation.class))
		{
			return new ActionForward(getCurrentMapping().findForward(FORWARD_SUCCESS).getPath() + "?id=" + op.getEntity().getId(), true);
		}
		return new ActionForward(getCurrentMapping().findForward(FORWARD_SUCCESS).getPath() + "?kind=" + getReceiverKind(op), true);
	}

	protected String getReceiverKind(EditPaymentReceiverOperationBase operation) throws BusinessException
	{
		PaymentPersonalReceiversDictionary dictionary = new PaymentPersonalReceiversDictionary();
		ReceiverDescriptor receiverDescriptor = dictionary.getReceiverDescriptor(operation.getEntity().getKind());
		return receiverDescriptor.getListkind();
	}
}
