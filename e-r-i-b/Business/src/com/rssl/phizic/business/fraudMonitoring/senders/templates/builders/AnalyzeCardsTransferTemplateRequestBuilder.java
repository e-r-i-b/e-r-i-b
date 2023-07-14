package com.rssl.phizic.business.fraudMonitoring.senders.templates.builders;

import com.rssl.phizic.business.documents.templates.impl.InternalTransferTemplate;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AccountData;
import com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact;
import com.rssl.phizic.rsa.integration.ws.control.generated.DataType;
import com.rssl.phizic.rsa.senders.ClientDefinedFactBuilder;

import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.FROM_CARD_NUMBER_FIELD_NAME;
import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.TO_CARD_NUMBER_FIELD_NAME;

/**
 * Билдер запросов для шаблона перевода между моими картами (тип analyze)
 *
 * @author khudyakov
 * @ created 19.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeCardsTransferTemplateRequestBuilder extends AnalyzeInternalTransferTemplateRequestBuilderBase<InternalTransferTemplate>
{
	private InternalTransferTemplate template;

	@Override
	public AnalyzeCardsTransferTemplateRequestBuilder append(InternalTransferTemplate template)
	{
		this.template = template;
		return this;
	}

	@Override
	protected InternalTransferTemplate getTemplateDocument()
	{
		return template;
	}

	@Override
	protected AccountData createMyAccountData()
	{
		AccountData accountData = new AccountData();
		accountData.setAccountNumber(template.getChargeOffCard());
		accountData.setInternationalAccountNumber(template.getChargeOffCard());
		accountData.setAccountName(template.getPayerName());
		return accountData;
	}

	@Override
	protected AccountData createOtherAccountData()
	{
		AccountData accountData = new AccountData();
		accountData.setAccountNumber(template.getReceiverCard());
		accountData.setInternationalAccountNumber(template.getReceiverCard());
		accountData.setAccountName(template.getPayerName());
		return accountData;
	}

	@Override
	protected ClientDefinedFact[] createClientDefinedAttributeList() throws GateException, GateLogicException
	{
		return new ClientDefinedFactBuilder()
				.append(super.createClientDefinedAttributeList())
				.append(FROM_CARD_NUMBER_FIELD_NAME, template.getChargeOffCard(), DataType.STRING)
				.append(TO_CARD_NUMBER_FIELD_NAME, template.getReceiverCard(), DataType.STRING)
				.build();
	}
}
