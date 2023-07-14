package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.phizic.business.documents.payments.CreateAutoPaymentImpl;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact;
import com.rssl.phizic.rsa.integration.ws.control.generated.DataType;
import com.rssl.phizic.rsa.senders.ClientDefinedFactBuilder;
import com.rssl.phizic.rsa.senders.ClientDefinedFactConstants;
import com.rssl.phizic.rsa.senders.types.ClientDefinedEventType;
import com.rssl.phizic.rsa.senders.types.ServingSystem;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * Билдер запросов для заявок создания автоплатежей с карт в IqWave
 *
 * @author khudyakov
 * @ created 18.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeCreateCardPaymentSystemPaymentAutoPaymentRequestBuilder extends AnalyzeCardPaymentSystemPaymentLongOfferRequestBuilderBase<CreateAutoPaymentImpl>
{
	private CreateAutoPaymentImpl document;

	@Override
	public AnalyzeDocumentRequestBuilderBase append(CreateAutoPaymentImpl document)
	{
		this.document = document;
		return this;
	}

	@Override
	protected CreateAutoPaymentImpl getBusinessDocument()
	{
		return document;
	}

	@Override
	protected ServingSystem getServingSystem()
	{
		 return ServingSystem.IQWAVE;
	}

	@Override
	protected ClientDefinedEventType getClientDefinedEventType()
	{
		return ClientDefinedEventType.CREATE_AUTO_PAYMENT;
	}

	@Override
	protected ClientDefinedFact[] createClientDefinedAttributeList() throws GateException, GateLogicException
	{
		ClientDefinedFactBuilder builder = new ClientDefinedFactBuilder()
				.append(super.createClientDefinedAttributeList());

		Calendar firstPaymentDate = document.getFirstPaymentDate();
		if (firstPaymentDate != null)
		{
			builder.append(ClientDefinedFactConstants.LONG_OFFER_FIRST_DATE_ATTRIBUTE_NAME, DateHelper.toISO8601_24HourDateFormat(firstPaymentDate), DataType.STRING);
		}
		return builder.build();
	}
}
