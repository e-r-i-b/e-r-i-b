package com.rssl.phizic.business.fraudMonitoring.senders.templates.builders;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.impl.InternalTransferTemplate;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AccountData;
import com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact;
import com.rssl.phizic.rsa.integration.ws.control.generated.DataType;
import com.rssl.phizic.rsa.senders.ClientDefinedFactBuilder;

import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.FROM_ACCOUNT_NUMBER_FIELD_NAME;
import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.TO_CARD_NUMBER_FIELD_NAME;

/**
 *
 *
 * @author khudyakov
 * @ created 19.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeAccountToCardTransferTemplateRequestBuilder<TD extends InternalTransferTemplate> extends AnalyzeInternalTransferTemplateRequestBuilderBase<TD>
{
	private TD template;

	@Override
	public AnalyzeTemplateFraudMonitoringRequestBuilderBase append(TD template)
	{
		this.template = template;
		return this;
	}

	@Override
	protected TD getTemplateDocument()
	{
		return template;
	}

	@Override
	protected AccountData createMyAccountData()
	{
		return createMyAccountData(getTemplateDocument());
	}

	protected AccountData createMyAccountData(TemplateDocument template)
	{
		AccountData accountData = new AccountData();
		accountData.setAccountNumber(template.getChargeOffCard());
		accountData.setInternationalAccountNumber(template.getChargeOffAccount());
		accountData.setAccountName(template.getPayerName());
		return accountData;
	}

	@Override
	protected AccountData createOtherAccountData()
	{
		return createOtherAccountData(getTemplateDocument());
	}

	protected AccountData createOtherAccountData(TemplateDocument template)
	{
		AccountData accountData = new AccountData();
		accountData.setAccountNumber(template.getReceiverCard());
		accountData.setInternationalAccountNumber(template.getReceiverCard());
		accountData.setAccountName(template.getPayerName());
		return accountData;
	}

	protected ClientDefinedFact[] createClientDefinedAttributeList() throws GateException, GateLogicException
	{
		return new ClientDefinedFactBuilder()
				.append(super.createClientDefinedAttributeList())
				.append(FROM_ACCOUNT_NUMBER_FIELD_NAME, template.getChargeOffAccount(), DataType.STRING)
				.append(TO_CARD_NUMBER_FIELD_NAME, template.getReceiverCard(), DataType.STRING)
				.build();
	}
}
