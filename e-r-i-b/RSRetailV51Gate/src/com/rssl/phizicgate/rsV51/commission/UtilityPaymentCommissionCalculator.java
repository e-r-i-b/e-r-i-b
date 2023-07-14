package com.rssl.phizicgate.rsV51.commission;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import org.w3c.dom.Document;

import java.math.BigDecimal;

/**
 * @author Krenev
 * @ created 26.06.2007
 * @ $Author$
 * @ $Revision$
 */
public class UtilityPaymentCommissionCalculator extends AbstractCommissionCalculator
{
	private static final String PARAMETER_UTILITY_PAYMENT_KIND_NAME = "payment-kind";
	private static final String PARAMETER_UTILITY_PAYMENT_RECEIVER_NAME = "payment-receiver";

	public void calcCommission(GateDocument doc) throws GateException, GateLogicException
	{
		WebBankServiceFacade webBankServiceFacade = getWebBankServiceFacade();
		AbstractAccountTransfer transfer = (AbstractAccountTransfer) doc;
		Money amount = transfer.getChargeOffAmount();

		GateMessage message = webBankServiceFacade.createRequest("commissionPayCP_q");
		message.addParameter("account", transfer.getChargeOffAccount());
		message.addParameter("filial", getFnCash(transfer));
		message.addParameter("date", getDate(transfer));
		message.addParameter("sum", amount.getDecimal().toString());
		message.addParameter("currency", getCurrency(transfer));
		message.addParameter("clientId", transfer.getClientInfo().getExternalOwnerId());
		message.addParameter("kindCP", getMunicipalPaymentKind());
		message.addParameter("recipCP", getMunicipalPaymentReceiver());

		Document document = webBankServiceFacade.sendOnlineMessage(message, null);

		BigDecimal commission = getDocumentCommission(document);
		transfer.setCommission(new Money(commission, amount.getCurrency()));
	}

	private String getMunicipalPaymentReceiver()
	{
		return (String) getParameter(PARAMETER_UTILITY_PAYMENT_RECEIVER_NAME);
	}

	private String getMunicipalPaymentKind()
	{
		return (String) getParameter(PARAMETER_UTILITY_PAYMENT_KIND_NAME);
	}
}
