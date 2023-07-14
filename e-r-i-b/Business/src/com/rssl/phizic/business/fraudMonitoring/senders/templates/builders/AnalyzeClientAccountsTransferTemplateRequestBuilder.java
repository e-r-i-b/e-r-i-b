package com.rssl.phizic.business.fraudMonitoring.senders.templates.builders;

import com.rssl.phizic.business.documents.templates.impl.InternalTransferTemplate;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AccountData;
import com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact;
import com.rssl.phizic.rsa.integration.ws.control.generated.DataType;
import com.rssl.phizic.rsa.senders.ClientDefinedFactBuilder;
import com.rssl.phizic.utils.StringHelper;

import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.*;

/**
 * Билдер запросов для шаблона перевода между моими счетами (тип analyze)
 *
 *  @author khudyakov
 * @ created 19.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeClientAccountsTransferTemplateRequestBuilder<TD extends InternalTransferTemplate> extends AnalyzeInternalTransferTemplateRequestBuilderBase<TD>
{
	private TD template;

	@Override
	public AnalyzeClientAccountsTransferTemplateRequestBuilder append(TD template)
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
		AccountData accountData = new AccountData();
		accountData.setAccountNumber(template.getChargeOffAccount());
		accountData.setInternationalAccountNumber(template.getChargeOffAccount());
		accountData.setAccountName(template.getPayerName());
		return accountData;
	}

	@Override
	protected AccountData createOtherAccountData()
	{
		AccountData accountData = new AccountData();
		accountData.setAccountNumber(template.getReceiverAccount());
		accountData.setInternationalAccountNumber(template.getReceiverAccount());
		accountData.setAccountName(template.getPayerName());
		return accountData;
	}

	@Override
	protected ClientDefinedFact[] createClientDefinedAttributeList() throws GateException, GateLogicException
	{
		ClientDefinedFactBuilder builder = new ClientDefinedFactBuilder()
				.append(super.createClientDefinedAttributeList())
				.append(FROM_ACCOUNT_NUMBER_FIELD_NAME, template.getChargeOffAccount(), DataType.STRING)
				.append(TO_ACCOUNT_NUMBER_FIELD_NAME, template.getReceiverAccount(), DataType.STRING);

		if (StringHelper.isNotEmpty(template.getReceiverAccount()))
		{
			builder.append(RECEIVER_BAL_ACCOUNT_FIELD_NAME, template.getReceiverAccount().substring(0, 5), DataType.STRING);
		}

		return builder.build();
	}
}
