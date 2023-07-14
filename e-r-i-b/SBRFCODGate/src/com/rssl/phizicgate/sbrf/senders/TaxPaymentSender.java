package com.rssl.phizicgate.sbrf.senders;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.config.SpecificGateConfig;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.payments.AccountRUSTaxPayment;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;

/**
 * @author gladishev
 * @ created 18.06.2010
 * @ $Author$
 * @ $Revision$
 */
public class TaxPaymentSender extends AbstractDocumentSender
{
	public TaxPaymentSender(GateFactory factory)
	{
		super(factory);
	}

	void fillGateMessage(GateMessage gateMessage, GateDocument gateDocument) throws GateLogicException, GateException
	{
		if (!(gateDocument instanceof AccountRUSTaxPayment))
			throw new GateException("Неверный тип платежа, должен быть - AccountRUSTaxPayment.");

		AccountRUSTaxPayment taxPayment = (AccountRUSTaxPayment) gateDocument;

		gateMessage.addParameter("clientId", getClientId(gateDocument.getExternalOwnerId()));
	    gateMessage.addParameter("account", taxPayment.getReceiverAccount());
		ResidentBank residentBank = taxPayment.getReceiverBank();
		gateMessage.addParameter("bankBIC", residentBank.getBIC());
		if(!StringHelper.isEmpty(residentBank.getAccount()))
			gateMessage.addParameter("bankAccount", residentBank.getAccount());
		gateMessage.addParameter("inn", taxPayment.getReceiverINN());
		gateMessage.addParameter("sum", taxPayment.getDestinationAmount().getDecimal().toString());
		if (taxPayment.getCommission() != null)
		{
			gateMessage.addParameter("commision", taxPayment.getCommission().getDecimal().toString());
		}
		gateMessage.addParameter("debitAccount", taxPayment.getChargeOffAccount());
		gateMessage.addParameter("purpose", processGround(taxPayment));
		gateMessage.addParameter("beneficiary", StringHelper.replaceQuotes(taxPayment.getReceiverName() ));
		String usePaymentOrder = ConfigFactory.getConfig(SpecificGateConfig.class).getUsePaymentOrderForRUSTaxPayment();
		if (usePaymentOrder != null)
		{
			gateMessage.addParameter("usePaymentOrder", usePaymentOrder);
		}
		gateMessage.addParameter("clientNumber", taxPayment.getDocumentNumber());
		gateMessage.addParameter("clientDate", XMLDatatypeHelper.formatDate(taxPayment.getClientCreationDate()));
		gateMessage.addParameter("nStatus", taxPayment.getTaxPaymentStatus());
		gateMessage.addParameter("nKppBen", taxPayment.getReceiverKPP());
		gateMessage.addParameter("nKbk", taxPayment.getTaxKBK());
		gateMessage.addParameter("nOkato", taxPayment.getTaxOKATO());
		gateMessage.addParameter("nBase", taxPayment.getTaxGround());
		gateMessage.addParameter("nPeriod", taxPayment.getTaxPeriod());
		gateMessage.addParameter("nNumber", taxPayment.getTaxDocumentNumber());
		gateMessage.addParameter("nDate", String.format("%1$te.%1$tm.%1$tY", taxPayment.getTaxDocumentDate()));
		gateMessage.addParameter("nType", taxPayment.getTaxPaymentType());
	}

	protected String getRequestName(GateDocument gateDocument) throws GateException, GateLogicException
	{
		return "executePayment_q";
	}
}
