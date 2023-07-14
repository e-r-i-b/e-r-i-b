package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.phizic.business.documents.P2PAutoTransferClaimBase;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AccountData;
import com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact;
import com.rssl.phizic.rsa.integration.ws.control.generated.DataType;
import com.rssl.phizic.rsa.senders.ClientDefinedFactBuilder;
import com.rssl.phizic.rsa.senders.types.*;
import com.rssl.phizic.utils.StringHelper;

import static com.rssl.phizic.business.documents.P2PAutoTransferClaimBase.SEVERAL_RECEIVER_TYPE_VALUE;
import static com.rssl.phizic.business.documents.payments.RurPayment.PHIZ_RECEIVER_TYPE_VALUE;
import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.*;

/**
 * Базовый класс билдера заявок на создание/редактирование автопереводов
 *
 * @author khudyakov
 * @ created 18.06.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class AnalyzeP2PAutoTransferClaimRequestBuilderBase<BD extends P2PAutoTransferClaimBase> extends AnalyzeAutoSubscriptionClaimRequestBuilderBase<BD>
{
	@Override
	protected ServingSystem getServingSystem()
	{
		return ServingSystem.AUTOPAY;
	}

	@Override
	protected AccountPayeeType getAccountPayeeType()
	{
		String receiverType = getBusinessDocument().getReceiverType();
		if (SEVERAL_RECEIVER_TYPE_VALUE.equals(receiverType))
		{
			return AccountPayeeType.PERSONAL_ACCOUNT;
		}
		if (PHIZ_RECEIVER_TYPE_VALUE.equals(receiverType))
		{
			return AccountPayeeType.BILLER;
		}
		throw new IllegalArgumentException();
	}

	@Override
	protected BeneficiaryType getBeneficiaryType() throws GateException
	{
		return BeneficiaryType.SAME_BANK;
	}

	@Override
	protected DirectionTransferFundsType getDirectionTransferFundsType()
	{
		String receiverType = getBusinessDocument().getReceiverType();
		if (SEVERAL_RECEIVER_TYPE_VALUE.equals(receiverType))
		{
			return DirectionTransferFundsType.ME_TO_ME;
		}
		if (PHIZ_RECEIVER_TYPE_VALUE.equals(receiverType))
		{
			return DirectionTransferFundsType.ME_TO_YOU;
		}
		throw new IllegalArgumentException();
	}

	@Override
	protected WayTransferOfFundsType getWayTransferOfFundsType() throws GateException
	{
		String receiverType = getBusinessDocument().getReceiverType();
		if (SEVERAL_RECEIVER_TYPE_VALUE.equals(receiverType))
		{
			return WayTransferOfFundsType.INTERNAL;
		}
		if (PHIZ_RECEIVER_TYPE_VALUE.equals(receiverType))
		{
			return WayTransferOfFundsType.WIRE;
		}
		throw new IllegalArgumentException();
	}

	@Override
	protected AccountData createMyAccountData()
	{
		BD document = getBusinessDocument();
		AccountData accountData = new AccountData();
		accountData.setAccountNumber(document.getChargeOffCard());
		accountData.setInternationalAccountNumber(document.getChargeOffCard());
		accountData.setAccountName(document.getPayerName());
		return accountData;
	}

	@Override
	protected AccountData createOtherAccountData()
	{
		BD document = getBusinessDocument();
		AccountData accountData = new AccountData();
		accountData.setAccountNumber(document.getReceiverCard());
		accountData.setInternationalAccountNumber(document.getReceiverCard());
		accountData.setAccountName(document.getReceiverName());
		return accountData;
	}

	@Override
	protected ClientDefinedFact[] createClientDefinedAttributeList() throws GateException, GateLogicException
	{
		BD document = getBusinessDocument();
		return new ClientDefinedFactBuilder()
				.append(super.createClientDefinedAttributeList())
				.append(createAutoPaymentData(document))
				.append(GROUND_FIELD_NAME, StringHelper.getEmptyIfNull(document.getGround()), DataType.STRING)
				.append(FROM_CARD_NUMBER_FIELD_NAME, document.getChargeOffCard(), DataType.STRING)
				.append(TO_CARD_NUMBER_FIELD_NAME, document.getReceiverCard(), DataType.STRING)
				.build();
	}

}
