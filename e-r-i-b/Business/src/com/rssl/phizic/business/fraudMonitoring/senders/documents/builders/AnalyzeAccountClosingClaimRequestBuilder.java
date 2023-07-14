package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.phizic.business.documents.AccountClosingPayment;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AccountData;
import com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact;
import com.rssl.phizic.rsa.integration.ws.control.generated.DataType;
import com.rssl.phizic.rsa.senders.ClientDefinedFactBuilder;
import com.rssl.phizic.rsa.senders.types.*;
import com.rssl.phizic.utils.StringHelper;

import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.*;

/**
 * Билдер Analyze запроса во ВС ФМ по заявке AccountClosingPayment
 *
 * @author khudyakov
 * @ created 14.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeAccountClosingClaimRequestBuilder extends AnalyzeDocumentRequestBuilderBase<AccountClosingPayment>
{
	private AccountClosingPayment document;

	@Override
	public AnalyzeDocumentRequestBuilderBase append(AccountClosingPayment document)
	{
		this.document = document;
		return this;
	}

	@Override
	protected AccountClosingPayment getBusinessDocument()
	{
		return document;
	}

	@Override
	protected AccountData createMyAccountData()
	{
		AccountData accountData = new AccountData();
		accountData.setAccountNumber(document.getReceiverCard());
		accountData.setInternationalAccountNumber(document.getReceiverCard());
		accountData.setAccountName(document.getPayerName());
		return accountData;
	}

	@Override
	protected AccountData createOtherAccountData()
	{
		ResourceType destinationResourceType = document.getDestinationResourceType();
		if (destinationResourceType == ResourceType.CARD)
		{
			AccountData accountData = new AccountData();
			accountData.setAccountNumber(document.getChargeOffCard());
			accountData.setInternationalAccountNumber(document.getChargeOffAccount());
			accountData.setAccountName(document.getPayerName());
			return accountData;
		}
		if (destinationResourceType == ResourceType.ACCOUNT)
		{
			AccountData accountData = new AccountData();
			accountData.setAccountNumber(document.getReceiverAccount());
			accountData.setInternationalAccountNumber(document.getReceiverAccount());
			accountData.setAccountName(document.getPayerName());
			return accountData;
		}
		throw new IllegalStateException("Невозможно определить тип документа");
	}

	@Override
	protected AccountPayeeType getAccountPayeeType()
	{
		return AccountPayeeType.PERSONAL_ACCOUNT;
	}

	@Override
	protected BeneficiaryType getBeneficiaryType() throws GateException
	{
		return BeneficiaryType.SAME_BANK;
	}

	@Override
	protected DirectionTransferFundsType getDirectionTransferFundsType()
	{
		return DirectionTransferFundsType.ME_TO_ME;
	}

	@Override
	protected ExecutionPeriodicityType getExecutionPeriodicityType()
	{
		return ExecutionPeriodicityType.IMMEDIATE;
	}

	@Override
	protected WayTransferOfFundsType getWayTransferOfFundsType() throws GateException
	{
		return WayTransferOfFundsType.INTERNAL;
	}

	@Override
	protected ClientDefinedEventType getClientDefinedEventType()
	{
		return ClientDefinedEventType.CLOSE_ACCOUNT;
	}

	@Override
	protected ClientDefinedFact[] createClientDefinedAttributeList() throws GateException, GateLogicException
	{
		ResourceType destinationResourceType = document.getDestinationResourceType();
		if (destinationResourceType == ResourceType.CARD)
		{
			return new ClientDefinedFactBuilder()
					.append(super.createClientDefinedAttributeList())
					.append(GROUND_FIELD_NAME, StringHelper.getEmptyIfNull(document.getGround()), DataType.STRING)
					.append(FROM_ACCOUNT_NUMBER_FIELD_NAME, document.getChargeOffAccount(), DataType.STRING)
					.append(TO_CARD_NUMBER_FIELD_NAME, document.getReceiverCard(), DataType.STRING)
					.build();
		}

		if (destinationResourceType == ResourceType.ACCOUNT)
		{
			ClientDefinedFactBuilder builder = new ClientDefinedFactBuilder()
					.append(super.createClientDefinedAttributeList())
					.append(GROUND_FIELD_NAME, StringHelper.getEmptyIfNull(document.getGround()), DataType.STRING)
					.append(FROM_ACCOUNT_NUMBER_FIELD_NAME, document.getChargeOffAccount(), DataType.STRING)
					.append(TO_ACCOUNT_NUMBER_FIELD_NAME, document.getReceiverAccount(), DataType.STRING);

			if (StringHelper.isNotEmpty(document.getReceiverAccount()))
			{
				builder.append(RECEIVER_BAL_ACCOUNT_FIELD_NAME, document.getReceiverAccount().substring(0, 5), DataType.STRING);
			}

			return builder.build();
		}

		throw new IllegalStateException("Невозможно определить тип документа");
	}
}
