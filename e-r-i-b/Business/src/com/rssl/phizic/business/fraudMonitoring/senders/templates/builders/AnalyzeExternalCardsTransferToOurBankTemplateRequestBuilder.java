package com.rssl.phizic.business.fraudMonitoring.senders.templates.builders;

import com.rssl.phizic.business.documents.templates.impl.IndividualTransferTemplate;
import com.rssl.phizic.gate.bankroll.Card;
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
 * Билдер запросов во ФМ для шаблона перевода с карты на карту внутри "нашего" банка.
 *
 * @author khudyakov
 * @ created 19.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeExternalCardsTransferToOurBankTemplateRequestBuilder extends AnalyzeRurPaymentTemplateRequestBuilderBase
{
	private IndividualTransferTemplate template;
	private Card receiverCard = null;

	@Override
	public AnalyzeTemplateFraudMonitoringRequestBuilderBase append(IndividualTransferTemplate _template)
	{
		try
		{
			template = _template;
			receiverCard = template.getExternalCard();
		}
		catch (Exception e)
		{
			log.error(e);
		}
		return this;
	}

	@Override
	protected IndividualTransferTemplate getTemplateDocument()
	{
		return template;
	}

	protected AccountData createOtherAccountData()
	{
		AccountData accountData = new AccountData();
		accountData.setAccountNumber(template.getReceiverAccount());
		accountData.setInternationalAccountNumber(template.getReceiverAccount());

		if (receiverCard != null)
		{
			accountData.setAccountName(receiverCard.getDescription());
			accountData.setRoutingCode(receiverCard.getOffice().getBIC());
		}
		return accountData;
	}

	@Override
	protected BeneficiaryType getBeneficiaryType() throws GateException
	{
		return BeneficiaryType.SAME_BANK;
	}

	protected ClientDefinedFact[] createClientDefinedAttributeList() throws GateException, GateLogicException
	{
		return new ClientDefinedFactBuilder()
				.append(super.createClientDefinedAttributeList())
				.append(TO_CARD_NUMBER_FIELD_NAME, template.getReceiverAccount(), DataType.STRING)
				.append(FROM_CARD_NUMBER_FIELD_NAME, template.getChargeOffAccount(), DataType.STRING)
				.build();
	}
}
