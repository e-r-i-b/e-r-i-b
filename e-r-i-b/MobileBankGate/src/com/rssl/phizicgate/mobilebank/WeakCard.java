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
 * �������� ����� ��� �������,
 * ����� �� ������ ����� �� ������ ������� ����������.
 * ����� �������, WeakCard ����� ������ ����� �����
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
		throw new UnsupportedOperationException("��������: WeakCard �� ����� Id");
	}

	public String getDescription()
	{
		return "WeakCard";
	}

	public StatusDescExternalCode getStatusDescExternalCode()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� StatusDescExternalCode");
	}

	public String getType()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� Type");
	}

	public String getNumber()
	{
		return cardNumber;
	}

	public Calendar getIssueDate()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� IssueDate");
	}

	public Calendar getExpireDate()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� ExpireDate");
	}

	public Calendar getNextReportDate()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� NextReportDate");
	}

	public String getDisplayedExpireDate()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� DisplayedExpireDate");
	}

	public boolean isMain()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� isMain");
	}

	public String getAgreementNumber()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� AgreementNumber");
	}

	public Currency getCurrency()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� Currency");
	}

	public String getStatusDescription()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� StatusDescription");
	}
	
	public Calendar getAgreementDate()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� AgreementDate");
	}

	public Money getAvailableLimit()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� AvailableLimit");
	}

	public CardState getCardState()
	{
		return cardState;
	}

	public AccountState getCardAccountState()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� CardAccountState");
	}

	public CardType getCardType()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� CardType");
	}

	public String getCodeWord()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� CodeWord");
	}

	public AdditionalCardType getAdditionalCardType()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� AdditionalCardType");
	}

	public Office getOffice()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� Office");
	}

	public Code getOriginalTbCode()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� Code");
	}

	public String getMainCardNumber()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� MainCardNumber");
	}

	public boolean isVirtual()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� isVirtual");
	}

	public String getPrimaryAccountNumber()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� primaryAccountNumber");
	}

	public String getPrimaryAccountExternalId()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� primaryAccountExternalId");
	}

	public Long getKind()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� kind");
	}

	public Long getSubkind()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� subkind");
	}

	public String getUNICardType()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� UNICardType");
	}

	public String getUNIAccountType()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� UNIAccountType");
	}

	public CardLevel getCardLevel()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� cardLevel");
	}

	public CardBonusSign getCardBonusSign()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� cardBonusSign");
	}

	public String getClientId()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� clientId");
	}

	public boolean isUseReportDelivery()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� useReportDelivery");
	}

	public String getEmailAddress()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� emailAddress");
	}

	public ReportDeliveryType getReportDeliveryType()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� reportDeliveryType");
	}

	public ReportDeliveryLanguage getReportDeliveryLanguage()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� reportDeliveryLanguage");
	}

	public Money getPurchaseLimit()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� purchaseLimit");
	}

	public Money getAvailableCashLimit()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� availableCashLimit");
	}

	public String getHolderName()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� holderName");
	}

	public String getContractNumber()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� ContractNumber");
	}

	public Money getOverdraftLimit()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� Limit");
	}

	public Money getOverdraftTotalDebtSum()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� TotalDebtSum");
	}

	public Money getOverdraftMinimalPayment()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� MinimalPayment");
	}

	public Calendar getOverdraftMinimalPaymentDate()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� MinimalPaymentDate");
	}

	public Money getOverdraftOwnSum()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� OwnSum");
	}

	public Client getCardClient()
	{
		throw new UnsupportedOperationException("��������: WeakCard �� ����� CardClient");
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
