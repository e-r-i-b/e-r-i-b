package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.phizic.business.documents.payments.CreateMoneyBoxPayment;
import com.rssl.phizic.rsa.senders.types.ClientDefinedEventType;

/**
 * Класс билдера запросов заявок создания копилок
 *
 * @author khudyakov
 * @ created 18.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeCreateMoneyBoxPaymentRequestBuilder extends AnalyzeMoneyBoxPaymentRequestBuilderBase<CreateMoneyBoxPayment>
{
	private CreateMoneyBoxPayment document;

	@Override
	public AnalyzeDocumentRequestBuilderBase append(CreateMoneyBoxPayment document)
	{
		this.document = document;
		return this;
	}

	@Override
	protected CreateMoneyBoxPayment getBusinessDocument()
	{
		return document;
	}

	@Override
	protected ClientDefinedEventType getClientDefinedEventType()
	{
		return ClientDefinedEventType.CREATE_AUTO_PAYMENT;
	}

}
