package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.phizic.business.documents.InternalTransfer;
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
 * Базовый класс билдера запросов заявок создания/редактирования копилок
 *
 * @author khudyakov
 * @ created 18.06.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class AnalyzeMoneyBoxPaymentRequestBuilderBase<BD extends InternalTransfer> extends AnalyzeAutoSubscriptionClaimRequestBuilderBase<BD>
{
	@Override
	protected ServingSystem getServingSystem()
	{
		return ServingSystem.AUTOPAY;
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
	protected WayTransferOfFundsType getWayTransferOfFundsType() throws GateException
	{
		return WayTransferOfFundsType.INTERNAL;
	}

	@Override
	protected AccountData createMyAccountData()
	{
		return createMyAccountData(getBusinessDocument());
	}

	protected AccountData createMyAccountData(InternalTransfer payment)
	{
		AccountData accountData = new AccountData();
		accountData.setAccountNumber(payment.getChargeOffCard());
		accountData.setInternationalAccountNumber(payment.getChargeOffCard());
		accountData.setAccountName(payment.getPayerName());
		return accountData;
	}

	@Override
	protected AccountData createOtherAccountData()
	{
		return createOtherAccountData(getBusinessDocument());
	}

	protected AccountData createOtherAccountData(InternalTransfer payment)
	{
		AccountData accountData = new AccountData();
		accountData.setAccountNumber(payment.getReceiverAccount());
		accountData.setInternationalAccountNumber(payment.getReceiverAccount());
		accountData.setAccountName(payment.getPayerName());
		return accountData;
	}

	@Override
	protected ClientDefinedFact[] createClientDefinedAttributeList() throws GateException, GateLogicException
	{
		InternalTransfer document = getBusinessDocument();
		ClientDefinedFactBuilder builder = new ClientDefinedFactBuilder()
				.append(super.createClientDefinedAttributeList())
				.append(createAutoPaymentData(document))
				.append(GROUND_FIELD_NAME, StringHelper.getEmptyIfNull(document.getGround()), DataType.STRING)
				.append(FROM_CARD_NUMBER_FIELD_NAME, document.getChargeOffCard(), DataType.STRING)
				.append(TO_ACCOUNT_NUMBER_FIELD_NAME, document.getReceiverAccount(), DataType.STRING);

		if (StringHelper.isNotEmpty(document.getReceiverAccount()))
		{
			builder.append(RECEIVER_BAL_ACCOUNT_FIELD_NAME, document.getReceiverAccount().substring(0, 5), DataType.STRING);
		}

		return builder.build();
	}
}
