package com.rssl.phizicgate.manager.services.objects;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;

import java.util.Calendar;

/**
 * @author Krenev
 * @ created 24.02.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class CardBase implements Card
{
	Card delegate;

	public CardBase(Card delegate)
	{
		this.delegate = delegate;
	}

	public String getDescription()
	{
		return delegate.getDescription();
	}

	public String getNumber()
	{
		return delegate.getNumber();
	}

	public String getType()
	{
		return delegate.getType();
	}

	public Calendar getIssueDate()
	{
		return delegate.getIssueDate();
	}

	public Calendar getExpireDate()
	{
		return delegate.getExpireDate();
	}

	public String getDisplayedExpireDate()
	{
		return delegate.getDisplayedExpireDate();
	}

	public boolean isMain()
	{
		return delegate.isMain();
	}

	public CardType getCardType()
	{
		return delegate.getCardType();
	}

	public AdditionalCardType getAdditionalCardType()
	{
		return delegate.getAdditionalCardType();
	}
	public Currency getCurrency()
	{
		return delegate.getCurrency();
	}
	public Office getOffice()
	{
		return delegate.getOffice();
	}

	public Code getOriginalTbCode()
	{
		return delegate.getOriginalTbCode();
	}

	public String getStatusDescription()
	{
		return delegate.getStatusDescription();
	}
	public Money getAvailableLimit()
	{
		return delegate.getAvailableLimit();
	}
	public CardState getCardState()
	{
		return delegate.getCardState();
	}

	public String getMainCardNumber()
	{
		return delegate.getMainCardNumber();
	}

	public boolean isVirtual()
	{
		return delegate.isVirtual();
	}

	public String getPrimaryAccountNumber()
	{
		return delegate.getPrimaryAccountNumber();
	}

	public String getPrimaryAccountExternalId()
	{
		return delegate.getPrimaryAccountExternalId();
	}

	public Long getKind()
	{
		return delegate.getKind();
	}

	public Long getSubkind()
	{
		return delegate.getSubkind();
	}

	public StatusDescExternalCode getStatusDescExternalCode()
	{
		return delegate.getStatusDescExternalCode();
	}

	public String getUNIAccountType()
	{
		return delegate.getUNIAccountType();
	}

	public String getUNICardType()
	{
		return delegate.getUNICardType();
	}

	public CardBonusSign getCardBonusSign()
	{
		return delegate.getCardBonusSign();
	}

	public CardLevel getCardLevel()
	{
		return delegate.getCardLevel();
	}

	public String getClientId()
	{
		return delegate.getClientId();
	}

	public boolean isUseReportDelivery()
	{
		return delegate.isUseReportDelivery();
	}

	public String getEmailAddress()
	{
		return delegate.getEmailAddress();
	}

	public ReportDeliveryType getReportDeliveryType()
	{
		return delegate.getReportDeliveryType();
	}

	public ReportDeliveryLanguage getReportDeliveryLanguage()
	{
		return delegate.getReportDeliveryLanguage();
	}

	public Money getPurchaseLimit()
	{
		return delegate.getPurchaseLimit();
	}

	public Money getAvailableCashLimit()
	{
		return delegate.getAvailableCashLimit();
	}

	public String getHolderName()
	{
		return delegate.getHolderName();
	}

	public String getContractNumber()
	{
		return delegate.getContractNumber();
	}

	public AccountState getCardAccountState()
	{
		return delegate.getCardAccountState();
	}

    public Calendar getNextReportDate()
    {
        return delegate.getNextReportDate();
    }

	public Money getOverdraftLimit()
	{
		return delegate.getOverdraftLimit();
	}

	public Money getOverdraftTotalDebtSum()
	{
		return delegate.getOverdraftTotalDebtSum();
	}

	public Money getOverdraftMinimalPayment()
	{
		return delegate.getOverdraftMinimalPayment();
	}

	public Calendar getOverdraftMinimalPaymentDate()
	{
		return delegate.getOverdraftMinimalPaymentDate();
	}

	public Money getOverdraftOwnSum()
	{
		return delegate.getOverdraftOwnSum();
	}

	public Client getCardClient()
	{
		return delegate.getCardClient();
	}

	@Override
	public String toString()
	{
		return delegate.toString();
	}
}
