package com.rssl.phizic.auth.sms;

import com.rssl.phizic.security.SecurityLogicException;

/**
 * @author eMakarov
 * @ created 26.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class NoActiveSmsPasswordException extends SecurityLogicException
{
	public NoActiveSmsPasswordException()
	{
		super();
	}

	public NoActiveSmsPasswordException(String s)
	{
		super(s);
	}
}
