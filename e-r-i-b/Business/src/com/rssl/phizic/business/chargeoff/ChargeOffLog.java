package com.rssl.phizic.business.chargeoff;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.common.types.Money;
import java.util.Calendar;

/**
 * @author Egorova
 * @ created 14.01.2009
 * @ $Author$
 * @ $Revision$
 */
public class ChargeOffLog
{
	private Long id;
	private CommonLogin login;
	private Calendar date;
	private Calendar periodFrom;
	private Calendar periodUntil;
	private String account;
	private ChargeOffType type;
	private ChargeOffState state;
	private Money amount;
	private Long attempt;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public CommonLogin getLogin()
	{
		return login;
	}

	public void setLogin(CommonLogin login)
	{
		this.login = login;
	}

	public Calendar getDate()
	{
		return date;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}

	public Calendar getPeriodFrom()
	{
		return periodFrom;
	}

	public void setPeriodFrom(Calendar periodFrom)
	{
		this.periodFrom = periodFrom;
	}

	public Calendar getPeriodUntil()
	{
		return periodUntil;
	}

	public void setPeriodUntil(Calendar periodUntil)
	{
		this.periodUntil = periodUntil;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public ChargeOffType getType()
	{
		return type;
	}

	public void setType(ChargeOffType type)
	{
		this.type = type;
	}

	public ChargeOffState getState()
	{
		return state;
	}

	public void setState(ChargeOffState state)
	{
		this.state = state;
	}

	public Money getAmount()
	{
		return amount;
	}

	public void setAmount(Money amount)
	{
		this.amount = amount;
	}

	public Long getAttempt()
	{
		return attempt;
	}

	public void setAttempt(Long attempt)
	{
		this.attempt = attempt;
	}
}
