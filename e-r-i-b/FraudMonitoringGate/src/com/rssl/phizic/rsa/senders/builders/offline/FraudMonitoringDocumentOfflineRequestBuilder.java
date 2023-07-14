package com.rssl.phizic.rsa.senders.builders.offline;

import com.rssl.phizic.rsa.integration.ws.control.generated.*;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import static com.rssl.phizic.rsa.senders.serializers.support.RSARequestXMLDocumentConstants.*;

/**
 * @author tisov
 * @ created 10.07.15
 * @ $Author$
 * @ $Revision$
 * Билдер оффлайн-запросов по документам
 */
public class FraudMonitoringDocumentOfflineRequestBuilder extends FraudMonitoringAnalyzeOfflineRequestBuilder
{

	private Amount createAmount(Node node)
	{
		Amount result = new Amount();
		Element elem = (Element) node;

		result.setAmount(Long.valueOf(XmlHelper.getSimpleElementValue(elem, AMOUNT_VALUE)));
		result.setCurrency(XmlHelper.getSimpleElementValue(elem, CURRENCY));

		return result;
	}

	private AccountData createMyAccountData(Node node)
	{
		AccountData result = new AccountData();
		Element element = (Element)node;

		result.setAccountNumber(XmlHelper.getSimpleElementValue(element, ACCOUNT_NUMBER));
		result.setInternationalAccountNumber(XmlHelper.getSimpleElementValue(element, INTERNATIONAL_ACCOUNT_NUMBER));

		return result;
	}

	private AccountData createOtherAccountData(Node node)
	{
		AccountData result = new AccountData();
		Element element = (Element)node;

		result.setAccountNumber(XmlHelper.getSimpleElementValue(element, ACCOUNT_NUMBER));
		result.setInternationalAccountNumber(XmlHelper.getSimpleElementValue(element, INTERNATIONAL_ACCOUNT_NUMBER));

		result.setAccountName(XmlHelper.getSimpleElementValue(element, ACCOUNT_NAME));
		result.setAccountCountry(XmlHelper.getSimpleElementValue(element, ACCOUNT_COUNTRY));
		result.setRoutingCode(XmlHelper.getSimpleElementValue(element, ROUTING_CODE));
		result.setReferenceCode(XmlHelper.getSimpleElementValue(element, REFERENCE_CODE));

		return result;

	}


	private TransactionData createTransactionData(Node node)
	{
		TransactionData result = new TransactionData();
	   	Element elem = (Element) node;

		String transferMediumType = XmlHelper.getSimpleElementValue(elem, TRANSFER_MEDIUM_TYPE);

		if (transferMediumType != null)
		{
			result.setTransferMediumType(TransferMediumType.fromValue(transferMediumType));
		}

		String schedule = XmlHelper.getSimpleElementValue(elem, SCHEDULE);

		if (schedule != null)
		{
			result.setSchedule(Schedule.fromString(schedule));
		}

		result.setDueDate(XmlHelper.getSimpleElementValue(elem, DUE_DATE));

		String recurringFrequency = XmlHelper.getSimpleElementValue(elem, RECURRING_FREQUENCY);
		if (recurringFrequency != null)
		{
			result.setRecurringFrequency(Integer.valueOf(recurringFrequency));
		}

		String executionSpeed = XmlHelper.getSimpleElementValue(elem, EXECUTION_SPEED);
		if (executionSpeed != null)
		{
			result.setExecutionSpeed(ExecutionSpeed.fromValue(executionSpeed));
		}

		String otherAccountBankType = XmlHelper.getSimpleElementValue(elem, OTHER_ACCOUNT_BANK_TYPE);

		if (otherAccountBankType != null)
		{
			result.setOtherAccountBankType(OtherAccountBankType.fromValue(otherAccountBankType));
		}

		String otherAccountOwnershipType = XmlHelper.getSimpleElementValue(elem, OTHER_ACCOUNT_OWNERSHIP_TYPE);

		if (otherAccountBankType != null)
		{
			result.setOtherAccountOwnershipType(OtherAccountOwnershipType.fromValue(otherAccountOwnershipType));
		}

		result.setAmount(createAmount(elem.getElementsByTagName(AMOUNT).item(0)));
		result.setMyAccountData(createMyAccountData(elem.getElementsByTagName(MY_ACCOUNT_DATA).item(0)));
		result.setOtherAccountData(createOtherAccountData(elem.getElementsByTagName(OTHER_ACCOUNT_DATA).item(0)));

		return result;
	}

	@Override
	protected EventData createEventData(Node node)
	{
		EventData result = super.createEventData(node);

		result.setTransactionData(createTransactionData(node));

		return result;
	}
}
