package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.phizic.business.documents.payments.EditMoneyBoxClaim;
import com.rssl.phizic.rsa.senders.types.ClientDefinedEventType;

/**
 * Класс билдера запросов заявок редактирования копилок
 *
 * @author khudyakov
 * @ created 18.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeEditMoneyBoxPaymentRequestBuilder extends AnalyzeMoneyBoxPaymentRequestBuilderBase<EditMoneyBoxClaim>
{
	private EditMoneyBoxClaim document;

	@Override
	public AnalyzeEditMoneyBoxPaymentRequestBuilder append(EditMoneyBoxClaim document)
	{
		this.document = document;
		return this;
	}

	@Override
	protected EditMoneyBoxClaim getBusinessDocument()
	{
		return document;
	}

	@Override
	protected ClientDefinedEventType getClientDefinedEventType()
	{
		return ClientDefinedEventType.EDIT_AUTO_PAYMENT;
	}
}
