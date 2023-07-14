package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.phizic.business.documents.InternalTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AccountData;
import com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact;
import com.rssl.phizic.rsa.integration.ws.control.generated.DataType;
import com.rssl.phizic.rsa.senders.ClientDefinedFactBuilder;
import com.rssl.phizic.utils.StringHelper;

import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.*;

/**
 * Билдер запроса перевода между моими картами (тип analyze)
 *
 * @author khudyakov
 * @ created 19.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeCardsTransferDocumentRequestBuilder extends AnalyzeInternalTransferDocumentRequestBuilderBase
{
	private InternalTransfer document;

	@Override
	public AnalyzeCardsTransferDocumentRequestBuilder append(InternalTransfer document)
	{
		this.document = document;
		return this;
	}

	@Override
	protected InternalTransfer getBusinessDocument()
	{
		return document;
	}

	@Override
	protected AccountData createMyAccountData()
	{
		AccountData accountData = new AccountData();
		accountData.setAccountNumber(document.getChargeOffCard());
		accountData.setInternationalAccountNumber(document.getChargeOffCard());
		accountData.setAccountName(document.getPayerName());
		return accountData;
	}

	@Override
	protected AccountData createOtherAccountData()
	{
		AccountData accountData = new AccountData();
		accountData.setAccountNumber(document.getReceiverCard());
		accountData.setInternationalAccountNumber(document.getReceiverCard());
		accountData.setAccountName(document.getPayerName());
		return accountData;
	}

	@Override
	protected ClientDefinedFact[] createClientDefinedAttributeList() throws GateException, GateLogicException
	{
		return new ClientDefinedFactBuilder()
				.append(super.createClientDefinedAttributeList())
				.append(GROUND_FIELD_NAME, StringHelper.getEmptyIfNull(document.getGround()), DataType.STRING)
				.append(FROM_CARD_NUMBER_FIELD_NAME, document.getChargeOffCard(), DataType.STRING)
				.append(TO_CARD_NUMBER_FIELD_NAME, document.getReceiverCard(), DataType.STRING)
				.build();
	}
}
