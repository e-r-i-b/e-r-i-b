package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact;
import com.rssl.phizic.rsa.integration.ws.control.generated.DataType;
import com.rssl.phizic.rsa.senders.ClientDefinedFactBuilder;
import com.rssl.phizic.rsa.senders.types.ExecutionPeriodicityType;
import com.rssl.phizic.rsa.senders.types.ServingSystem;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.*;

/**
 * Базовый билдер запросов для всех заявок создания автоплатежа в Автопай (тип analyze)
 *
 * @author khudyakov
 * @ created 14.06.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class AnalyzeAutoSubscriptionClaimRequestBuilderBase<BD extends BusinessDocument> extends AnalyzeDocumentRequestBuilderBase<BD>
{
	protected abstract ServingSystem getServingSystem();

	@Override
	protected ExecutionPeriodicityType getExecutionPeriodicityType()
	{
		return ExecutionPeriodicityType.SCHEDULED;
	}

	protected ClientDefinedFact[] createAutoPaymentData(LongOffer longOffer)
	{
		ClientDefinedFactBuilder builder = new ClientDefinedFactBuilder()
				.append(LONG_OFFER_NAME_ATTRIBUTE_NAME, longOffer.getFriendlyName(), DataType.STRING)
				.append(LONG_OFFER_TYPE_ATTRIBUTE_NAME, longOffer.getExecutionEventType().name(), DataType.STRING);

		Calendar startDate = longOffer.getStartDate();
		if (startDate != null)
		{
			builder.append(LONG_OFFER_START_DATE_ATTRIBUTE_NAME, DateHelper.toISO8601_24HourDateFormat(startDate), DataType.STRING);
		}

		ExecutionEventType eventType = longOffer.getExecutionEventType();
		if (ExecutionEventType.REDUSE_OF_BALANCE == eventType)
		{
			Money floorLimit = longOffer.getFloorLimit();
			if (floorLimit != null)
			{
				builder.append(LONG_OFFER_FLOOR_LIMIT_ATTRIBUTE_NAME, Long.toString(floorLimit.getAsCents()), DataType.STRING);
			}

			Money totalAmountLimit = longOffer.getTotalAmountLimit();
			if (totalAmountLimit != null)
			{
				builder.append(LONG_OFFER_TOTAL_AMOUNT_LIMIT_ATTRIBUTE_NAME, Long.toString(totalAmountLimit.getAsCents()), DataType.STRING);
			}
		}

		if (ExecutionEventType.BY_INVOICE == eventType)
		{
			Money floorLimit = longOffer.getFloorLimit();
			if (floorLimit != null)
			{
				builder.append(LONG_OFFER_FLOOR_LIMIT_ATTRIBUTE_NAME, Long.toString(floorLimit.getAsCents()), DataType.STRING);
			}
		}

		builder.append(LONG_OFFER_SYSTEM_ATTRIBUTE_NAME, getServingSystem().name(), DataType.STRING);
		return builder.build();
	}
}
