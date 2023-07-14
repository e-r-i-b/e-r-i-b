package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.phizic.business.documents.payments.AutoSubType;
import com.rssl.phizic.business.documents.payments.EditAutoSubscriptionPayment;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.Amount;
import com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact;
import com.rssl.phizic.rsa.integration.ws.control.generated.DataType;
import com.rssl.phizic.rsa.senders.ClientDefinedFactBuilder;
import com.rssl.phizic.rsa.senders.types.ClientDefinedEventType;
import com.rssl.phizic.rsa.senders.types.ServingSystem;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.LONG_OFFER_FIRST_DATE_ATTRIBUTE_NAME;

/**
 * Билдер запросов для заявок редактирования автоплатежей с карт в БС через шину
 *
 * @author khudyakov
 * @ created 18.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeEditCardPaymentSystemPaymentAutoSubscriptionRequestBuilder extends AnalyzeCardPaymentSystemPaymentLongOfferRequestBuilderBase<EditAutoSubscriptionPayment>
{
	private EditAutoSubscriptionPayment document;

	@Override
	public AnalyzeDocumentRequestBuilderBase append(EditAutoSubscriptionPayment document)
	{
		this.document = document;
		return this;
	}

	@Override
	protected EditAutoSubscriptionPayment getBusinessDocument()
	{
		return document;
	}

	@Override
	protected ServingSystem getServingSystem()
	{
		return ServingSystem.AUTOPAY;
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

		if (ServingSystem.IQWAVE == getServingSystem())
		{
			Calendar firstPaymentDate = document.getFirstPaymentDate();
			if (firstPaymentDate != null)
			{
				builder.append(LONG_OFFER_FIRST_DATE_ATTRIBUTE_NAME, DateHelper.toISO8601_24HourDateFormat(firstPaymentDate), DataType.STRING);
			}
		}

		return builder.build();
	}

	@Override
	protected Amount getAmount()
	{
		Money result;
   	    if (AutoSubType.INVOICE == this.document.getAutoSubType())
	    {
			result = this.document.getMaxSumWritePerMonth();
	    }
		else if (AutoSubType.ALWAYS == this.document.getAutoSubType())
	    {
			result = this.document.getDestinationAmount();
	    }
		else
	    {
			return null;
	    }

		return new Amount(result.getAsCents(), null, result.getCurrency().getCode());
	}
}
