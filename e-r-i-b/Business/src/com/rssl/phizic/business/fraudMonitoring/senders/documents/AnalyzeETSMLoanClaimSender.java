package com.rssl.phizic.business.fraudMonitoring.senders.documents;

import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.fraudMonitoring.senders.documents.builders.AnalyzeETSMLoanClaimRequestBuilder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.rsa.exceptions.RequireAdditionConfirmFraudGateException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;

/**
 * Cендер во ФМ для заявки на открыти кредита (тип analyze)
 *
 * @author khudyakov
 * @ created 12.02.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeETSMLoanClaimSender extends DocumentFraudMonitoringSenderBase
{
	private ExtendedLoanClaim document;

	public AnalyzeETSMLoanClaimSender(ExtendedLoanClaim document)
	{
		this.document = document;
	}

	@Override
	protected AnalyzeRequest createOnLineRequest() throws GateException, GateLogicException
	{
		return new AnalyzeETSMLoanClaimRequestBuilder()
				.append(document)
				.append(getInitializationData())
				.build();
	}

	@Override
	protected FraudAuditedObject getFraudAuditedObject()
	{
		return document;
	}

	public void send() throws GateLogicException
	{
		try
		{
			super.send();
		}
		catch (RequireAdditionConfirmFraudGateException e)
		{
			//по РО заявка на оформление кредита в КЦ не переводится
			log.error(e);
		}
	}
}
