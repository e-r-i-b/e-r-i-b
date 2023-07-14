package com.rssl.phizic.business.payments.forms.meta.handlers.basket.subscription;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.dictionaries.pages.staticmessages.StaticMessage;
import com.rssl.phizic.business.dictionaries.pages.staticmessages.StaticMessagesService;
import com.rssl.phizic.business.documents.payments.DelayAutoSubscriptionPayment;
import com.rssl.phizic.business.services.ServiceService;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.payments.longoffer.DelayCardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Niculichev
 * @ created 13.07.15
 * @ $Author$
 * @ $Revision$
 */
public class AutoCreateInvoiceSubscriptionChecker extends BusinessDocumentHandlerBase<DelayAutoSubscriptionPayment>
{
	private static final String BASKET_MESSAGE_KEY = "clientinfo_message";
	private static final StaticMessagesService service = new StaticMessagesService();
	private static final ServiceService serviceService = new ServiceService();

	public void process(DelayAutoSubscriptionPayment document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		try
		{
			if(isAccessCreateInvoiceSubscription(document))
			{
				StaticMessage message = service.findByKey(BASKET_MESSAGE_KEY);
				if(message != null && StringHelper.isNotEmpty(message.getText()))
					stateMachineEvent.addMessage(message.getText());
			}
		}
		catch (Exception e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * Доступно ли и возможно ли создание автопоиска по заявке на приостановку автоплатежа
	 * @param claim заявка на приостановку автоплатежа
	 * @return true - доступно и возможно
	 */
	public static boolean isAccessCreateInvoiceSubscription(DelayCardPaymentSystemPaymentLongOffer claim) throws Exception
	{
		// только по выставленному счету
		if(claim.getSumType() != SumType.BY_BILLING)
			return false;

		// только если автоплатеж был раз в месяц или раз в квартал
		ExecutionEventType eventType = claim.getExecutionEventType();
		if(eventType != ExecutionEventType.ONCE_IN_MONTH && eventType != ExecutionEventType.ONCE_IN_QUARTER)
			return false;

		// нет прав - ничего не делаем
		if(!serviceService.isPersonServices(claim.getInternalOwnerId(), "PaymentBasketManagment"))
			return false;

		return true;
	}
}
