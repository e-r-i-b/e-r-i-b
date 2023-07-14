package com.rssl.phizic.business.fraudMonitoring.senders.documents;

import com.rssl.phizic.business.documents.payments.EarlyLoanRepaymentClaimImpl;
import com.rssl.phizic.business.documents.payments.LoanPayment;
import com.rssl.phizic.business.fraudMonitoring.senders.documents.builders.AnalyzeEarlyLoanRepaymentClaimRequestBuilder;
import com.rssl.phizic.business.fraudMonitoring.senders.documents.builders.AnalyzeLoanPaymentRequestBuilder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.rsa.exceptions.RequireAdditionConfirmFraudGateException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;

/**
 * Сендер во ФМ для заявки погашения кредита (тип analyze)
 *
 * @author khudyakov
 * @ created 16.02.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeLoanPaymentSender extends DocumentFraudMonitoringSenderBase
{
	private LoanPayment loanPayment;
	private EarlyLoanRepaymentClaimImpl earlyLoanRepaymentClaim;
	private Type type;

	public AnalyzeLoanPaymentSender(LoanPayment loanPayment)
	{
	 	this.loanPayment = loanPayment;
		this.type = Type.LoanPayment;
	}

	public AnalyzeLoanPaymentSender(EarlyLoanRepaymentClaimImpl earlyLoanRepaymentClaim)
	{
	 	this.earlyLoanRepaymentClaim = earlyLoanRepaymentClaim;
		this.type = Type.EarlyLoanRepaymentClaimImpl;
	}

	@Override
	protected AnalyzeRequest createOnLineRequest() throws GateException, GateLogicException
	{
		switch (type)
		{
			case LoanPayment:
			{
				return new AnalyzeLoanPaymentRequestBuilder()
						.append(loanPayment)
						.append(getInitializationData())
						.build();
			}
			case EarlyLoanRepaymentClaimImpl:
			{
				return new AnalyzeEarlyLoanRepaymentClaimRequestBuilder()
						.append(earlyLoanRepaymentClaim)
						.append(getInitializationData())
						.build();
			}
			default: throw new IllegalArgumentException();
		}
	}

	@Override
	protected FraudAuditedObject getFraudAuditedObject()
	{
		switch (type)
		{
			case LoanPayment:
			{
				return loanPayment;
			}
			case EarlyLoanRepaymentClaimImpl:
			{
				return earlyLoanRepaymentClaim;
			}
			default: throw new IllegalArgumentException();
		}
	}

	public void send() throws GateLogicException
	{
		try
		{
			super.send();
		}
		catch (RequireAdditionConfirmFraudGateException e)
		{
			//по РО в КЦ не переводим
			log.error(e.getMessage(), e);
		}
	}

	private enum Type
	{
		LoanPayment,
		EarlyLoanRepaymentClaimImpl
	}
}
