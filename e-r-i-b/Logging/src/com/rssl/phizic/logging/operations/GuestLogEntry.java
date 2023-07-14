package com.rssl.phizic.logging.operations;

import com.rssl.phizic.logging.LogThreadContext;

import java.util.Calendar;

/**
 * @author vagin
 * @ created 17.02.15
 * @ $Author$
 * @ $Revision$
 * Объект журнала действий гостя
 */
public class GuestLogEntry extends LogEntryBase
{
	private String phoneNumber;
	private String guestLogin;
	private Long guestCode;

	public GuestLogEntry(LogDataReader reader, Calendar start, Calendar end)
	{
		fillBasePart(reader, start, end);
		setGuestLogin(LogThreadContext.getGuestLogin());
		setGuestCode(LogThreadContext.getGuestCode());
		setPhoneNumber(LogThreadContext.getGuestPhoneNumber());
	}

	public GuestLogEntry() {}

	public Long getGuestCode()
	{
		return guestCode;
	}

	public void setGuestCode(Long guestCode)
	{
		this.guestCode = guestCode;
	}

	public String getGuestLogin()
	{
		return guestLogin;
	}

	public void setGuestLogin(String guestLogin)
	{
		this.guestLogin = guestLogin;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
}
