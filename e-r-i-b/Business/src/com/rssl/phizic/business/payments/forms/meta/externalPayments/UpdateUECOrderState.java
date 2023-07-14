package com.rssl.phizic.business.payments.forms.meta.externalPayments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.shop.ExternalPaymentService;
import com.rssl.phizic.business.shop.Order;

/**
 * User: Moshenko
 * Date: 21.04.14
 * Time: 16:33
 * Хендлер устанавливаем статус оповещение в NOT_SEND, для заказов UEC.
 */
public class UpdateUECOrderState extends BusinessDocumentHandlerBase
{
	public void process(StateObject stateObject, StateMachineEvent stateMachineEvent) throws DocumentException
	{
		if (!(stateObject instanceof BusinessDocument))
			throw new DocumentException("BusinessDocument");
		BusinessDocument document = (BusinessDocument) stateObject;
		try
		{
			Order order = DocumentHelper.getUECOrder(document);
			if (order == null)
				return;

			if (order.getNotificationStatus() == null)
				ExternalPaymentService.get().refreshOrderNotificationStatus(order);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
