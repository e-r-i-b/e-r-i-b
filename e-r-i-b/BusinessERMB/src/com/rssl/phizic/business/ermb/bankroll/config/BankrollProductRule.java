package com.rssl.phizic.business.ermb.bankroll.config;

import com.rssl.phizic.business.ermb.ErmbTariff;

import java.util.List;

/**
 * Правило включения видимости продуктов по умолчанию
 * @author Rtischeva
 * @ created 02.12.13
 * @ $Author$
 * @ $Revision$
 */
public class BankrollProductRule
{
	private Long id;

	private String name;

	private boolean isActive;

	private List<String> terbankCodes;
	private ClientCategoryType clientCategory;
	private Integer ageFrom;
	private Integer ageUntil;
	private BankrollProductAvailabilityType creditCardCriteria;
	private BankrollProductAvailabilityType depositCriteria;
	private BankrollProductAvailabilityType loanCriteria;

	private ErmbTariff ermbTariff;

	private boolean cardsVisibility;
	private boolean depositsVisibility;
	private boolean loansVisibility;
	private boolean newProductsVisibility;

	private boolean cardsNotification;
	private boolean depositsNotification;
	private boolean loansNotification;
	private boolean newProductsNotification;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isActive()
	{
		return isActive;
	}

	public void setActive(boolean active)
	{
		isActive = active;
	}

	public List<String> getTerbankCodes()
	{
		return terbankCodes;
	}

	public void setTerbankCodes(List<String> terbankCodes)
	{
		this.terbankCodes = terbankCodes;
	}

	public ClientCategoryType getClientCategory()
	{
		return clientCategory;
	}

	public void setClientCategory(ClientCategoryType clientCategory)
	{
		this.clientCategory = clientCategory;
	}

	public Integer getAgeFrom()
	{
		return ageFrom;
	}

	public void setAgeFrom(Integer ageFrom)
	{
		this.ageFrom = ageFrom;
	}

	public Integer getAgeUntil()
	{
		return ageUntil;
	}

	public void setAgeUntil(Integer ageUntil)
	{
		this.ageUntil = ageUntil;
	}

	public BankrollProductAvailabilityType getCreditCardCriteria()
	{
		return creditCardCriteria;
	}

	public void setCreditCardCriteria(BankrollProductAvailabilityType creditCardCriteria)
	{
		this.creditCardCriteria = creditCardCriteria;
	}

	public BankrollProductAvailabilityType getDepositCriteria()
	{
		return depositCriteria;
	}

	public void setDepositCriteria(BankrollProductAvailabilityType depositCriteria)
	{
		this.depositCriteria = depositCriteria;
	}

	public BankrollProductAvailabilityType getLoanCriteria()
	{
		return loanCriteria;
	}

	public void setLoanCriteria(BankrollProductAvailabilityType loanCriteria)
	{
		this.loanCriteria = loanCriteria;
	}

	public ErmbTariff getErmbTariff()
	{
		return ermbTariff;
	}

	public void setErmbTariff(ErmbTariff ermbTariff)
	{
		this.ermbTariff = ermbTariff;
	}

	public boolean isCardsVisibility()
	{
		return cardsVisibility;
	}

	public void setCardsVisibility(boolean cardsVisibility)
	{
		this.cardsVisibility = cardsVisibility;
	}

	public boolean isDepositsVisibility()
	{
		return depositsVisibility;
	}

	public void setDepositsVisibility(boolean depositsVisibility)
	{
		this.depositsVisibility = depositsVisibility;
	}

	public boolean isLoansVisibility()
	{
		return loansVisibility;
	}

	public void setLoansVisibility(boolean loansVisibility)
	{
		this.loansVisibility = loansVisibility;
	}

	public boolean isNewProductsVisibility()
	{
		return newProductsVisibility;
	}

	public void setNewProductsVisibility(boolean newProductsVisibility)
	{
		this.newProductsVisibility = newProductsVisibility;
	}

	public boolean isCardsNotification()
	{
		return cardsNotification;
	}

	public void setCardsNotification(boolean cardsNotification)
	{
		this.cardsNotification = cardsNotification;
	}

	public boolean isDepositsNotification()
	{
		return depositsNotification;
	}

	public void setDepositsNotification(boolean depositsNotification)
	{
		this.depositsNotification = depositsNotification;
	}

	public boolean isLoansNotification()
	{
		return loansNotification;
	}

	public void setLoansNotification(boolean loansNotification)
	{
		this.loansNotification = loansNotification;
	}

	public boolean isNewProductsNotification()
	{
		return newProductsNotification;
	}

	public void setNewProductsNotification(boolean newProductsNotification)
	{
		this.newProductsNotification = newProductsNotification;
	}
}
