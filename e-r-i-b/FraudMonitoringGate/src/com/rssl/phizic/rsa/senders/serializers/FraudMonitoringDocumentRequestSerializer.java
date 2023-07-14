package com.rssl.phizic.rsa.senders.serializers;

import com.rssl.phizic.rsa.integration.ws.control.generated.*;
import com.rssl.phizic.rsa.senders.enumeration.FraudMonitoringRequestType;
import org.w3c.dom.Node;

import static com.rssl.phizic.rsa.senders.serializers.support.RSARequestXMLDocumentConstants.*;

/**
 * @author tisov
 * @ created 07.07.15
 * @ $Author$
 * @ $Revision$
 * Сериализатор фрод-запросов по документам
 */
public class FraudMonitoringDocumentRequestSerializer extends FraudMonitoringAnalyzeRequestSerializerBase
{
	@Override
	protected FraudMonitoringRequestType getRequestType()
	{
		return FraudMonitoringRequestType.BY_DOCUMENT;
	}

	private Node createAmountTag(TransactionData transactionData)
	{
		Node result = createElement(AMOUNT);

		result.appendChild(createSimpleTag(AMOUNT_VALUE, transactionData.getAmount().getAmount().toString()));
		result.appendChild(createSimpleTag(CURRENCY, transactionData.getAmount().getCurrency()));

		return result;
	}

	private Node createOtherAccountTag(TransactionData transactionData)
	{
		AccountData otherAccountData = transactionData.getOtherAccountData();

		Node result = createElement(OTHER_ACCOUNT_DATA);

		result.appendChild(createSimpleTag(ACCOUNT_NUMBER, otherAccountData.getAccountNumber()));
		result.appendChild(createSimpleTag(INTERNATIONAL_ACCOUNT_NUMBER, otherAccountData.getInternationalAccountNumber()));

		appendSimpleTagIfNotNull(result, ACCOUNT_NAME, otherAccountData.getAccountName());
		appendSimpleTagIfNotNull(result, ACCOUNT_COUNTRY, otherAccountData.getAccountCountry());
		appendSimpleTagIfNotNull(result, ROUTING_CODE, otherAccountData.getRoutingCode());
		appendSimpleTagIfNotNull(result, REFERENCE_CODE, otherAccountData.getReferenceCode());

		return result;
	}

	private Node createMyAccountTag(TransactionData transactionData)
	{
		AccountData myAccountData = transactionData.getMyAccountData();

		Node result = createElement(MY_ACCOUNT_DATA);

		result.appendChild(createSimpleTag(ACCOUNT_NUMBER, myAccountData.getAccountNumber()));
		result.appendChild(createSimpleTag(INTERNATIONAL_ACCOUNT_NUMBER, myAccountData.getInternationalAccountNumber()));

		return result;
	}

	private Node createEventTransactionDataTag(EventData eventData)
	{

		TransactionData transactionData = eventData.getTransactionData();
		Node result = createElement(TRANSACTION_DATA);

		if (transactionData.getTransferMediumType() != null)
		{
			result.appendChild(createSimpleTag(TRANSFER_MEDIUM_TYPE, transactionData.getTransferMediumType().getValue()));
		}

		if (transactionData.getSchedule() != null)
		{
			result.appendChild(createSimpleTag(SCHEDULE, transactionData.getSchedule().getValue()));
		}

		appendSimpleTagIfNotNull(result, DUE_DATE, transactionData.getDueDate());

		if (transactionData.getRecurringFrequency() != null)
		{
			appendSimpleTagIfNotNull(result, RECURRING_FREQUENCY, transactionData.getRecurringFrequency().toString());
		}

		if (transactionData.getExecutionSpeed() != null)
		{
			result.appendChild(createSimpleTag(EXECUTION_SPEED, transactionData.getExecutionSpeed().getValue()));
		}

		result.appendChild(createAmountTag(transactionData));
		result.appendChild(createSimpleTag(OTHER_ACCOUNT_TYPE, transactionData.getOtherAccountType().getValue()));

		if (transactionData.getOtherAccountBankType() != null)
		{
			result.appendChild(createSimpleTag(OTHER_ACCOUNT_BANK_TYPE, transactionData.getOtherAccountBankType().getValue()));
		}

		if (transactionData.getOtherAccountOwnershipType() != null)
		{
			result.appendChild(createSimpleTag(OTHER_ACCOUNT_OWNERSHIP_TYPE, transactionData.getOtherAccountOwnershipType().getValue()));
		}

		result.appendChild(createOtherAccountTag(transactionData));
		result.appendChild(createMyAccountTag(transactionData));

		return result;

	}

	@Override
	protected Node createEventDataTag()
	{
		EventData eventData = getRequest().getEventDataList()[0];

		Node result = super.createEventDataTag();

		result.appendChild(createEventTransactionDataTag(eventData));

		return result;
	}
}
