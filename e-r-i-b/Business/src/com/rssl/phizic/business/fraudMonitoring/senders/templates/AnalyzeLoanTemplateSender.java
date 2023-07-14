package com.rssl.phizic.business.fraudMonitoring.senders.templates;

import com.rssl.phizic.business.documents.templates.impl.LoanTransferTemplate;
import com.rssl.phizic.business.fraudMonitoring.senders.templates.builders.AnalyzeLoanTemplateRequestBuilder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;

/**
 * Сендер во ФМ для шаблона заявки погашения кредита (тип analyze)
 *
 * @author khudyakov
 * @ created 16.02.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeLoanTemplateSender extends TemplateFraudMonitoringSenderBase
{
	private LoanTransferTemplate template;

	public AnalyzeLoanTemplateSender(LoanTransferTemplate template)
	{
		this.template = template;
	}

	@Override
	protected AnalyzeRequest createOnLineRequest() throws GateException, GateLogicException
	{
		return new AnalyzeLoanTemplateRequestBuilder()
				.append(template)
				.build();
	}

	@Override
	protected FraudAuditedObject getFraudAuditedObject()
	{
		return template;
	}
}
