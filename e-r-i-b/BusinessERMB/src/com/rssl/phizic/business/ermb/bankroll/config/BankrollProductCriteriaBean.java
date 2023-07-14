package com.rssl.phizic.business.ermb.bankroll.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Rtischeva
 * @ created 29.11.13
 * @ $Author$
 * @ $Revision$
 */
@XmlType(name = "bankroll-products")
@XmlAccessorType(XmlAccessType.NONE)
class BankrollProductCriteriaBean
{
	@XmlElement(name = "credit-card", required = true)
	private BankrollProductAvailabilityType creditCardCriteria;

	@XmlElement(name = "deposit", required = true)
	private BankrollProductAvailabilityType depositCriteria;

	@XmlElement(name = "loan", required = true)
	private BankrollProductAvailabilityType loanCriteria;

	BankrollProductAvailabilityType getCreditCardCriteria()
	{
		return creditCardCriteria;
	}

	void setCreditCardCriteria(BankrollProductAvailabilityType creditCardCriteria)
	{
		this.creditCardCriteria = creditCardCriteria;
	}

	BankrollProductAvailabilityType getDepositCriteria()
	{
		return depositCriteria;
	}

	void setDepositCriteria(BankrollProductAvailabilityType depositCriteria)
	{
		this.depositCriteria = depositCriteria;
	}

	BankrollProductAvailabilityType getLoanCriteria()
	{
		return loanCriteria;
	}

	void setLoanCriteria(BankrollProductAvailabilityType loanCriteria)
	{
		this.loanCriteria = loanCriteria;
	}
}
