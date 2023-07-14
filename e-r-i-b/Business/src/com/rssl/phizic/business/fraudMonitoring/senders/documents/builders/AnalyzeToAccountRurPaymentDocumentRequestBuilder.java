package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AccountData;
import com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact;
import com.rssl.phizic.rsa.integration.ws.control.generated.DataType;
import com.rssl.phizic.rsa.senders.ClientDefinedFactBuilder;
import com.rssl.phizic.rsa.senders.types.BeneficiaryType;

import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.*;

/**
 * Билдер во ФМ для перевода с карты/вклада на на счет в "нашем" банке или на счет в другой банк через платежную систему России
 *
 * @author khudyakov
 * @ created 18.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeToAccountRurPaymentDocumentRequestBuilder extends AnalyzeRurPaymentDocumentRequestBuilderBase
{
	private RurPayment document;

	@Override
	public AnalyzeDocumentRequestBuilderBase append(RurPayment document)
	{
		this.document = document;
		return this;
	}

	@Override
	protected RurPayment getBusinessDocument()
	{
		return document;
	}

	protected AccountData createOtherAccountData()
	{
		AccountData accountData = new AccountData();
		accountData.setAccountNumber(document.getReceiverAccount());
		accountData.setInternationalAccountNumber(document.getReceiverAccount());
		accountData.setAccountName(document.getReceiverName());
		accountData.setRoutingCode(document.getReceiverBIC());
		return accountData;
	}

	protected BeneficiaryType getBeneficiaryType() throws GateException
	{
		return document.isOurBank() ? BeneficiaryType.SAME_BANK : BeneficiaryType.OTHER_BANK;
	}

	protected ClientDefinedFact[] createClientDefinedAttributeList() throws GateException, GateLogicException
	{
		ResourceType fromResourceType = document.getChargeOffResourceType();
		ClientDefinedFactBuilder builder = new ClientDefinedFactBuilder()
				.append(super.createClientDefinedAttributeList())
				.append(TO_ACCOUNT_NUMBER_FIELD_NAME, document.getReceiverAccount(), DataType.STRING)
				.append(fromResourceType == ResourceType.CARD ? FROM_CARD_NUMBER_FIELD_NAME : FROM_ACCOUNT_NUMBER_FIELD_NAME, document.getChargeOffAccount(), DataType.STRING)
				.append(RECEIVER_BAL_ACCOUNT_FIELD_NAME, document.getReceiverAccount().substring(0, 5), DataType.STRING);

		ExtendedAttribute address = document.getAttribute("receiver-address");
		if (address != null)
		{
			builder.append(RECEIVER_ADDRESS_FIELD_NAME, address.getStringValue(), DataType.STRING);
		}
		return builder.build();
	}
}
