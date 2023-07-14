package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.RefuseLongOffer;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;
import com.rssl.phizicgate.esberibgate.messaging.RequestHelperBase;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.utils.LongOfferCompositeId;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

/**
 * @author gladishev
 * @ created 05.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class RefuseLongOfferSender extends AbstractClaimSenderBase
{
	public RefuseLongOfferSender(GateFactory factory) throws GateException
	{
		super(factory);
	}

	protected IFXRq_Type createRequest(GateDocument document) throws GateException, GateLogicException
	{
		if (document.getType() != RefuseLongOffer.class)
			throw new GateException("Неверный тип документа. Ожидался RefuseLongOffer");

		RequestHelperBase requestHelperBase = new RequestHelperBase(getFactory());

		RefuseLongOffer refuseLongOffer = (RefuseLongOffer) document;
		LongOfferCompositeId compositId = EntityIdHelper.getLongOfferCompositeId(refuseLongOffer.getLongOfferExternalId());

		SvcAcctDelRq_Type svcAcctDelRq = new SvcAcctDelRq_Type();
		svcAcctDelRq.setRqUID(PaymentsRequestHelper.generateUUID());
		svcAcctDelRq.setRqTm(PaymentsRequestHelper.generateRqTm());
		svcAcctDelRq.setOperUID(PaymentsRequestHelper.generateOUUID());
		svcAcctDelRq.setSPName(SPName_Type.BP_ERIB);
		svcAcctDelRq.setSvcAcct(createSvcAct(compositId));
		svcAcctDelRq.setBankInfo(requestHelperBase.createAuthBankInfo(compositId.getLoginId()));
		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setSvcAcctDelRq(svcAcctDelRq);
		return ifxRq;
	}

	protected void processResponse(GateDocument document, IFXRs_Type ifxRs) throws GateException, GateLogicException
	{
		if (document.getType() != RefuseLongOffer.class)
			throw new GateException("Неверный тип документа. Ожидался RefuseLongOffer");

		RefuseLongOffer refuseLongOffer = (RefuseLongOffer) document;

		Status_Type statusType = ifxRs.getSvcAcctDelRs().getStatus();
		long statusCode = statusType.getStatusCode();
		if (statusCode != 0)
		{
			//Все ошибки пользовательские.
			throwGateLogicException(statusType, SvcAcctDelRs_Type.class);
		}
		refuseLongOffer.setExternalId(ifxRs.getSvcAcctDelRs().getRqUID());
	}

	private SvcAcctDelRq_TypeSvcAcct createSvcAct(LongOfferCompositeId compositId ) throws GateException, GateLogicException
	{
		SvcAcctId_Type svcAcctId = new SvcAcctId_Type();
		svcAcctId.setSystemId(compositId.getSystemIdActiveSystem());
		svcAcctId.setSvcAcctNum(Long.parseLong(compositId.getEntityId()));
		svcAcctId.setSvcType(compositId.getSvcType());
		svcAcctId.setBankInfo(new BankInfo_Type(compositId.getBranchId(), compositId.getAgencyId(),
										compositId.getRegionId(), null, compositId.getRbBrchId()));

		return new SvcAcctDelRq_TypeSvcAcct(svcAcctId);
	}
}
