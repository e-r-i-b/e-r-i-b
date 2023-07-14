package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.claims.SecurityRegistrationClaim;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.esberibgate.depo.DepoAccountRequestHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

/**
 * @author lukina
 * @ created 29.09.2010
 * @ $Author$
 * @ $Revision$
 */

public class SecurityRegistrationClaimSender extends AbstractOfflineClaimSenderBase
{
	public SecurityRegistrationClaimSender(GateFactory factory)
	{
		super(factory);
	}

	protected IFXRq_Type createRequest(GateDocument document) throws GateException, GateLogicException
	{
		if(document.getType() != SecurityRegistrationClaim.class)
			throw new GateException("Неверный тип документа, должен быть SecurityRegistrationClaim.");
		SecurityRegistrationClaim claim = (SecurityRegistrationClaim) document;
		DepoAccountRequestHelper helper = new DepoAccountRequestHelper(getFactory());
		return helper.createDepoAccSecRegRq(claim, getClientDepartmentCode(claim));
	}

	protected void processResponse(GateDocument document, IFXRs_Type ifxRs) throws GateException, GateLogicException
	{
		SecurityRegistrationClaim claim = (SecurityRegistrationClaim) document;
		Status_Type status = ifxRs.getDepoAccSecRegRs().getStatus();
		if (status.getStatusCode() != 0)
			throwGateLogicException(status, DepoAccSecRegRsType.class);
		claim.setExternalId(ifxRs.getDepoAccSecRegRs().getDocNumber());
	}
}
