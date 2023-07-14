package com.rssl.phizicgate.esberibgate.ima;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.ima.IMAccountState;

import java.util.Calendar;

/**
 * @author Balovtsev
 * @created 15.09.2010
 * @ $Author$
 * @ $Revision$
 */

public class IMAccountImpl implements IMAccount
{
	private String id;
	private String number;
	private String name;
	private Currency currency;
	private Money balance;
	private Money maxSumWrite;
	private String agreementNumber;
	private Calendar openDate;
	private Calendar closingDate;
	private IMAccountState state = IMAccountState.opened;
	private Office office;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
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

	public void setCurrency(Currency currency)
	{
		this.currency = currency;
	}

	public Money getBalance()
	{
		return balance;
	}

	public void setBalance(Money balance)
	{
		this.balance = balance;
	}

	public String getAgreementNumber()
	{
		return agreementNumber;
	}

	public void setAgreementNumber(String agreementNumber)
	{
		this.agreementNumber = agreementNumber;
	}

	public Calendar getOpenDate()
	{
		return openDate;
	}

	public void setOpenDate(Calendar openDate)
	{
		this.openDate = openDate;
	}

	public IMAccountState getState()
	{
		return state;
	}

	public void setState(IMAccountState state)
	{
		if(state != null)
			this.state = state;
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
		return maxSumWrite;
	}

	public void setMaxSumWrite(Money maxSumWrite)
	{
		this.maxSumWrite = maxSumWrite;
	}

	public Calendar getClosingDate()
	{
		return closingDate;
	}

	public void setClosingDate(Calendar closingDate)
	{
		this.closingDate = closingDate;
	}
}
