package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.basket.EditInvoiceSubscription;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizicgate.esberibgate.autopayments.AutoSubscriptionsRequestHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.AutoSubscriptionId_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.AutoSubscriptionInfo_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.AutoSubscriptionRec_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRq_Type;

import java.util.Date;

/**
 * @author saharnova
 * @ created 26.06.15
 * @ $Author$
 * @ $Revision$
 */

public class BillingEditClaimDocumentInvoiceSubSender extends BillingPaymentDocumentInvoiceSubSender
{
	public BillingEditClaimDocumentInvoiceSubSender(GateFactory factory)
	{
		super(factory);
	}

	protected IFXRq_Type createRequest(GateDocument transfer) throws GateException, GateLogicException
	{
		IFXRq_Type ifxRq = super.createRequest(transfer);
		EditInvoiceSubscription claim = (EditInvoiceSubscription) transfer;
		AutoSubscriptionRec_Type autoSubscriptionRec = ifxRq.getAutoSubscriptionModRq().getAutoSubscriptionRec();

		autoSubscriptionRec.setAutoSubscriptionId(new AutoSubscriptionId_Type(
				ExternalSystemHelper.getCode(AutoSubscriptionsRequestHelper.SYSTEM_ID), Long.parseLong(claim.getAutopayNumber())));

		AutoSubscriptionInfo_Type autoSubscriptionInfo = autoSubscriptionRec.getAutoSubscriptionInfo();
		autoSubscriptionInfo.setUpdateDate(DateHelper.getXmlDateTimeWithoutMillisecondsFormat(new Date()));
		autoSubscriptionInfo.setAutopayNumber(null);

		return ifxRq;
	}

	protected void addRouteInvoicesInfo(GateDocument document) throws GateException
	{
	}
}
