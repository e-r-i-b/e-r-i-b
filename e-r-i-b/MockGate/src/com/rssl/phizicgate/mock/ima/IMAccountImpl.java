package com.rssl.phizicgate.mock.ima;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.ima.IMAccountState;

import java.util.Calendar;

/**
 * @ author Balovtsev
 * @ created 25.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class IMAccountImpl implements IMAccount
{
	private String          id;
	private String          number;
	private String          name;
	private Currency        currency;
	private Money           balance;
	private String          agreementNumber;
	private Calendar        openDate;
	private IMAccountState  state = IMAccountState.opened;
	private Office office;

	public IMAccountImpl(String id,
	                     String number,
	                     Currency currency,
	                     Money balance,
	                     String agreementNumber,
	                     Calendar openDate,
	                     IMAccountState state,
	                     Office office)
	{
		this.id = id;
		this.number = number;
		this.currency = currency;
		this.balance = balance;
		this.agreementNumber = agreementNumber;
		this.openDate = openDate;
		if(state != null)
			this.state = state;
		this.office = office;
	}

	public String getId()
	{
		return id;
	}

	public String getNumber()
	{
		return number;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Currency getCurrency()
	{
		return currency;
	}

	public Money getBalance()
	{
		return balance;
	}

	public String getAgreementNumber()
	{
		return agreementNumber;
	}

	public Calendar getOpenDate()
	{
		return openDate;
	}

	public void setState(IMAccountState state)
	{
		if(state != null)
			this.state = state;
	}

	public IMAccountState getState()
	{
		return state;
	}

	public Office getOffice()
	{
		return office;
	}

	public void setOffice(Office office)
	{
		this.office = office;
	}

	public Money getMaxSumWrite()
	{
		return null;
	}

	public Calendar getClosingDate()
	{
		return null;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (!(o instanceof IMAccountImpl))
			return false;

		IMAccountImpl imAccount = (IMAccountImpl) o;

		if (id != null ? !id.equals(imAccount.id) : imAccount.id != null)
			return false;
		if (number != null ? !number.equals(imAccount.number) : imAccount.number != null)
			return false;

		return true;
	}
}
