package com.rssl.phizic.business.einvoicing;

import com.rssl.phizic.gate.einvoicing.ShopProfile;

import java.util.Calendar;

/**
 * @author gladishev
 * @ created 14.02.14
 * @ $Author$
 * @ $Revision$
 */
public class ShopProfileImpl implements ShopProfile
{
	private String firstName;
	private String surName;
	private String patrName;
	private String passport;
	private Calendar birthdate;
	private String tb;

	public Long getId()
	{
		throw new UnsupportedOperationException();
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getSurName()
	{
		return surName;
	}

	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	public String getPatrName()
	{
		return patrName;
	}

	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	public String getPassport()
	{
		return passport;
	}

	public void setPassport(String passport)
	{
		this.passport = passport;
	}

	public Calendar getBirthdate()
	{
		return birthdate;
	}

	public void setBirthdate(Calendar birthdate)
	{
		this.birthdate = birthdate;
	}

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
	}
}
