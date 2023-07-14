package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.cms.claims.CardBlockingClaim;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.esberibgate.bankroll.BankrollRequestHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.CardBlockRs_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRq_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRs_Type;

/**
 * @author gladishev
 * @ created 24.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class BlockingCardSender extends AbstractClaimSenderBase
{
	public BlockingCardSender(GateFactory factory)
	{
		super(factory);
	}

	protected IFXRq_Type createRequest(GateDocument document) throws GateException, GateLogicException
	{
		if( !(document instanceof CardBlockingClaim) )
			throw new GateException("Неверный тип платежа, должен быть - CardBlockingClaim.");
		try
		{
			CardBlockingClaim claim = (CardBlockingClaim) document;
			BankrollRequestHelper helper = new BankrollRequestHelper(getFactory());
			Client owner = getBusinessOwner(document);
			Card card = getCard(owner, claim.getCardNumber(), null);
			return helper.createCardBlockRq(card, claim, helper.getRbTbBrch(claim.getInternalOwnerId()));
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
		catch (LogicException e)
		{
			throw new GateLogicException(e.getMessage(), e);
		}
	}

	protected void processResponse(GateDocument document, IFXRs_Type ifxRs) throws GateException, GateLogicException
	{
		CardBlockingClaim claim = (CardBlockingClaim) document;

		if (ifxRs.getCardBlockRs().getStatus().getStatusCode() != 0)
		{
			throwGateLogicException(ifxRs.getCardBlockRs().getStatus(), CardBlockRs_Type.class);
		}
		claim.setExternalId(ifxRs.getCardBlockRs().getRqUID());
	}
}