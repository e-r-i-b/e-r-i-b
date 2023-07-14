package com.rssl.phizic.business.payments.forms.meta.handlers.basket.subscription;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.documents.payments.EditInvoiceSubscriptionClaim;
import com.rssl.phizic.common.types.basket.InvoiceSubscriptionState;

/**
 * @author saharnova
 * @ created 25.06.15
 * @ $Author$
 * @ $Revision$
 * Хендлер, возвращающий статус подписки (услуги) на изначальный при редактировании при смене статуса на EXECUTED или REFUSED
 */

public class UpdateEditInvoiceSubscriptionStateHandler extends BusinessDocumentHandlerBase
{
	private static InvoiceSubscriptionService invoiceSubscriptionService = new InvoiceSubscriptionService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof EditInvoiceSubscriptionClaim))
		{
			throw new DocumentException("Ожидается EditInvoiceSubscriptionClaim");
		}
		try
		{
			EditInvoiceSubscriptionClaim claim = (EditInvoiceSubscriptionClaim) document;
			Long subscriptionId = claim.getInvoiceSubscriptionId();
			if (subscriptionId == null)
			{
				throw new IllegalArgumentException("Идентификатор подписки не может быть null.");
			}
			InvoiceSubscription subscription = invoiceSubscriptionService.findById(subscriptionId);
			if (subscription == null)
			{
				throw new DocumentException("Не найден авопоиск счетов по id " + subscriptionId);
			}
			String[] states = subscription.getBaseState().split(com.rssl.phizic.common.types.basket.Constants.STATE_DELIMITER);
			if(states.length == 1)
			{
				return;
			}
			invoiceSubscriptionService.updateState(InvoiceSubscriptionState.valueOf(states[0]), subscriptionId);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
