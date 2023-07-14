package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.AccountClosingPayment;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.business.finances.targets.AccountTargetService;

/**
 * Хендлер заявки на закрытие вклада. Если вклад привязан к цели, то цель необходимо удалить при закрытии вклада.
 * @author mihaylov
 * @ created 06.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class DeleteClientAccountTargetHandler extends BusinessDocumentHandlerBase
{
	private static final AccountTargetService targetService = new AccountTargetService();	

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if(!(document instanceof AccountClosingPayment))
			return;

		AccountClosingPayment payment = (AccountClosingPayment) document;
		try
		{
			BusinessDocumentOwner documentOwner = payment.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			AccountTarget accountTarget = targetService.findTargetByAccountForLogin(payment.getAccountNumber(), documentOwner.getLogin());
			if(accountTarget == null)
				return;
			targetService.remove(accountTarget);
		}
		catch (BusinessException e)
		{
			log.error("Не удалось удалить цель клиента при закрытии вклада",e);
		}
	}
}
