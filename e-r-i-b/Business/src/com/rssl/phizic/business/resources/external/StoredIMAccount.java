package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.ima.IMAccountState;
import com.rssl.phizic.business.util.MoneyUtil;

import java.util.Calendar;

/**
 * User: Balovtsev
 * Date: 23.10.2012
 * Time: 11:32:34
 */
public class StoredIMAccount extends AbstractStoredResource<IMAccount, Void> implements IMAccount
{
	private Long            storedId;
	private String          name;
	private Money           balance;
	private Money           maxSumWrite;
	private String          agreementNumber;
	private Calendar        closingDate;
	private Calendar        openDate;
	private IMAccountState  state;

	public Long getStoredId()
	{
		return storedId;
	}

	public void setStoredId(Long storedId)
	{
		this.storedId = storedId;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setBalance(Money balance)
	{
		this.balance = balance;
	}

	public Money getBalance()
	{
		return balance;
	}

	public void setMaxSumWrite(Money maxSumWrite)
	{
		this.maxSumWrite = maxSumWrite;
	}

	public Money getMaxSumWrite()
	{
		return maxSumWrite;
	}

	public void setAgreementNumber(String agreementNumber)
	{
		this.agreementNumber = agreementNumber;
	}

	public String getAgreementNumber()
	{
		return agreementNumber;
	}

	public void setOpenDate(Calendar openDate)
	{
		this.openDate = openDate;
	}

	public Calendar getOpenDate()
	{
		return openDate;
	}

	public void setClosingDate(Calendar closingDate)
	{
		this.closingDate = closingDate;
	}

	public Calendar getClosingDate()
	{
		return closingDate;
	}

	public void setState(IMAccountState state)
	{
		this.state = state;
	}

	public IMAccountState getState()
	{
		return state;
	}

	public String getId()
	{
		return getResourceLink().getExternalId();
	}

	public String getNumber()
	{
		return getResourceLink().getNumber();
	}

	public Currency getCurrency()
	{
		return getResourceLink().getCurrency();
	}

	public void update(IMAccount imaccount)
	{
		this.name             = imaccount.getName();
		this.state            = imaccount.getState();
		this.balance          = imaccount.getBalance();
		this.openDate         = imaccount.getOpenDate();
		this.maxSumWrite      = imaccount.getMaxSumWrite();
		this.closingDate      = imaccount.getClosingDate();
		this.agreementNumber  = imaccount.getAgreementNumber();

		updateOffice(imaccount.getOffice());
		setEntityUpdateTime(Calendar.getInstance());
	}

	public boolean needUpdate(IMAccount imAccount)
	{
		// Сравниваем не все свойства, т.к. на входе продукт обновляется безусловно
		if (state != imAccount.getState())
			return true;

		if (!MoneyUtil.equalsNullIgnore(balance, imAccount.getBalance()))
			return true;

		if (!MoneyUtil.equalsNullIgnore(maxSumWrite, imAccount.getMaxSumWrite()))
			return true;

		return false;
	}
}
