package com.rssl.auth.csa.back.servises.restrictions.security;

import com.rssl.auth.csa.back.servises.Password;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.connectors.CSAConnector;
import com.rssl.auth.csa.back.servises.restrictions.Restriction;
import com.rssl.auth.csa.back.exceptions.PasswordRestrictionException;

import java.util.List;
import java.util.Calendar;

/**
 * @author krenev
 * @ created 23.11.2012
 * @ $Author$
 * @ $Revision$
 * ќграничени на несовпадение парол€ за предыдущие mounthCount мес€цев.
 */
public class PasswordHistoryRestriction implements Restriction<String>
{
	private Profile profile;
	private int mounthCount;

	public PasswordHistoryRestriction(Profile profile, int mounthCount)
	{
		if (profile == null)
		{
			throw new IllegalArgumentException("профиль не может быть null");
		}
		this.profile = profile;
		this.mounthCount = mounthCount;
	}

	public void check(String object) throws Exception
	{
		List<CSAConnector> connectors = CSAConnector.findByProfileID(profile.getId());
		for (CSAConnector connector : connectors)
		{
			checkByConnector(connector, object);
		}
	}

	private void checkByConnector(CSAConnector connector, String object) throws Exception
	{
		Calendar toDate = Calendar.getInstance();
		Calendar fromDate = (Calendar) toDate.clone();
		fromDate.add(Calendar.MONTH, -mounthCount);
		List<Password> passwords = connector.getPasswordsHistory(fromDate, toDate);
		for (Password password : passwords)
		{
			if (password.check(object))
			{
				throw new PasswordRestrictionException("ѕароль не должен повтор€ть старые пароли за последние " + mounthCount + " мес€ца.");//TODO согласовать текстовку
			}
		}
	}
}