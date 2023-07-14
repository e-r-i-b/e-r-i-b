package com.rssl.auth.csa.back.exceptions;

import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.GuestProfile;
import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * Ошибка аутентификации при гостевом входе
 * @author niculichev
 * @ created 19.01.15
 * @ $Author$
 * @ $Revision$
 */
public class GuestAuthenticationFailedException extends LogicException
{
	private GuestProfile profile;

	public GuestAuthenticationFailedException()
	{
		super();
	}

	public GuestAuthenticationFailedException(GuestProfile profile)
	{
		super();
		this.profile = profile;
	}

	/**
	 * @return гостевой профиль, для которого не прошла аутентификация, либо null если вход по телефону
	 */
	public GuestProfile getProfile()
	{
		return profile;
	}
}
