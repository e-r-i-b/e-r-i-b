package com.rssl.phizic.business.fraudMonitoring.senders.templates;

import com.rssl.phizic.business.documents.templates.impl.IndividualTransferTemplate;
import com.rssl.phizic.business.fraudMonitoring.senders.templates.builders.AnalyzeToAccountRurPaymentTemplateRequestBuilder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;

/**
 * ������ �� �� ��� ������� �������� � �����/������ �� �� ���� � "�����" ����� ��� �� ���� � ������ ���� ����� ��������� ������� ������
 *
 * @author khudyakov
 * @ created 18.02.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeToAccountRurPaymentTemplateSender extends TemplateFraudMonitoringSenderBase
{
	private IndividualTransferTemplate template;

	public AnalyzeToAccountRurPaymentTemplateSender(IndividualTransferTemplate template)
	{
		this.template = template;
	}

	@Override
	protected AnalyzeRequest createOnLineRequest() throws GateException, GateLogicException
	{
		return new AnalyzeToAccountRurPaymentTemplateRequestBuilder()
				.append(template)
				.build();
	}

	@Override
	protected FraudAuditedObject getFraudAuditedObject()
	{
		return template;
	}
}
