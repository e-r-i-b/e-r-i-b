package com.rssl.phizic.web.security;

import com.rssl.phizic.auth.modes.UserVisitingMode;

/**
 * @author niculichev
 * @ created 11.01.15
 * @ $Author$
 * @ $Revision$
 */
public class GuestVersionLoginFilter extends LoginFilter
{
	@Override
	UserVisitingMode getUserMode()
	{
		return UserVisitingMode.GUEST;
	}
}
