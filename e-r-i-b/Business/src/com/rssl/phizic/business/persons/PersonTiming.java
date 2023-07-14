package com.rssl.phizic.business.persons;

import java.util.Calendar;

/**
 * @author Erkin
 * @ created 22.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� �� ������ �������
 */
public class PersonTiming
{
	private Long personId;

	/**
	 * ����� ��������� �������� ������ ������ ������� ��� null
	 */
	private Calendar accountListLastUpdate;

	/**
	 * ����� ��������� �������� ������ ���� ������� ��� null
	 */
	private Calendar cardListLastUpdate;

	/**
	 * ����� ��������� �������� ������ �������� ������� ��� null
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
