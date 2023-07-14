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

/**
 * @author Egorova
 * @ created 25.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class GorodCODSender extends AbstractDocumentSender
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	private static final String ERROR_MESSAGE = "Невозможно подготовить биллинговый платеж в адаптере к АБС, необходимо проверить настройки адаптера и узла у биллинга ";

	public GorodCODSender(GateFactory factory)
	{
		super(factory);
	}

	protected String getRequestName(GateDocument gateDocument) throws GateException, GateLogicException
	{
			return "executeBillingPayment";
	}

	void fillGateMessage(GateMessage gateMessage, GateDocument gateDocument) throws GateLogicException, GateException
	{
		if (!(gateDocument instanceof AccountPaymentSystemPayment))
			throw new GateException("Неверный тип платежа, должен быть - AccountPaymentSystemPayment.");

		AccountPaymentSystemPayment payment = (AccountPaymentSystemPayment) gateDocument;

		gateMessage.addParameter("clientId", getClientId(gateDocument.getExternalOwnerId()));
		gateMessage.addParameter("recipientAccount",payment.getReceiverTransitAccount());
		if(!StringHelper.isEmpty(payment.getReceiverINN())){
			gateMessage.addParameter("recipientInn", payment.getReceiverINN());
		}
//		gateMessage.addParameter("recipientKpp", recipientInfo.getKPP());
		ResidentBank residentBank = payment.getReceiverTransitBank();
		gateMessage.addParameter("recipientName", StringHelper.replaceQuotes(residentBank.getName()));
		gateMessage.addParameter("bankBIC", residentBank.getBIC());
		gateMessage.addParameter("bankAccount", residentBank.getAccount());
//		gateMessage.addParameter("bankName", "name");
		gateMessage.addParameter("sum", payment.getDestinationAmount().getDecimal().toString());
		gateMessage.addParameter("commision", payment.getCommission().getDecimal().toString());
		gateMessage.addParameter("debitAccount", payment.getChargeOffAccount());
		gateMessage.addParameter("purpose", processGround(payment));

	}

	/**
	 * технический отзыв документа из ЦОД
	 * @param document - отзываемый документ
	 * @throws GateException, GateLogicException
	 */
	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		String documentId = document.getWithdrawExternalId().toString();
		try
		{
			WebBankServiceFacade service = getFactory().service(WebBankServiceFacade.class);

			GateMessage gateMessage = service.createRequest("revokeOperation_q");
			gateMessage.addParameter("clientId", getClientId(document.getExternalOwnerId()));
//TODO почему 2 раза?
			gateMessage.addParameter("opType", "EXTERNAL_PAYMENT");
			gateMessage.addParameter("opType", "EXTERNAL_PAYMENT");

			service.sendOnlineMessage(gateMessage, new MessageHeadImpl(null, null, null, documentId, null, null));
		}
		catch (GateException e)
		{
			log.debug("Не удалось отменить операцию в ЦОД!", e);
			throw new GateException(e);
		}
		catch (GateLogicException e)
		{
			log.debug("Не удалось отменить операцию в ЦОД!", e);
			throw new GateLogicException(e);
		}
	}

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		if (!(document instanceof AccountPaymentSystemPayment))
			throw new GateException("Неверный тип платежа, должен быть - AccountPaymentSystemPayment.");

		AccountPaymentSystemPayment payment = (AccountPaymentSystemPayment) document;
		log.error(ERROR_MESSAGE + payment.getBillingCode());
		throw new GateException(ERROR_MESSAGE + payment.getBillingCode());
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		if (!(document instanceof AccountPaymentSystemPayment))
			 throw new GateException("Неверный тип платежа, должен быть - AccountPaymentSystemPayment.");

		AccountPaymentSystemPayment payment = (AccountPaymentSystemPayment) document;
		String receiverAccount = payment.getReceiverAccount();
		if(!StringHelper.isEmpty(receiverAccount) && (receiverAccount.length() < 20 || receiverAccount.length() > 24))
		{
			String errorMessage = "Cчет зачисления  поставщика " + payment.getReceiverName() + " должен содержать 20-24 цифр";
			log.error(errorMessage);
			throw new GateException(errorMessage);
		}
	}
}
