package com.rssl.phizic.business.fraudMonitoring.senders.documents;

import com.rssl.phizic.business.documents.AccountClosingPayment;
import com.rssl.phizic.business.fraudMonitoring.senders.documents.builders.AnalyzeAccountClosingClaimRequestBuilder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;

/**
 * Cендер во ФМ для заявок закрытия вклада (тип analyze)
 *
 * @author khudyakov
 * @ created 16.02.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeAccountClosingClaimSender extends DocumentFraudMonitoringSenderBase
{
	private AccountClosingPayment document;

	public AnalyzeAccountClosingClaimSender(AccountClosingPayment document)
	{
		this.document = document;
	}

	protected AnalyzeRequest createOnLineRequest() throws GateException, GateLogicException
	{
		return new AnalyzeAccountClosingClaimRequestBuilder()
				.append(document)
				.append(getInitializationData())
				.build();
	}

	@Override
	protected FraudAuditedObject getFraudAuditedObject()
	{
		return document;
	}
}
