package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.gate.payments.autosubscriptions.AutoSubscriptionClaim;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.esberibgate.autopayments.AutoSubscriptionsRequestHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.AutoSubscriptionId_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.AutoSubscriptionInfo_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.AutoSubscriptionRec_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRq_Type;

import java.util.Calendar;

/**
 * @author vagin
 * @ created 13.10.14
 * @ $Author$
 * @ $Revision$
 * Сендер заявки на редактированеи копилки.
 */
public class EditCardToAccountLongOfferSender extends CardToAccountLongOfferSender
{
	public EditCardToAccountLongOfferSender(GateFactory factory)
	{
		super(factory);
	}

	protected IFXRq_Type createRequest(GateDocument document) throws GateException, GateLogicException
	{
		IFXRq_Type ifxRq = super.createRequest(document);

		AutoSubscriptionClaim claim = (AutoSubscriptionClaim) document;
		AutoSubscriptionRec_Type autoSubRec_type = ifxRq.getAutoSubscriptionModRq().getAutoSubscriptionRec();
		autoSubRec_type.setAutoSubscriptionId(createAutoSubscriptionId(claim));

		AutoSubscriptionInfo_Type autoSubscriptionInfo = autoSubRec_type.getAutoSubscriptionInfo();
		Calendar date = claim.getUpdateDate();
		autoSubscriptionInfo.setUpdateDate(date == null ? null : XMLDatatypeHelper.formatDateTimeWithoutTimeZone(date));

		return ifxRq;
	}

	private AutoSubscriptionId_Type createAutoSubscriptionId(AutoSubscription payment) throws GateException
	{
		AutoSubscriptionId_Type autoSubId_type = new AutoSubscriptionId_Type();
		autoSubId_type.setAutopayId(Long.parseLong(payment.getNumber()));
		autoSubId_type.setSystemId(ExternalSystemHelper.getCode(AutoSubscriptionsRequestHelper.SYSTEM_ID));
		return autoSubId_type;
	}
}
