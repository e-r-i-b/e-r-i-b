package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizgate.common.payments.documents.StateUpdateInfoImpl;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.config.ESBEribConfig;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.StateUpdateInfo;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.payments.autosubscriptions.AutoSubscriptionClaim;
import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;
import com.rssl.phizicgate.esberibgate.payment.recipients.BillingPaymentDocumentService;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

import java.util.Calendar;

/**
 * Сендер отмены, приостановки, возобновления автоплатежа
 *
 * @author khudyakov
 * @ created 24.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class AutoSubscriptionProcessSender extends BillingPaymentDocumentService
{
	public AutoSubscriptionProcessSender(GateFactory factory)
	{
		super(factory);
	}

	@Override
	public void send(GateDocument document) throws GateException, GateLogicException
	{
		super.send(document);

		addOfflineDocumentInfo(document);
	}

	@Override
	public StateUpdateInfo execute(GateDocument document) throws GateException, GateLogicException
	{
		StateUpdateInfo stateUpdateInfo = super.execute(document);
		if (stateUpdateInfo != null)
			return stateUpdateInfo;

		Calendar allowedUpdateDate = getAllowedUpdateDate(document.getClientOperationDate(), ConfigFactory.getConfig(ESBEribConfig.class).getDocumentUpdateWaitingTimeForLongOffer());
		if (allowedUpdateDate != null)
			return new StateUpdateInfoImpl(allowedUpdateDate);

		return null;
	}

	protected IFXRq_Type createRequest(GateDocument transfer) throws GateException, GateLogicException
	{
		if (!AutoSubscriptionClaim.class.isAssignableFrom(transfer.getType()))
			throw new GateException("Ожидается AutoSubscriptionClaim");

		AutoSubscriptionClaim longOffer = (AutoSubscriptionClaim) transfer;

		AutoSubscriptionStatusModRq_Type request = billingRequestHelper.createAutoSubscriptionStatusModRq(longOffer);
		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setAutoSubscriptionStatusModRq(request);

		return ifxRq;
	}

	protected void processResponse(GateDocument document, IFXRs_Type ifxRs) throws GateException, GateLogicException
	{
		AutoSubscriptionStatusModRs_Type responce = ifxRs.getAutoSubscriptionStatusModRs();

		Status_Type statusType = responce.getStatus();
		long statusCode = statusType.getStatusCode();

		if(statusCode == -105)
		{
			//Возникновение таймаута
			throw new GateTimeOutException(statusType.getStatusDesc());
		}

		if (statusCode != 0)
		{
			//Все ошибки пользовательские.
			throwGateLogicException(statusType, AutoSubscriptionStatusModRs_Type.class);
		}

		AutoSubscriptionClaim payment = (AutoSubscriptionClaim) document;
		payment.setExternalId(payment.getOperationUID());
	}


	public void prepare(GateDocument transfer) throws GateException, GateLogicException
	{
		// подготовка платежа не нужна
	}
}
