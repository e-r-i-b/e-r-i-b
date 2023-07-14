package com.rssl.phizic.business.fraudMonitoring.senders.templates;

import com.rssl.phizic.business.documents.templates.impl.InternalTransferTemplate;
import com.rssl.phizic.business.fraudMonitoring.senders.templates.builders.AnalyzeCardsTransferTemplateRequestBuilder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;

/**
 * Cендер во ФМ для шаблона перевода между моими картами (тип analyze)
 *
 * @author khudyakov
 * @ created 16.02.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeCardsTransferTemplateSender extends TemplateFraudMonitoringSenderBase
{
	private InternalTransferTemplate template;

	public AnalyzeCardsTransferTemplateSender(InternalTransferTemplate template)
	{
		this.template = template;
	}

	@Override
	protected AnalyzeRequest createOnLineRequest() throws GateException, GateLogicException
	{
		return new AnalyzeCardsTransferTemplateRequestBuilder()
				.append(template)
				.build();
	}

	@Override
	protected FraudAuditedObject getFraudAuditedObject()
	{
		return template;
	}
}
