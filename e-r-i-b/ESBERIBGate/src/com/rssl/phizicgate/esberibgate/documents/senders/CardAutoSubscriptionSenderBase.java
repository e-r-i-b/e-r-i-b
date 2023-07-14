package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.payments.autosubscriptions.AutoSubscriptionClaim;
import com.rssl.phizic.gate.payments.autosubscriptions.ExternalCardsTransferToOurBankLongOffer;
import com.rssl.phizic.gate.payments.autosubscriptions.InternalCardsTransferLongOffer;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

/**
 * Базовый класс автоподписок
 *
 * @author khudyakov
 * @ created 10.09.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class CardAutoSubscriptionSenderBase extends AbstractOfflineClaimSenderBase
{
	public CardAutoSubscriptionSenderBase(GateFactory factory)
	{
		super(factory);
	}

	protected abstract AutoSubscriptionModRq_Type createAutoSubscriptionModRq(AutoSubscriptionClaim payment) throws GateException, GateLogicException;

	protected IFXRq_Type createRequest(GateDocument document) throws GateException, GateLogicException
	{
		AutoSubscriptionClaim payment = (AutoSubscriptionClaim) document;

		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setAutoSubscriptionModRq(createAutoSubscriptionModRq(payment));

		return ifxRq;
	}

	protected void processResponse(GateDocument document, IFXRs_Type ifxRs) throws GateException, GateLogicException
	{
		AutoSubscriptionModRs_Type response = ifxRs.getAutoSubscriptionModRs();
		Status_Type statusType = response.getStatus();
		long statusCode = statusType.getStatusCode();

		//таймаут
		if (statusCode == -105)
		{
			throw new GateTimeOutException(statusType.getStatusDesc());
		}

		if (statusCode != 0)
		{
			throwGateLogicException(statusType, AutoSubscriptionModRs_Type.class);
		}

		AutoSubscriptionClaim payment = (AutoSubscriptionClaim) document;
		payment.setExternalId(payment.getOperationUID());
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{

	}
}