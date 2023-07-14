package com.rssl.phizic.business.fraudMonitoring.senders.templates.builders;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.impl.ExternalTransferTemplate;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.rsa.integration.ws.control.generated.AccountData;
import com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact;
import com.rssl.phizic.rsa.integration.ws.control.generated.DataType;
import com.rssl.phizic.rsa.senders.ClientDefinedFactBuilder;
import com.rssl.phizic.rsa.senders.types.AccountPayeeType;
import com.rssl.phizic.rsa.senders.types.BeneficiaryType;
import com.rssl.phizic.rsa.senders.types.ClientDefinedEventType;
import com.rssl.phizic.rsa.senders.types.DirectionTransferFundsType;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.*;

/**
 * Билдер запросов создания шаблона бил. получателю платежа (тип analyze)
 *
 * @author khudyakov
 * @ created 19.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeCardPaymentSystemTransferTemplateRequestBuilder<TD extends ExternalTransferTemplate> extends AnalyzeTemplateFraudMonitoringRequestBuilderBase<TD>
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
	protected AccountPayeeType getAccountPayeeType()
	{
		return AccountPayeeType.BILLER;
	}

	@Override
	protected BeneficiaryType getBeneficiaryType() throws GateException
	{
		return getBeneficiaryType(getTemplateDocument().getReceiverBank());
	}

	@Override
	protected DirectionTransferFundsType getDirectionTransferFundsType()
	{
		return DirectionTransferFundsType.ME_TO_YOU;
	}

	@Override
	protected ClientDefinedEventType getClientDefinedEventType()
	{
		return ClientDefinedEventType.RUR_PAY_JUR_SB;
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
		accountData.setInternationalAccountNumber(template.getChargeOffCard());
		accountData.setAccountName(template.getPayerName());
		return accountData;
	}

	@Override
	protected AccountData createOtherAccountData() throws GateException
	{
		return createOtherAccountData(getTemplateDocument());
	}

	protected AccountData createOtherAccountData(TemplateDocument template) throws GateException
	{
		AccountData accountData = new AccountData();
		accountData.setAccountNumber(getOtherAccountNumber(template));
		accountData.setInternationalAccountNumber(accountData.getAccountNumber());
		accountData.setAccountName(template.getReceiverName());
		return accountData;
	}

	@Override
	protected ClientDefinedFact[] createClientDefinedAttributeList() throws GateException, GateLogicException
	{
		ClientDefinedFactBuilder builder = new ClientDefinedFactBuilder()
				.append(super.createClientDefinedAttributeList())
				.append(FROM_CARD_NUMBER_FIELD_NAME, template.getChargeOffCard(), DataType.STRING)
				.append(TO_ACCOUNT_NUMBER_FIELD_NAME, template.getReceiverAccount(), DataType.STRING);

		ResidentBank residentBank = template.getReceiverBank();
		if (residentBank != null)
		{
			builder.append(RECEIVER_BANK_BIC_ATTRIBUTE_NAME, StringHelper.getEmptyIfNull(residentBank.getBIC()), DataType.STRING);
			builder.append(RECEIVER_BANK_CORR_ACCOUNT_ATTRIBUTE_NAME, StringHelper.getEmptyIfNull(residentBank.getAccount()), DataType.STRING);
			builder.append(RECEIVER_BANK_NAME_ATTRIBUTE_NAME, StringHelper.getEmptyIfNull(residentBank.getName()), DataType.STRING);
			builder.append(RECEIVER_INN_ATTRIBUTE_NAME, StringHelper.getEmptyIfNull(template.getReceiverINN()), DataType.STRING);
			builder.append(getBICReceiverAccountClientDefinedFact(residentBank.getBIC(), template.getReceiverAccount()));
			builder.append(getClientBICReceiverAccountClientDefinedFact(getDocumentOwner().getId(), residentBank.getBIC(), template.getReceiverAccount()));
		}

		if (StringHelper.isNotEmpty(template.getReceiverAccount()))
		{
			builder.append(RECEIVER_BAL_ACCOUNT_FIELD_NAME, template.getReceiverAccount().substring(0, 5), DataType.STRING);
		}

		try
		{
			List<Field> fields = template.getExtendedFields();
			if (CollectionUtils.isNotEmpty(fields))
			{
				for (Field field : fields)
				{
					builder.append(field.getName(), getExtendedFieldValue(field), DataType.STRING);
				}
			}
		}
		catch (DocumentException e)
		{
			log.error(e);
		}

		return builder.build();
	}
}
