package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.LoanClaim;
import com.rssl.phizic.business.loanclaim.type.LoanRate;
import com.rssl.phizic.business.persons.PersonHelper;
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

import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.*;

/**
 * Базовый класс билдеров кредитных заявок
 *
 * @author khudyakov
 * @ created 18.06.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class LoanClaimRequestBuilderBase<BD extends BusinessDocument> extends AnalyzeDocumentRequestBuilderBase<BD>
{
	@Override
	protected AccountData createOtherAccountData() throws GateException
	{
		return null;
	}

	@Override
	protected AccountData createMyAccountData()
	{
		return null;
	}

	@Override
	protected AccountPayeeType getAccountPayeeType()
	{
		return null;
	}

	@Override
	protected BeneficiaryType getBeneficiaryType() throws GateException
	{
		return null;
	}

	@Override
	protected DirectionTransferFundsType getDirectionTransferFundsType()
	{
		return null;
	}

	@Override
	protected ExecutionPeriodicityType getExecutionPeriodicityType()
	{
		return null;
	}

	@Override
	protected WayTransferOfFundsType getWayTransferOfFundsType() throws GateException
	{
		return null;
	}

	@Override
	protected ClientDefinedEventType getClientDefinedEventType() throws GateException
	{
		return null;
	}

	@Override
	protected EventsType getEventsType()
	{
		return EventsType.REQUEST_CREDIT;
	}

	protected abstract Long getLoanPeriod();

	protected abstract LoanRate getLoanRate() throws BusinessException, GateException;

	@Override
	protected ClientDefinedFact[] createClientDefinedAttributeList() throws GateException, GateLogicException
	{
		try
		{
			ClientDefinedFactBuilder builder = new ClientDefinedFactBuilder()
					.append(PERSON_VSP_NUMBER_FIELD_NAME, StringHelper.getEmptyIfNull(PersonHelper.getPersonOSBVSP(getDocumentOwner())), DataType.STRING)
					.append(LOAN_AUTOMATIC_ISSUE_FIELD_NAME, NO, DataType.STRING);

			Long loanPeriod = getLoanPeriod();
			if (loanPeriod != null)
			{
				builder.append(LOAN_TERM_FIELD_NAME, Long.toString(loanPeriod), DataType.STRING);
			}

			LoanRate loanRate = getLoanRate();
			if (loanRate != null)
			{
				builder.append(LOAN_INTEREST_RATE_FIELD_NAME, getLoanRateValue(loanRate), DataType.STRING);
			}

			return builder.build();
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	private String getLoanRateValue(LoanRate loanRate)
	{
		StringBuilder rate = new StringBuilder();
		if (loanRate.getMinLoanRate() != null)
		{
			rate.append(FROM_FIELD_NAME).append(loanRate.getMinLoanRate()).append(" ");
		}
		if (loanRate.getMaxLoanRate() != null)
		{
			rate.append(TO_FIELD_NAME).append(loanRate.getMaxLoanRate());
		}
		return rate.toString();
	}

	protected Amount getAmount()
	{
		LoanClaim loanClaim = (LoanClaim)getBusinessDocument();
		Money loanAmount = loanClaim.getLoanAmount();
		return new Amount(loanAmount.getAsCents(), null, loanAmount.getCurrency().getCode());
	}
}
