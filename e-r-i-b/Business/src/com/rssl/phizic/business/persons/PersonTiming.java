package com.rssl.phizic.business.persons;

import java.util.Calendar;

/**
 * @author Erkin
 * @ created 22.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * “айминги по данным персоны
 */
public class PersonTiming
{
	private Long personId;

	/**
	 * ¬рем€ следующей загрузки списка счетов клиента или null
	 */
	private Calendar accountListLastUpdate;

	/**
	 * ¬рем€ следующей загрузки списка карт клиента или null
	 */
	private Calendar cardListLastUpdate;

	/**
	 * ¬рем€ следующей загрузки списка кредитов клиента или null
	 */
	private Calendar loanListLastUpdate;

	///////////////////////////////////////////////////////////////////////////

	public Long getPersonId()
	{
		return personId;
	}

	public void setPersonId(Long personId)
	{
		this.personId = personId;
	}

	public Calendar getAccountListLastUpdate()
	{
		return accountListLastUpdate;
	}

	public void setAccountListLastUpdate(Calendar accountListLastUpdate)
	{
		this.accountListLastUpdate = accountListLastUpdate;
	}

	public Calendar getCardListLastUpdate()
	{
		return cardListLastUpdate;
	}

	public void setCardListLastUpdate(Calendar cardListLastUpdate)
	{
		this.cardListLastUpdate = cardListLastUpdate;
	}

	public Calendar getLoanListLastUpdate()
	{
		return loanListLastUpdate;
	}

	public void setLoanListLastUpdate(Calendar loanListLastUpdate)
	{
		this.loanListLastUpdate = loanListLastUpdate;
	}
}
