package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BackRefBankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AccountData;
import com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact;
import com.rssl.phizic.rsa.integration.ws.control.generated.DataType;
import com.rssl.phizic.rsa.senders.ClientDefinedFactBuilder;
import com.rssl.phizic.rsa.senders.types.BeneficiaryType;

import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.*;

/**
 * Билдер во ФМ для перевода с карты на карту внутри "нашего" банка.
 *
 * @author khudyakov
 * @ created 18.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeExternalCardsTransferToOurBankDocumentRequestBuilder extends AnalyzeRurPaymentDocumentRequestBuilderBase
{
	private RurPayment document;
	private Card receiverCard;

	@Override
	public AnalyzeDocumentRequestBuilderBase append(RurPayment _document)
	{
		try
		{
			document = _document;
			if (receiverCard == null)
			{
				receiverCard = document.receiveExternalCard(document.getReceiverAccount());
			}
		}
		catch (Exception e)
		{
			log.error(e);
		}
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
		if (receiverCard != null)
		{
			accountData.setAccountName(receiverCard.getDescription());
			accountData.setRoutingCode(receiverCard.getOffice().getBIC());
		}
		return accountData;
	}

	protected BeneficiaryType getBeneficiaryType()
	{
		return BeneficiaryType.SAME_BANK;
	}

	protected ClientDefinedFact[] createClientDefinedAttributeList() throws GateException, GateLogicException
	{
		return new ClientDefinedFactBuilder()
				.append(TO_CARD_NUMBER_FIELD_NAME, document.getReceiverAccount(), DataType.STRING)
				.append(FROM_CARD_NUMBER_FIELD_NAME, document.getChargeOffAccount(), DataType.STRING)
				.append(super.createClientDefinedAttributeList())
				.append(getDestinationCardPrimaryAccount())
				.build();
	}

	/**
	 * Добавление Бал. счета получателя карты зачисления.(доступно только для карт присутствующих в ЕРИБ в рамках блока)
	 */
	protected ClientDefinedFact[] getDestinationCardPrimaryAccount()
	{
		try
		{
			//если перевод не в "наш банк" даже не пытаемся искать счет.
			if (!document.isOurBank())
			{
				return null;
			}

			Account primaryAccount = GateSingleton.getFactory().service(BackRefBankrollService.class).getCardAccount(receiverCard.getNumber());
			if (primaryAccount != null)
			{
				return new ClientDefinedFactBuilder().append(RECEIVER_BAL_ACCOUNT_FIELD_NAME, primaryAccount.getNumber().substring(0, 5), DataType.STRING).build();
			}
			return null;
		}
		catch (Exception e)
		{
			log.error(e);
			return null;
		}
	}
}
