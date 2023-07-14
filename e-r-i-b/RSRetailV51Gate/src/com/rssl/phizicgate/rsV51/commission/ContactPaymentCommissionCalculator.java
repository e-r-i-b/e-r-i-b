package com.rssl.phizicgate.rsV51.commission;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.payments.systems.contact.ContactPayment;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import org.w3c.dom.Document;

import java.math.BigDecimal;

/**
 * @author Krenev
 * @ created 15.06.2007
 * @ $Author$
 * @ $Revision$
 */
public class ContactPaymentCommissionCalculator extends AbstractCommissionCalculator
{
	private static final String PARAMETER_RETAIL_CONTACT_ID_NAME = "retail-contact-id";

	/**
	 * получить комиссию по платежу
	 * @param document платеж
	 */
	public void calcCommission(GateDocument document) throws GateException, GateLogicException
	{
		if (!(document instanceof ContactPayment))
		{
			throw new IllegalArgumentException("Excpected contact payment");
		}
		WebBankServiceFacade webBankServiceFacade = getWebBankServiceFacade();
		ContactPayment contactPayment = (ContactPayment) document;

		GateMessage request = webBankServiceFacade.createRequest("commissionContact_q");

		request.addParameter("senderFnCash", getFnCash(contactPayment));
		request.addParameter("senderPsNum", getContactRetailId());
		request.addParameter("receiverPointCode", contactPayment.getReceiverPointCode());

		Money amount = contactPayment.getChargeOffAmount();

		request.addParameter("sum", amount.getDecimal().toString());
		request.addParameter("currency", getCurrency(contactPayment));
		Document response = webBankServiceFacade.sendOnlineMessage(request, null);

		BigDecimal commission = getDocumentCommission(response);
		contactPayment.setCommission(new Money(commission, amount.getCurrency()));
	}
	private String getContactRetailId()
	{
		return (String) getParameter(PARAMETER_RETAIL_CONTACT_ID_NAME);
	}
}
