package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.claims.SecuritiesTransferClaim;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.esberibgate.depo.DepoAccountRequestHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.DepoAccTranRsType;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRq_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRs_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.Status_Type;

/**
 * @author mihaylov
 * @ created 19.09.2010
 * @ $Author$
 * @ $Revision$
 */

public class SecuritiesTransferClaimSender extends AbstractOfflineClaimSenderBase
{
	public SecuritiesTransferClaimSender(GateFactory factory)
	{
		super(factory);
	}

	protected IFXRq_Type createRequest(GateDocument document) throws GateException, GateLogicException
	{
		if(document.getType() != SecuritiesTransferClaim.class)
			throw new GateException("Неверный тип документа, должен быть SecuritiesTransferClaim.");
		SecuritiesTransferClaim claim = (SecuritiesTransferClaim) document;
		DepoAccountRequestHelper helper = new DepoAccountRequestHelper(getFactory());
		return helper.createDepoAccTranRq(claim,getClientDepartmentCode(claim));
	}

	protected void processResponse(GateDocument document, IFXRs_Type ifxRs) throws GateException, GateLogicException
	{
		SecuritiesTransferClaim claim = (SecuritiesTransferClaim) document;
		Status_Type status = ifxRs.getDepoAccTranRs().getStatus();
		if (status.getStatusCode() != 0)
			throwGateLogicException(status, DepoAccTranRsType.class);
		claim.setExternalId(ifxRs.getDepoAccTranRs().getDocNumber());
	}
}
