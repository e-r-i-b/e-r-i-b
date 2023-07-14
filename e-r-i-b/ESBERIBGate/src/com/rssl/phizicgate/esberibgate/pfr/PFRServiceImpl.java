package com.rssl.phizicgate.esberibgate.pfr;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.claims.pfr.PFRStatementClaim;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.pfr.PFRService;
import com.rssl.phizic.gate.pfr.StatementStatus;
import com.rssl.phizicgate.esberibgate.AbstractService;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

/**
 * @author gulov
 * @ created 16.02.2011
 * @ $Authors$
 * @ $Revision$
 */
public class PFRServiceImpl extends AbstractService implements PFRService
{
	public PFRServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public String getStatement(PFRStatementClaim claim) throws GateLogicException, GateException
	{
		IFXRq_Type request = createRequest(claim);
		IFXRs_Type response = getRequest(request);
		PfrGetInfoInqRs_Type infoResponse = response.getPfrGetInfoInqRs();

		switch ((int) infoResponse.getStatus().getStatusCode())
		{
			case PFRSender.STATUS_CODE_0:
			{
				if (infoResponse.getResult() != null &&	infoResponse.getResult().isResponseExists())
				{
					return infoResponse.getPfrInfo().toString();
				}
			}
			case PFRSender.STATUS_CODE_801:
			{
				claim.setReady(StatementStatus.UNAVAILABLE_DUE_UNKNOWN_PERSON);
				claim.setReadyDescription(PFRSender.STATUS_DESC_801);

				return null;
			}
			case PFRSender.STATUS_CODE_802:
			{
				claim.setReady(StatementStatus.UNAVAILABLE_DUE_UNKNOWN_PERSON);
				claim.setReadyDescription(PFRSender.STATUS_DESC_802);

				return null;
			}
			case PFRSender.STATUS_CODE_803:
			case PFRSender.STATUS_CODE_810:
			{
				claim.setReadyDescription(String.format(PFRSender.STATUS_DESC_810, infoResponse.getStatus().getStatusDesc()));
				claim.setReady(StatementStatus.UNAVAILABLE_DUE_FAIL);

				return null;
			}
			case PFRSender.STATUS_CODE_804:
			{
				claim.setReady(StatementStatus.UNAVAILABLE_DUE_UNKNOWN_PERSON);
				claim.setReadyDescription(PFRSender.STATUS_DESC_804);

				return null;
			}
			default:
			{
				claim.setReady(StatementStatus.UNAVAILABLE_DUE_FAIL);
				claim.setReadyDescription(PFRSender.NOT_SPECIFIC_ERROR);

				return null;
			}
		}
	}

	private IFXRq_Type createRequest(SynchronizableDocument document) throws GateException, GateLogicException
	{
		PaymentsRequestHelper paymentsRequestHelper = new PaymentsRequestHelper(GateSingleton.getFactory());

		PfrGetInfoInqRq_Type type = new PfrGetInfoInqRq_Type();

		type.setRqUID(PaymentsRequestHelper.generateUUID());
	    type.setRqTm(PaymentsRequestHelper.generateRqTm());
	    type.setOperUID(PaymentsRequestHelper.generateOUUID());
	    type.setSPName(SPName_Type.BP_ERIB);
	    type.setBankInfo(paymentsRequestHelper.createAuthBankInfo(document.getInternalOwnerId()));
		type.setOperationId(document.getExternalId());

		IFXRq_Type result = new IFXRq_Type();

		result.setPfrGetInfoInqRq(type);

		return result;
	}
}
