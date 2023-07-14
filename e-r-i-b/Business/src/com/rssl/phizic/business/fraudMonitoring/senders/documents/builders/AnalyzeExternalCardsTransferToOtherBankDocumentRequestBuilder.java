package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AccountData;
import com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact;
import com.rssl.phizic.rsa.integration.ws.control.generated.DataType;
import com.rssl.phizic.rsa.senders.ClientDefinedFactBuilder;
import com.rssl.phizic.rsa.senders.types.BeneficiaryType;

import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.FROM_CARD_NUMBER_FIELD_NAME;
import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.TO_CARD_NUMBER_FIELD_NAME;

/**
 * Билдер запроса перевода с карты на внешнюю карту.
 *
 * @author khudyakov
 * @ created 18.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeExternalCardsTransferToOtherBankDocumentRequestBuilder extends AnalyzeRurPaymentDocumentRequestBuilderBase
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
		return accountData;
	}

	protected BeneficiaryType getBeneficiaryType()
	{
		return BeneficiaryType.OTHER_BANK;
	}

	protected ClientDefinedFact[] createClientDefinedAttributeList() throws GateException, GateLogicException
	{
		return new ClientDefinedFactBuilder()
				.append(super.createClientDefinedAttributeList())
				.append(TO_CARD_NUMBER_FIELD_NAME, document.getReceiverAccount(), DataType.STRING)
				.append(FROM_CARD_NUMBER_FIELD_NAME, document.getChargeOffAccount(), DataType.STRING)
				.build();
	}
}
