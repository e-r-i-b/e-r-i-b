package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizgate.common.payments.offline.basket.BasketRouteInfoService;
import com.rssl.phizic.BasketPaymentsListenerConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.payments.basket.CreateInvoiceSubscription;
import com.rssl.phizicgate.esberibgate.ws.generated.*;
import org.hibernate.exception.ConstraintViolationException;

/**
 * Сендер для отправки заявки на создание подписки получения инвойсов
 * @author niculichev
 * @ created 19.05.14
 * @ $Author$
 * @ $Revision$
 */
public class BillingPaymentDocumentInvoiceSubSender extends BillingPaymentDocumentAutoSubSender
{
	private static final String PREFIX = "BASKET|";
	private static final String CONSTRAINT_MESSAGE_ROUTE_INFO = "Информация по маршрутизации обратного потока инвойсов для документа в operUID = %s уже добавлена";

	public BillingPaymentDocumentInvoiceSubSender(GateFactory factory)
	{
		super(factory);
	}

	protected IFXRq_Type createRequest(GateDocument transfer) throws GateException, GateLogicException
	{
		IFXRq_Type ifxRq = super.createRequest(transfer);
		AutoSubscriptionInfo_Type autoSubscriptionInfo =
				ifxRq.getAutoSubscriptionModRq().getAutoSubscriptionRec().getAutoSubscriptionInfo();

		BasketPaymentsListenerConfig config  = ConfigFactory.getConfig(BasketPaymentsListenerConfig.class);
		if(BasketPaymentsListenerConfig.WorkingMode.autopay == config.getWorkingMode())
		{
			// чтобы AutoPay мог отличить подписку на инвойсы от автоподписки нужен префик к названию
			autoSubscriptionInfo.setAutopayName(PREFIX + autoSubscriptionInfo.getAutopayName());
		}
		else
		{
			autoSubscriptionInfo.setSummaKindCode(SummaKindCodeASAP_Type.BY_BILLING_BASKET);
		}

		return ifxRq;
	}

	public void send(GateDocument document) throws GateException, GateLogicException
	{
		try
		{
			super.send(document);
			addRouteInvoicesInfo(document);
		}
		catch (GateTimeOutException e)
		{
			addRouteInvoicesInfo(document); // все равно добавляем информацию для инвойсов
			throw e;
		}
	}

	protected void addRouteInvoicesInfo(GateDocument document) throws GateException
	{
		if(!(document instanceof CreateInvoiceSubscription))
			throw new GateException("Ожидался CreateInvoiceSubscription. Пришел " + document.getClass().getName());

		CreateInvoiceSubscription subscription = (CreateInvoiceSubscription) document;

		Long sendNodeNumber = subscription.getSendNodeNumber();
		if (sendNodeNumber == null)
			throw new GateException("Не установлен номер блока для документа c externalId = " + subscription.getExternalId());

		try
		{
			BasketRouteInfoService.addInfo(subscription.getOperationUID(), sendNodeNumber);
		}
		catch (ConstraintViolationException e)
		{
			log.error(String.format(CONSTRAINT_MESSAGE_ROUTE_INFO, subscription.getOperationUID()), e);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
