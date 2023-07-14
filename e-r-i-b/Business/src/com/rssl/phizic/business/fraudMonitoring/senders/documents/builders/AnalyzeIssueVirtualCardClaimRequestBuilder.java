package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.phizic.business.documents.payments.VirtualCardClaim;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.rsa.senders.types.ClientDefinedEventType;

/**
 * Билдер запроса для заявки на открытие карты VirtualCardClaim
 *
 * @author khudyakov
 * @ created 18.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeIssueVirtualCardClaimRequestBuilder extends AnalyzeIssueCardClaimRequestBuilderBase<VirtualCardClaim>
{
	private VirtualCardClaim document;

	@Override
	public AnalyzeIssueVirtualCardClaimRequestBuilder append(VirtualCardClaim document)
	{
		this.document = document;
		return this;
	}

	@Override
	protected VirtualCardClaim getBusinessDocument()
	{
		return document;
	}

	@Override
	protected ClientDefinedEventType getClientDefinedEventType() throws GateException
	{
		return ClientDefinedEventType.REISSUE_VIRTUAL_CARD;
	}
}
