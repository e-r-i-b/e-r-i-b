package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.ReIssueCardClaim;
import com.rssl.phizicgate.esberibgate.bankroll.BankrollRequestHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRq_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRs_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.CardReissuePlaceRs_Type;

/**
 * сендер отправка за€вки на перевыпуск карты.
 *
 * @author bogdanov
 * @ created 25.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class ReissueCardSender extends AbstractClaimSenderBase
{
	public ReissueCardSender(GateFactory factory)
	{
		super(factory);
	}

	protected IFXRq_Type createRequest(GateDocument document) throws GateException, GateLogicException
	{
		if( !(document instanceof ReIssueCardClaim) )
			throw new GateException("Ќеверный тип платежа, должен быть " + ReIssueCardClaim.class.getSimpleName());

		ReIssueCardClaim claim = (ReIssueCardClaim) document;

		BankrollRequestHelper helper = new BankrollRequestHelper(getFactory());
		return helper.createCardReissuePlaceRq(claim);
	}

	protected void processResponse(GateDocument document, IFXRs_Type ifxRs) throws GateException, GateLogicException
	{
		ReIssueCardClaim claim = (ReIssueCardClaim) document;

		CardReissuePlaceRs_Type cardReissuePlaceRs = ifxRs.getCardReissuePlaceRs();
		if (cardReissuePlaceRs.getStatus().getStatusCode() != 0)
		{
			throwGateLogicException(cardReissuePlaceRs.getStatus(), CardReissuePlaceRs_Type.class);
		}
		claim.setExternalId(cardReissuePlaceRs.getRqUID());
	}
}
