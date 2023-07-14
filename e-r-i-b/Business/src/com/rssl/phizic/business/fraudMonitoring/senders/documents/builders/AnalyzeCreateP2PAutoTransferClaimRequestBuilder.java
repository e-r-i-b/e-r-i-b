package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.phizic.business.documents.payments.CreateP2PAutoTransferClaim;
import com.rssl.phizic.rsa.senders.types.ClientDefinedEventType;

/**
 * Класс билдера заявок на редактирование автопереводов
 *
 * @author khudyakov
 * @ created 18.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeCreateP2PAutoTransferClaimRequestBuilder extends AnalyzeP2PAutoTransferClaimRequestBuilderBase<CreateP2PAutoTransferClaim>
{
	private CreateP2PAutoTransferClaim document;

	@Override
	public AnalyzeDocumentRequestBuilderBase append(CreateP2PAutoTransferClaim document)
	{
		this.document = document;
		return this;
	}

	@Override
	protected CreateP2PAutoTransferClaim getBusinessDocument()
	{
		return document;
	}

	@Override
	protected ClientDefinedEventType getClientDefinedEventType()
	{
		return ClientDefinedEventType.CREATE_AUTO_PAYMENT;
	}
}
