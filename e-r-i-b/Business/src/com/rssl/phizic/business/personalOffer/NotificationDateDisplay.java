package com.rssl.phizic.business.personalOffer;

import com.rssl.phizic.auth.Login;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author lukina
 * @ created 28.01.2014
 * @ $Author$
 * @ $Revision$
 */
public class NotificationDateDisplay implements Serializable
{
	private PersonalOfferNotification notification;
	private Login login;
	private Calendar displayDate;

	public PersonalOfferNotification getNotification()
	{
		return notification;
	}

	public void setNotification(PersonalOfferNotification notification)
	{
		this.notification = notification;
	}

	public Login getLogin()
	{
		return login;
	}

	public void setLogin(Login login)
	{
		this.login = login;
	}

	public Calendar getDisplayDate()
	{
		return displayDate;
	}

	public void setDisplayDate(Calendar displayDate)
	{
		this.displayDate = displayDate;
	}
}
