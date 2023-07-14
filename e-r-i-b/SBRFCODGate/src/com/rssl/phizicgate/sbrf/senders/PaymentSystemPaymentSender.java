package com.rssl.phizicgate.sbrf.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.messaging.impl.MessageHeadImpl;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * Сендер для документов типа PaymentSystemPayment.
 * Используется для АБС София.
 *
 * @author gladishev
 * @ created 13.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class PaymentSystemPaymentSender extends AbstractDocumentSender
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	private static final String USE_PAYMENT_ORDER_PARAMETER_NAME = "usePaymentOrder";
	private String usePaymentOrder;

	public PaymentSystemPaymentSender(GateFactory factory)
	{
		super(factory);
	}

	public void setParameters(Map<String, ?> params)
	{
		usePaymentOrder = (String) params.get(USE_PAYMENT_ORDER_PARAMETER_NAME);
	}

	protected String getRequestName(GateDocument gateDocument) throws GateException, GateLogicException
	{
		return "executePayment_q";
	}

	void fillGateMessage(GateMessage gateMessage, GateDocument gateDocument) throws GateLogicException, GateException
	{
		if (gateDocument.getType() != AccountPaymentSystemPayment.class)
			throw new GateException("Неверный тип платежа, должен быть - AccountPaymentSystemPayment.");

		AccountPaymentSystemPayment payment = (AccountPaymentSystemPayment) gateDocument;

		gateMessage.addParameter("clientId", getClientId(gateDocument.getExternalOwnerId()));

		gateMessage.addParameter("account", payment.getReceiverTransitAccount());
		ResidentBank residentBank = payment.getReceiverTransitBank();
		gateMessage.addParameter("bankBIC", residentBank.getBIC());
		if(!StringHelper.isEmpty(residentBank.getAccount()))
			gateMessage.addParameter("bankAccount", residentBank.getAccount());
		if(!StringHelper.isEmpty(payment.getReceiverINN()))
			gateMessage.addParameter("inn", payment.getReceiverINN());

		gateMessage.addParameter("sum", payment.getDestinationAmount().getDecimal().toString());
		if (payment.getCommission() != null)
		{
			gateMessage.addParameter("commision", payment.getCommission().getDecimal().toString());
		}
 		gateMessage.addParameter("debitAccount", payment.getChargeOffAccount());
		gateMessage.addParameter("purpose", processGround(payment));
		gateMessage.addParameter( "beneficiary", StringHelper.replaceQuotes(residentBank.getName()));
		if (usePaymentOrder != null)
		{
			gateMessage.addParameter("usePaymentOrder", usePaymentOrder);
		}
	}

	/**
	 * технический отзыв документа из АБС София
	 * @param document - отзываемый документ
	 * @throws GateException, GateLogicException
	 */
	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		String documentId = document.getId().toString();
		try
		{
			WebBankServiceFacade service = getFactory().service(WebBankServiceFacade.class);

			GateMessage gateMessage = service.createRequest("revokeOperation_q");
			gateMessage.addParameter("clientId", getClientId(document.getExternalOwnerId()));
			gateMessage.addParameter("opType", "EXTERNAL_PAYMENT");

			service.sendOnlineMessage(gateMessage, new MessageHeadImpl(null, null, null, documentId, null, null));
		}
		catch (GateException e)
		{
			log.debug("Не удалось отменить операцию", e);
			throw new GateException(e);
		}
		catch (GateLogicException e)
		{
			log.debug("Не удалось отменить операцию", e);
			throw new GateLogicException(e);
		}
	}
}
