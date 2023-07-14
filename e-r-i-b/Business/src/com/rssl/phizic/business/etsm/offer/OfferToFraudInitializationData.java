package com.rssl.phizic.business.etsm.offer;

import com.rssl.phizic.rsa.InteractionType;
import com.rssl.phizic.rsa.PhaseType;
import com.rssl.phizic.rsa.senders.initialization.PhaseInitializationData;

/**
 * @author Moshenko
 * @ created 31.07.15
 * @ $Author$
 * @ $Revision$
 */
public class OfferToFraudInitializationData extends PhaseInitializationData
{
	private OfferConfirmed offerConfirmed;

	public OfferToFraudInitializationData(OfferConfirmed offerConfirmed, PhaseType phaseType)
	{
		super(InteractionType.ASYNC, phaseType);
		this.offerConfirmed = offerConfirmed;
	}

	public OfferConfirmed getOfferConfirmed()
	{
		return offerConfirmed;
	}
}
