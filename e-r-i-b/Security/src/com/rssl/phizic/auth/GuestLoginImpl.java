package com.rssl.phizic.auth;

/**
 * @author niculichev
 * @ created 27.12.14
 * @ $Author$
 * @ $Revision$
 */
public class GuestLoginImpl extends LoginImpl implements GuestLogin
{
	private String authPhone;
	private Long guestCode;

	public GuestLoginImpl(String authPhone, Long authCode)
	{
		this.authPhone = authPhone;
		this.guestCode = authCode;
		setUserId(authPhone); // костыль для работы в PrincipalBase
	}

	public String getAuthPhone()
	{
		return authPhone;
	}

	public Long getGuestCode()
	{
		return guestCode;
	}

	public void setId(Long id)
	{
		throw new UnsupportedOperationException("В гостевой сессии не поддерживается");
	}

	public Long getId()
	{
		throw new UnsupportedOperationException("В гостевой сессии не поддерживается");
	}
}
