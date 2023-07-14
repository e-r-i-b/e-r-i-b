package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.payments.autosubscriptions.*;
import com.rssl.phizicgate.esberibgate.payment.AutoSubscriptionHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

/**
 * Сендер возобновления/приостановки/закртытия P2P автоплатежа
 * @author lukina
 * @ created 17.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class CardToCardAutoSubscriptionProcessSender extends AbstractOfflineClaimSenderBase
{
	private AutoSubscriptionHelper autoSubscriptionHelper;

	public CardToCardAutoSubscriptionProcessSender(GateFactory factory)
	{
		super(factory);

		autoSubscriptionHelper = new AutoSubscriptionHelper(factory);
	}

	@Override
	protected IFXRq_Type createRequest(GateDocument document) throws GateException, GateLogicException
	{
		if (document.getType() != RecoveryCardToCardLongOffer.class && document.getType() != DelayCardToCardLongOffer.class && document.getType() != CloseCardToCardLongOffer.class )
		{
			throw new GateException("Ожидается RecoveryCardToCardLongOffer, DelayCardToCardLongOffer или CloseCardToCardLongOffer");
		}

		AutoSubscriptionClaim payment = (AutoSubscriptionClaim) document;
		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setAutoSubscriptionStatusModRq(autoSubscriptionHelper.createAutoSubscriptionStatusModRq(payment));

		return ifxRq;
	}

	@Override
	protected void processResponse(GateDocument document, IFXRs_Type ifxRs) throws GateException, GateLogicException
	{
		AutoSubscriptionStatusModRs_Type response = ifxRs.getAutoSubscriptionStatusModRs();
		Status_Type statusType = response.getStatus();
		long statusCode = statusType.getStatusCode();

		if (statusCode == -105)
		{
			//Возникновение таймаута
			throw new GateTimeOutException(statusType.getStatusDesc());
		}

		if (statusCode != 0)
		{
			//Все ошибки пользовательские.
			throwGateLogicException(statusType, AutoSubscriptionModRs_Type.class);
		}

		AutoSubscriptionClaim payment = (AutoSubscriptionClaim) document;
		payment.setExternalId(payment.getOperationUID());
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
	}
}
