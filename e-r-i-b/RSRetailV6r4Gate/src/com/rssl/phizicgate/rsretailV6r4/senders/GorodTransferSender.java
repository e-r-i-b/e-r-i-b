package com.rssl.phizicgate.rsretailV6r4.senders;

import com.rssl.phizic.BankContextConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

/**
 * @author Egorova
 * @ created 28.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class GorodTransferSender  extends AbstractDocumentSender
{
	private static final String PARAMETER_RECEIVER_INN = "receiver-inn";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	private static final String ERROR_MESSAGE = "Невозможно подготовить билинговый платеж в адаптере к АБС, необходимо проверить настройки адаптера и узла у билинга ";

	public GorodTransferSender(GateFactory factory)
	{
		super(factory);
	}

	public GateMessage createGateMessage(GateDocument gateDocument) throws GateLogicException, GateException
	{
		if (!(gateDocument instanceof AccountPaymentSystemPayment))
			throw new GateException("Неверный тип платежа, должен быть - AccountPaymentSystemPayment.");
		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		return service.createRequest("externalTransfer_q");
	}

	void fillGateMessage(GateMessage gateMessage, GateDocument gateDocument) throws GateLogicException, GateException
	{
		super.fillGateMessage(gateMessage, gateDocument);
		if (!(gateDocument instanceof AccountPaymentSystemPayment))
			throw new GateException("Неверный тип платежа, должен быть - AccountPaymentSystemPayment.");

		AccountPaymentSystemPayment payment = (AccountPaymentSystemPayment) gateDocument;

		gateMessage.addParameter("chargeOffAccount",payment.getChargeOffAccount());
		Element chargeOffAmount = XmlHelper.appendSimpleElement(gateMessage.getDocument().getDocumentElement(), "chargeOffAmount");
		XmlHelper.appendSimpleElement(chargeOffAmount, "value",payment.getDestinationAmount().getDecimal().toString());
		gateMessage.addParameter("receiverName", StringHelper.replaceQuotes(payment.getReceiverTransitBank().getName()));
		BankContextConfig bankContextConfig = ConfigFactory.getConfig(BankContextConfig.class);
		gateMessage.addParameter("receiverBIC", bankContextConfig.getBankBIC());
		gateMessage.addParameter("receiverCorAccount", bankContextConfig.getBankCorAcc());
		gateMessage.addParameter("receiverINN", getParameter(PARAMETER_RECEIVER_INN));
		gateMessage.addParameter("receiverKPP","09288706");
		gateMessage.addParameter("receiverBankName",
				StringHelper.replaceQuotes(bankContextConfig.getBankName()));
		gateMessage.addParameter("receiverAccount",payment.getReceiverTransitAccount());
		if(payment.getCommission() != null)
			gateMessage.addParameter("commission", payment.getCommission().getDecimal().toString());
		gateMessage.addParameter("ground", StringHelper.replaceQuotes(payment.getGround()));
		if(payment.getExternalId()!= null)
		{
			String extId = payment.getExternalId();
			String key = extId.substring(extId.indexOf("key:") + 4,extId.indexOf(","));
			String kind = extId.substring(extId.indexOf("kind:")+5, extId.length() );
			gateMessage.addParameter("applKey", key);
			gateMessage.addParameter("applKind", kind);
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
		String transitAccount = payment.getReceiverTransitAccount();
		if(transitAccount == null || (transitAccount.length() > 25 && transitAccount.length() < 20))
		{
			String errorMessage = "Транзитный счет поставщика " + payment.getReceiverName() + " должен содержать 20-25 цифр";
			log.error(errorMessage);
			throw new GateException(errorMessage);
		}

		super.validate(document);
	}
}
