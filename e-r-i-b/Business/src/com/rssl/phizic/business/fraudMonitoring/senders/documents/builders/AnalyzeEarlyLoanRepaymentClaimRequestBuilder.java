package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.phizic.business.documents.payments.EarlyLoanRepaymentClaimImpl;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AccountData;
import com.rssl.phizic.rsa.integration.ws.control.generated.Amount;
import com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact;
import com.rssl.phizic.rsa.integration.ws.control.generated.DataType;
import com.rssl.phizic.rsa.senders.ClientDefinedFactBuilder;
import com.rssl.phizic.rsa.senders.types.*;
import com.rssl.phizic.utils.StringHelper;

import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.FROM_CARD_NUMBER_FIELD_NAME;
import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.GROUND_FIELD_NAME;
import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.LOAN_AGREEMENT_NUMBER_FIELD_NAME;

/**
 * Билдер запроса для заявки погашения кредита
 *
 * @author khudyakov
 * @ created 24.08.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeEarlyLoanRepaymentClaimRequestBuilder extends AnalyzeDocumentRequestBuilderBase<EarlyLoanRepaymentClaimImpl>
{
	private EarlyLoanRepaymentClaimImpl document;

	@Override
	public AnalyzeDocumentRequestBuilderBase append(EarlyLoanRepaymentClaimImpl document)
	{
		this.document = document;
		return this;
	}

	@Override
	protected EarlyLoanRepaymentClaimImpl getBusinessDocument()
	{
		return document;
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
		return ClientDefinedEventType.LOAN_PAYMENT;
	}

	@Override
	protected AccountData createMyAccountData()
	{
		AccountData accountData = new AccountData();
		accountData.setAccountNumber(document.getChargeOffCard());
		accountData.setInternationalAccountNumber(document.getChargeOffCard());
		accountData.setAccountName(document.getPayerName());
		return accountData;
	}

	@Override
	protected AccountData createOtherAccountData()
	{
		AccountData accountData = new AccountData();
		accountData.setAccountNumber(document.getLoanIdentifier());
		accountData.setInternationalAccountNumber(document.getLoanIdentifier());
		accountData.setAccountName(document.getLoanIdentifier());
		return accountData;
	}

	@Override
	protected ClientDefinedFact[] createClientDefinedAttributeList() throws GateException, GateLogicException
	{
		return new ClientDefinedFactBuilder()
				.append(super.createClientDefinedAttributeList())
				.append(GROUND_FIELD_NAME, StringHelper.getEmptyIfNull(document.getGround()), DataType.STRING)
				.append(FROM_CARD_NUMBER_FIELD_NAME, document.getChargeOffCard(), DataType.STRING)
				.append(LOAN_AGREEMENT_NUMBER_FIELD_NAME, document.getLoanIdentifier(), DataType.STRING)
				.build();
	}

	protected Amount getAmount()
	{
		Money money = document.getRepaymentAmount();
		if (money == null)
		{
			return null;
		}

		return new Amount(money.getAsCents(), null, money.getCurrency().getCode());
	}
}
