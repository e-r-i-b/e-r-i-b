package com.rssl.auth.csa.back.servises.restrictions.security;

import com.rssl.auth.csa.back.exceptions.PasswordRestrictionException;
import com.rssl.auth.csa.back.servises.GuestPassword;
import com.rssl.auth.csa.back.servises.GuestProfile;
import com.rssl.auth.csa.back.servises.Password;
import com.rssl.auth.csa.back.servises.restrictions.Restriction;

import java.util.Calendar;
import java.util.List;

/**
 * ���������� �� ������������ ��������� ������ �� ���������� mounthCount �������.
 * @author niculichev
 * @ created 16.02.15
 * @ $Author$
 * @ $Revision$
 */
public class GuestPasswordHistoryRestriction implements Restriction<String>
{
	private GuestProfile profile;
	private int mounthCount;

	/**
	 * ctor
	 * @param profile �������� �������
	 * @param mounthCount ���������� ��������� �������
	 */
	public GuestPasswordHistoryRestriction(GuestProfile profile, int mounthCount)
	{
		this.profile = profile;
		this.mounthCount = mounthCount;
	}

	public void check(String passwordValue) throws Exception
	{
		Calendar toDate = Calendar.getInstance();
		Calendar fromDate = (Calendar) toDate.clone();
		fromDate.add(Calendar.MONTH, -mounthCount);

		List<GuestPassword> passwords = GuestPassword.getHistory(profile.getId(), fromDate, toDate);
		for (GuestPassword password : passwords)
		{
			if (password.check(passwordValue))
				throw new PasswordRestrictionException("������ �� ������ ��������� ������ ������ �� ��������� " + mounthCount + " ������.");
		}
	}
}
