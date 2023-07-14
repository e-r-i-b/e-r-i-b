package com.rssl.phizic.einvoicing;

import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.gate.einvoicing.ShopProfile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author bogdanov
 * @ created 10.02.14
 * @ $Author$
 * @ $Revision$
 */

public class ShopProfileImpl implements ShopProfile
{
	private Long id;
	private String firstName;
	private String surName;
	private String patrName;
	private String passport;
	private Calendar birthdate;
	private String tb;
	private final DateFormat format = new SimpleDateFormat("dd.MM.yyyy");

public ShopProfileImpl() {}

	public ShopProfileImpl(UserInfo userInfo)
	{
		this.firstName = userInfo.getFirstname();
		this.surName = userInfo.getSurname();
		this.patrName = userInfo.getPatrname();
		this.passport = userInfo.getPassport();
		this.birthdate = userInfo.getBirthdate();
		this.tb = userInfo.getTb();

	}

	public ShopProfileImpl(ShopProfile profile)
	{
		this.birthdate = profile.getBirthdate();
		this.firstName = profile.getFirstName();
		this.passport = profile.getPassport();
		this.patrName = profile.getPatrName();
		this.surName = profile.getSurName();
		this.tb = profile.getTb();
	}

	public Calendar getBirthdate()
	{
		return birthdate;
	}

	/**
	 * Испоьзуется в ftl.
	 * @return дата рождения в виде строки.
	 */
	public String getBirthdatestr()
	{
		return format.format(birthdate.getTime());
	}

	public void setBirthdate(Calendar birthdate)
	{
		this.birthdate = birthdate;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getPassport()
	{
		return passport;
	}

	public void setPassport(String passport)
	{
		this.passport = passport;
	}

	public String getPatrName()
	{
		return patrName;
	}

	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	public String getSurName()
	{
		return surName;
	}

	public void setSurName(String surName)
	{
		this.surName = surName;
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
