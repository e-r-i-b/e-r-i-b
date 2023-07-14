package com.rssl.phizicgate.manager.services.routable.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.DocumentService;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.AbstractBillingPayment;
import com.rssl.phizic.gate.payments.autopayment.CreateAutoPayment;
import com.rssl.phizic.gate.payments.autopayment.EditAutoPayment;
import com.rssl.phizic.gate.payments.autopayment.RefuseAutoPayment;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizicgate.manager.services.routable.documents.AbstractRoutableDocumentService;

import java.util.Map;

/**
 * @author akrenev
 * @ created 19.01.2010
 * @ $Author$
 * @ $Revision$
 * Сендер биллнговых платежей для БС, самостоятельно осуществлющих 2фазную транзакцию.
 * Маршрутизация происходит по коду постащика(=биллинга).
 */
public class BillingPaymentSystemPaymentSender extends AbstractRoutableDocumentService
{
	public BillingPaymentSystemPaymentSender(GateFactory factory)
	{
		super(factory);
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		// см. BUG021883: Невозможно удалить клиента
	}

	public void setParameters(Map<String, ?> params) {}

	protected DocumentService getDelegate(GateDocument document) throws GateException, GateLogicException
	{
		Class<? extends GateDocument> actualType = document.getType();
		if (actualType != AccountPaymentSystemPayment.class && 
				actualType != CardPaymentSystemPayment.class &&
				actualType != CreateAutoPayment.class &&
				actualType != RefuseAutoPayment.class &&
				actualType != EditAutoPayment.class)
		{
			throw new IllegalArgumentException("Неверный тип документа. Ожидается CardPaymentSystemPayment, AccountPaymentSystemPayment, CreateAutoPayment, " +
					"RefuseAutoPayment или EditAutoPayment получен " + actualType);
		}
		return getDelegateFactory(((AbstractBillingPayment) document).getReceiverPointCode()).service(DocumentService.class);
	}
}