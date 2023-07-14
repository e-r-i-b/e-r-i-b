package com.rssl.phizic.business.fraudMonitoring.senders.templates.builders;

import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.templates.impl.IndividualTransferTemplate;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.documents.attribute.ExtendedAttribute;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AccountData;
import com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact;
import com.rssl.phizic.rsa.integration.ws.control.generated.DataType;
import com.rssl.phizic.rsa.senders.ClientDefinedFactBuilder;
import com.rssl.phizic.rsa.senders.types.BeneficiaryType;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.*;

/**
 * Билдер запросов во ФМ для шаблона перевода с карты/вклада на на счет в "нашем" банке или на счет в другой банк через платежную систему России
 *
 * @author khudyakov
 * @ created 19.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeToAccountRurPaymentTemplateRequestBuilder extends AnalyzeRurPaymentTemplateRequestBuilderBase
{
	private IndividualTransferTemplate template;

	@Override
	public AnalyzeTemplateFraudMonitoringRequestBuilderBase append(IndividualTransferTemplate template)
	{
		this.template = template;
		return this;
	}

	@Override
	protected IndividualTransferTemplate getTemplateDocument()
	{
		return template;
	}

	@Override
	protected AccountData createOtherAccountData()
	{
		AccountData accountData = new AccountData();
		accountData.setAccountNumber(template.getReceiverAccount());
		accountData.setInternationalAccountNumber(template.getReceiverAccount());
		accountData.setAccountName(template.getReceiverName());
		return accountData;
	}

	@Override
	protected BeneficiaryType getBeneficiaryType() throws GateException
	{
		return template.isOurBank() ? BeneficiaryType.SAME_BANK : BeneficiaryType.OTHER_BANK;
	}

	@Override
	protected ClientDefinedFact[] createClientDefinedAttributeList() throws GateException, GateLogicException
	{
		ClientDefinedFactBuilder builder = new ClientDefinedFactBuilder()
				.append(super.createClientDefinedAttributeList())
				.append(TO_ACCOUNT_NUMBER_FIELD_NAME, template.getReceiverAccount(), DataType.STRING)
				.append(RECEIVER_BAL_ACCOUNT_FIELD_NAME, StringHelper.isNotEmpty(template.getReceiverAccount()) ? template.getReceiverAccount().substring(0, 5) : StringUtils.EMPTY, DataType.STRING);

		ExtendedAttribute address = template.getAttribute("receiver-address");
		if (address != null)
		{
			builder.append(RECEIVER_ADDRESS_FIELD_NAME, StringHelper.getEmptyIfNull(address.getStringValue()), DataType.STRING);
		}

		ResidentBank residentBank = template.getReceiverBank();
		if (residentBank != null)
		{
			builder.append(RECEIVER_BANK_BIC_ATTRIBUTE_NAME, StringHelper.getEmptyIfNull(residentBank.getBIC()), DataType.STRING);
			builder.append(RECEIVER_BANK_NAME_ATTRIBUTE_NAME, StringHelper.getEmptyIfNull(residentBank.getName()), DataType.STRING);
			builder.append(RECEIVER_BANK_CORR_ACCOUNT_ATTRIBUTE_NAME, StringHelper.getEmptyIfNull(residentBank.getAccount()), DataType.STRING);
			builder.append(getBICReceiverAccountClientDefinedFact(residentBank.getBIC(), template.getReceiverAccount()));
			builder.append(getClientBICReceiverAccountClientDefinedFact(getDocumentOwner().getId(), residentBank.getBIC(), template.getReceiverAccount()));
		}

		if (StringHelper.isNotEmpty(template.getReceiverAccount()))
		{
			builder.append(RECEIVER_BAL_ACCOUNT_FIELD_NAME, template.getReceiverAccount().substring(0, 5), DataType.STRING);
		}

		ResourceType fromResourceType = template.getChargeOffResourceType();
		builder.append(fromResourceType == ResourceType.CARD ? FROM_CARD_NUMBER_FIELD_NAME : FROM_ACCOUNT_NUMBER_FIELD_NAME, template.getChargeOffAccount(), DataType.STRING);
		return builder.build();
	}
}
