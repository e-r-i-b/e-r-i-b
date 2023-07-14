package com.rssl.phizicgate.iqwave.loyalty.mock;

import com.rssl.phizic.gate.loyalty.LoyaltyProgram;

import java.math.BigDecimal;

/**
 * @author gladishev
 * @ created 02.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoyaltyProgramImpl implements LoyaltyProgram
{
	private String externalId;
	private BigDecimal balance;
	private String phone;
	private String email;

	public String getExternalId()
	{
		return externalId;
	}

	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	public BigDecimal getBalance()
	{
		return balance;
	}

	public void setBalance(BigDecimal balance)
	{
		this.balance = balance;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}
}
