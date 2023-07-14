package com.rssl.phizic.web.security;

import com.rssl.phizic.auth.modes.UserVisitingMode;

/**
 * @author Mescheryakova
 * @ created 11.02.2011
 * @ $Author$
 * @ $Revision$
 */

public class BasicVersionLoginFilter extends LoginFilter
{
	public UserVisitingMode getUserMode()
	{
		return UserVisitingMode.BASIC;
	}
}
