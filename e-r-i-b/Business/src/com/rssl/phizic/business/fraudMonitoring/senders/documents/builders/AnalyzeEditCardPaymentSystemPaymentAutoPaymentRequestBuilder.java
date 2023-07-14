package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.phizic.business.documents.payments.EditAutoPaymentImpl;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact;
import com.rssl.phizic.rsa.integration.ws.control.generated.DataType;
import com.rssl.phizic.rsa.senders.ClientDefinedFactBuilder;
import com.rssl.phizic.rsa.senders.types.ClientDefinedEventType;
import com.rssl.phizic.rsa.senders.types.ServingSystem;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.LONG_OFFER_FIRST_DATE_ATTRIBUTE_NAME;

/**
 * Билдер запросов для заявок редактирования автоплатежей с карт в IqWave
 *
 * @author khudyakov
 * @ created 18.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeEditCardPaymentSystemPaymentAutoPaymentRequestBuilder extends AnalyzeCardPaymentSystemPaymentLongOfferRequestBuilderBase<EditAutoPaymentImpl>
{
	private EditAutoPaymentImpl document;

	@Override
	public AnalyzeEditCardPaymentSystemPaymentAutoPaymentRequestBuilder append(EditAutoPaymentImpl document)
	{
		this.document = document;
		return this;
	}

	@Override
	protected EditAutoPaymentImpl getBusinessDocument()
	{
		return document;
	}

	protected ServingSystem getServingSystem()
	{
		return ServingSystem.IQWAVE;
	}

	@Override
	protected ClientDefinedEventType getClientDefinedEventType() throws GateException
	{
		return ClientDefinedEventType.EDIT_AUTO_PAYMENT;
	}

	@Override
	protected ClientDefinedFact[] createClientDefinedAttributeList() throws GateException, GateLogicException
	{
		ClientDefinedFactBuilder builder = new ClientDefinedFactBuilder()
				.append(super.createClientDefinedAttributeList());

		Calendar firstPaymentDate = document.getFirstPaymentDate();
		if (firstPaymentDate != null)
		{
			builder.append(LONG_OFFER_FIRST_DATE_ATTRIBUTE_NAME, DateHelper.toISO8601_24HourDateFormat(firstPaymentDate), DataType.STRING);
		}
		return builder.build();
	}
}
