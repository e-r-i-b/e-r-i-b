package com.rssl.phizic.business.bankroll;

import javax.xml.bind.annotation.*;

/**
 * @author Erkin
 * @ created 26.03.2013
 * @ $Author$
 * @ $Revision$
 */

@XmlType(name = "BankrollRegistry")
@XmlRootElement(name="bankroll-registry")
@XmlAccessorType(XmlAccessType.NONE)
class BankrollRegistryBean
{
    @XmlElement(name = "account-config", required = true)
    private BankrollConfigBean accountConfigBean;

	@XmlElement(name = "card-config", required = true)
	private BankrollConfigBean cardConfigBean;

	@XmlElement(name = "loan-config", required = true)
	private BankrollConfigBean loanConfigBean;

	///////////////////////////////////////////////////////////////////////////

	BankrollConfigBean getAccountConfigBean()
	{
		return accountConfigBean;
	}

	void setAccountConfigBean(BankrollConfigBean accountConfigBean)
	{
		this.accountConfigBean = accountConfigBean;
	}

	BankrollConfigBean getCardConfigBean()
	{
		return cardConfigBean;
	}

	void setCardConfigBean(BankrollConfigBean cardConfigBean)
	{
		this.cardConfigBean = cardConfigBean;
	}

	BankrollConfigBean getLoanConfigBean()
	{
		return loanConfigBean;
	}

	void setLoanConfigBean(BankrollConfigBean loanConfigBean)
	{
		this.loanConfigBean = loanConfigBean;
	}
}
