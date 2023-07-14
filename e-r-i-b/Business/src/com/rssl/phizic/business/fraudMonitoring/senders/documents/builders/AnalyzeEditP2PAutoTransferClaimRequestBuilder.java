package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.phizic.business.documents.payments.EditP2PAutoTransferClaim;
import com.rssl.phizic.rsa.senders.types.ClientDefinedEventType;

/**
 * Класс билдера заявок на редактирование автопереводов
 *
 * @author khudyakov
 * @ created 18.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeEditP2PAutoTransferClaimRequestBuilder extends AnalyzeP2PAutoTransferClaimRequestBuilderBase<EditP2PAutoTransferClaim>
{
	private EditP2PAutoTransferClaim document;

	@Override
	public AnalyzeDocumentRequestBuilderBase append(EditP2PAutoTransferClaim document)
	{
		this.document = document;
		return this;
	}

	@Override
	protected EditP2PAutoTransferClaim getBusinessDocument()
	{
		return document;
	}

	@Override
	protected ClientDefinedEventType getClientDefinedEventType()
	{
		return ClientDefinedEventType.EDIT_AUTO_PAYMENT;
	}
}
