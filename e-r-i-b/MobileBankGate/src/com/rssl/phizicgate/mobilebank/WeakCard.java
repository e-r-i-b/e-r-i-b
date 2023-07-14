package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.MockObject;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.mobilebank.MobileBankCardStatus;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;

/**
 * Заглушка карты для случаев,
 * когда по номеру карты не удаётся поднять информацию.
 * Таким образом, WeakCard знает только номер карты
 * @author Erkin
 * @ created 28.04.2010
 * @ $Author$
 * @ $Revision$
 */
class WeakCard implements Card, MockObject
{
	private final String cardNumber;

	private final CardState cardState;

	WeakCard(String cardNumber, MobileBankCardStatus mbCardStatus)
	{
		if (StringHelper.isEmpty(cardNumber))
			throw new IllegalArgumentException("Argument 'cardNumber' cannot be null or empty");
		this.cardNumber = cardNumber;

		if (mbCardStatus == null)
			this.cardState = CardState.unknown;
		else switch (mbCardStatus) {
			case ACTIVE:
				this.cardState = CardState.active;
				break;
			case INACTIVE:
				this.cardState = CardState.blocked;
				break;
			default:
				this.cardState = CardState.unknown;
				break;
		}
	}

	public String getId()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает Id");
	}

	public String getDescription()
	{
		return "WeakCard";
	}

	public StatusDescExternalCode getStatusDescExternalCode()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает StatusDescExternalCode");
	}

	public String getType()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает Type");
	}

	public String getNumber()
	{
		return cardNumber;
	}

	public Calendar getIssueDate()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает IssueDate");
	}

	public Calendar getExpireDate()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает ExpireDate");
	}

	public Calendar getNextReportDate()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает NextReportDate");
	}

	public String getDisplayedExpireDate()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает DisplayedExpireDate");
	}

	public boolean isMain()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает isMain");
	}

	public String getAgreementNumber()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает AgreementNumber");
	}

	public Currency getCurrency()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает Currency");
	}

	public String getStatusDescription()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает StatusDescription");
	}
	
	public Calendar getAgreementDate()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает AgreementDate");
	}

	public Money getAvailableLimit()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает AvailableLimit");
	}

	public CardState getCardState()
	{
		return cardState;
	}

	public AccountState getCardAccountState()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает CardAccountState");
	}

	public CardType getCardType()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает CardType");
	}

	public String getCodeWord()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает CodeWord");
	}

	public AdditionalCardType getAdditionalCardType()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает AdditionalCardType");
	}

	public Office getOffice()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает Office");
	}

	public Code getOriginalTbCode()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает Code");
	}

	public String getMainCardNumber()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает MainCardNumber");
	}

	public boolean isVirtual()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает isVirtual");
	}

	public String getPrimaryAccountNumber()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает primaryAccountNumber");
	}

	public String getPrimaryAccountExternalId()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает primaryAccountExternalId");
	}

	public Long getKind()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает kind");
	}

	public Long getSubkind()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает subkind");
	}

	public String getUNICardType()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает UNICardType");
	}

	public String getUNIAccountType()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает UNIAccountType");
	}

	public CardLevel getCardLevel()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает cardLevel");
	}

	public CardBonusSign getCardBonusSign()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает cardBonusSign");
	}

	public String getClientId()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает clientId");
	}

	public boolean isUseReportDelivery()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает useReportDelivery");
	}

	public String getEmailAddress()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает emailAddress");
	}

	public ReportDeliveryType getReportDeliveryType()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает reportDeliveryType");
	}

	public ReportDeliveryLanguage getReportDeliveryLanguage()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает reportDeliveryLanguage");
	}

	public Money getPurchaseLimit()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает purchaseLimit");
	}

	public Money getAvailableCashLimit()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает availableCashLimit");
	}

	public String getHolderName()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает holderName");
	}

	public String getContractNumber()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает ContractNumber");
	}

	public Money getOverdraftLimit()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает Limit");
	}

	public Money getOverdraftTotalDebtSum()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает TotalDebtSum");
	}

	public Money getOverdraftMinimalPayment()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает MinimalPayment");
	}

	public Calendar getOverdraftMinimalPaymentDate()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает MinimalPaymentDate");
	}

	public Money getOverdraftOwnSum()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает OwnSum");
	}

	public Client getCardClient()
	{
		throw new UnsupportedOperationException("Извините: WeakCard не знает CardClient");
	}

	public String toString()
	{
		return "WeakCard{" +
				"cardNumber='" + cardNumber + '\'' +
				'}';
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		WeakCard weakCard = (WeakCard) o;

		if (!cardNumber.equals(weakCard.cardNumber))
			return false;

		return true;
	}

	public int hashCode()
	{
		return cardNumber.hashCode();
	}
}
