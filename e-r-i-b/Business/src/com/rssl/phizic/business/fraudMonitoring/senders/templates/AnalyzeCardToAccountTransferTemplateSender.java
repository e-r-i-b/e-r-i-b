package com.rssl.phizic.business.fraudMonitoring.senders.templates;

import com.rssl.phizic.business.documents.templates.impl.ConvertCurrencyTransferTemplate;
import com.rssl.phizic.business.documents.templates.impl.IMATransferTemplate;
import com.rssl.phizic.business.documents.templates.impl.InternalTransferTemplate;
import com.rssl.phizic.business.fraudMonitoring.senders.templates.builders.AnalyzeAccountToCardTransferTemplateRequestBuilder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;

/**
 * Cендер во ФМ для шаблонов перевода с карты на счет (тип analyze)
 *
 * @author khudyakov
 * @ created 16.02.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeCardToAccountTransferTemplateSender extends TemplateFraudMonitoringSenderBase
{
	private InternalTransferTemplate internalTransferTemplate;
	private ConvertCurrencyTransferTemplate convertCurrencyTransferTemplate;
	private IMATransferTemplate imaTransferTemplate;
	private Type type;

	public AnalyzeCardToAccountTransferTemplateSender(InternalTransferTemplate internalTransferTemplate)
	{
		this.internalTransferTemplate = internalTransferTemplate;
		this.type = Type.InternalTransferTemplate;
	}

	public AnalyzeCardToAccountTransferTemplateSender(ConvertCurrencyTransferTemplate convertCurrencyTransferTemplate)
	{
		this.convertCurrencyTransferTemplate = convertCurrencyTransferTemplate;
		this.type = Type.ConvertCurrencyTransferTemplate;
	}

	public AnalyzeCardToAccountTransferTemplateSender(IMATransferTemplate imaTransferTemplate)
	{
		this.imaTransferTemplate = imaTransferTemplate;
		this.type = Type.IMATransferTemplate;
	}

	@Override
	protected AnalyzeRequest createOnLineRequest() throws GateException, GateLogicException
	{
		switch (type)
		{
			case IMATransferTemplate :
			{
				return new AnalyzeAccountToCardTransferTemplateRequestBuilder<IMATransferTemplate>()
						.append(imaTransferTemplate)
						.build();
			}
			case InternalTransferTemplate :
			{
				return new AnalyzeAccountToCardTransferTemplateRequestBuilder<InternalTransferTemplate>()
						.append(internalTransferTemplate)
						.build();
			}
			case ConvertCurrencyTransferTemplate :
			{
				return new AnalyzeAccountToCardTransferTemplateRequestBuilder<ConvertCurrencyTransferTemplate>()
						.append(convertCurrencyTransferTemplate)
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
			case IMATransferTemplate :
			{
				return imaTransferTemplate;
			}
			case InternalTransferTemplate :
			{
				return internalTransferTemplate;
			}
			case ConvertCurrencyTransferTemplate :
			{
				return convertCurrencyTransferTemplate;
			}
			default : throw new IllegalArgumentException();
		}
	}

	private enum Type
	{
		InternalTransferTemplate,
		ConvertCurrencyTransferTemplate,
		IMATransferTemplate
	}
}
