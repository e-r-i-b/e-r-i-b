package com.rssl.phizic.business.fraudMonitoring.senders.documents;

import com.rssl.phizic.business.documents.payments.EditMoneyBoxClaim;
import com.rssl.phizic.business.fraudMonitoring.senders.documents.builders.AnalyzeEditMoneyBoxPaymentRequestBuilder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;

/**
 * Cендер во ФМ для всех заявок редактирования копилки в Автопай (тип analyze)
 *
 * @author khudyakov
 * @ created 12.02.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeEditMoneyBoxPaymentSender extends DocumentFraudMonitoringSenderBase
{
	private EditMoneyBoxClaim document;

	public AnalyzeEditMoneyBoxPaymentSender(EditMoneyBoxClaim document)
	{
		this.document = document;
	}

	@Override
	protected AnalyzeRequest createOnLineRequest() throws GateException, GateLogicException
	{
		return new AnalyzeEditMoneyBoxPaymentRequestBuilder()
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
