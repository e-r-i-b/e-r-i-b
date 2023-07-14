package com.rssl.phizic.business.fraudMonitoring.senders.documents;

import com.rssl.phizic.business.documents.payments.*;
import com.rssl.phizic.business.fraudMonitoring.senders.documents.builders.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.rsa.exceptions.RequireAdditionConfirmFraudGateException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;

/**
 * Cендер во ФМ для заявки на открыти карты (тип analyze)
 *
 * @author khudyakov
 * @ created 25.02.15
 * @ $Author$
 * @ $Revision$
 * Сендер(Фродмониторинг) анализа заявки на кредитную карту
 */
public class AnalyzeIssueCardClaimSender extends DocumentFraudMonitoringSenderBase
{
	private LoanCardOfferClaim loanCardOfferClaim;
	private LoanCardProductClaim loanCardProductClaim;
	private VirtualCardClaim virtualCardClaim;
	private PreapprovedLoanCardClaim preApprovedLoanCardClaim;
	private LoanCardClaim loanCardClaim;
	private Type type;

	public AnalyzeIssueCardClaimSender(LoanCardOfferClaim _loanCardOfferClaim)
	{
		loanCardOfferClaim = _loanCardOfferClaim;
		type = Type.LoanCardOfferClaim;
	}

	public AnalyzeIssueCardClaimSender(LoanCardProductClaim _loanCardProductClaim)
	{
		loanCardProductClaim = _loanCardProductClaim;
		type = Type.LoanCardOfferClaim;
	}

	public AnalyzeIssueCardClaimSender(VirtualCardClaim _virtualCardClaim)
	{
		virtualCardClaim = _virtualCardClaim;
		type = Type.VirtualCardClaim;
	}

	public AnalyzeIssueCardClaimSender(PreapprovedLoanCardClaim _preApprovedLoanCardClaim)
	{
		preApprovedLoanCardClaim = _preApprovedLoanCardClaim;
		type = Type.PreApprovedLoanCardClaim;
	}

	public AnalyzeIssueCardClaimSender(LoanCardClaim loanCardClaim)
	{
		this.loanCardClaim = loanCardClaim;
		type = Type.LoanCardClaim;
	}

	@Override
	protected AnalyzeRequest createOnLineRequest() throws GateException, GateLogicException
	{
		switch (type)
		{
			case LoanCardOfferClaim :
			{
				return new AnalyzeIssueLoanCardOfferClaimRequestBuilder()
						.append(loanCardOfferClaim)
						.append(getInitializationData())
						.build();
			}
			case LoanCardProductClaim :
			{
				return new AnalyzeIssueLoanCardProductClaimRequestBuilder()
						.append(loanCardProductClaim)
						.append(getInitializationData())
						.build();
			}
			case VirtualCardClaim :
			{
				return new AnalyzeIssueVirtualCardClaimRequestBuilder()
						.append(virtualCardClaim)
						.append(getInitializationData())
						.build();
			}
			case PreApprovedLoanCardClaim :
			{
				return new AnalyzeIssuePreApprovedLoanClaimRequestBuilder()
						.append(preApprovedLoanCardClaim)
						.append(getInitializationData())
						.build();
			}
			case LoanCardClaim:
			{
				return new AnalyzeLoanCardClaimBuilder()
						.append(loanCardClaim)
						.append(getInitializationData())
						.build();
			}
			default : throw new IllegalArgumentException();
		}
	}

	@Override
	protected FraudAuditedObject getFraudAuditedObject()
	{
		switch (type)
		{
			case LoanCardOfferClaim :
			{
				return loanCardOfferClaim;
			}
			case LoanCardProductClaim :
			{
				return loanCardProductClaim;
			}
			case VirtualCardClaim :
			{
				return virtualCardClaim;
			}
			case PreApprovedLoanCardClaim :
			{
				return preApprovedLoanCardClaim;
			}
			case LoanCardClaim:
			{
				return loanCardClaim;
			}
			default : throw new IllegalArgumentException();
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
			//по РО заявка на оформление новой карты в КЦ не переводится
			log.error(e);
		}
	}

	private enum Type
	{
		LoanCardOfferClaim,
		LoanCardProductClaim,
		VirtualCardClaim,
		PreApprovedLoanCardClaim,
		LoanCardClaim
	}
}
