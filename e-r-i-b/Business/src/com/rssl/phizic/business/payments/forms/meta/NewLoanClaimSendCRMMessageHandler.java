package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.ikfl.crediting.CRMMessageService;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;

/**
 * Хендлер отправки в CRM сообщения о создании новой заявки на кредит
 *
 * @ author: Gololobov
 * @ created: 10.03.15
 * @ $Author$
 * @ $Revision$
 */
public class NewLoanClaimSendCRMMessageHandler extends BusinessDocumentHandlerBase
{
	private static final CRMMessageService crmService = new CRMMessageService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof ExtendedLoanClaim))
			throw new DocumentException("Неверный тип платежа id=" + document.getId() + " (Ожидается ExtendedLoanClaim)");
		ExtendedLoanClaim claim = (ExtendedLoanClaim) document;

		//Если не отправляем заявку в ВСП, заявка должна быть заполнена полностью
		if (!stateMachineEvent.getNextState().equals("VISIT_OFFICE"))
			if (!claim.getCompleteAppFlag())
				return;

		try
		{
			//Отправка в CRM сообщения о создании новой заявки на кредит
			crmService.createNewExtendedLoanClaim(claim);
		}
		catch (Exception e)
		{
			throw new DocumentException(e);
		}

	}
}
