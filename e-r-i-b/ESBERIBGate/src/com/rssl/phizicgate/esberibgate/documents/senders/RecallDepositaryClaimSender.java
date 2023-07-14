package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.claims.RecallDepositaryClaim;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.esberibgate.depo.DepoAccountRequestHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.DepoRevokeDocRsType;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRq_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRs_Type;

/**
 * @author mihaylov
 * @ created 17.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class RecallDepositaryClaimSender extends AbstractClaimSenderBase
{
	public RecallDepositaryClaimSender(GateFactory factory)
	{
		super(factory);
	}

	protected IFXRq_Type createRequest(GateDocument document) throws GateException, GateLogicException
	{
		if(document.getType() != RecallDepositaryClaim.class)
			throw new GateException("Неверный тип документа, должен быть RecallDepositaryClaim.");
		RecallDepositaryClaim claim = (RecallDepositaryClaim) document;
		DepoAccountRequestHelper helper = new DepoAccountRequestHelper(getFactory());
		return helper.createDepoRevokeDocRq(claim,getClientDepartmentCode(claim));				
	}

	protected void processResponse(GateDocument document, IFXRs_Type ifxRs) throws GateException, GateLogicException
	{
		RecallDepositaryClaim claim = (RecallDepositaryClaim) document;

		if (ifxRs.getDepoRevokeDocRs().getStatus().getStatusCode() != 0)
		{
			throwGateLogicException(ifxRs.getDepoRevokeDocRs().getStatus(), DepoRevokeDocRsType.class);
		}
		claim.setExternalId(ifxRs.getDepoRevokeDocRs().getRqUID());
	}
}
