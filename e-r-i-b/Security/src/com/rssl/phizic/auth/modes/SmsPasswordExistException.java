package com.rssl.phizic.auth.modes;

import com.rssl.phizic.security.SecurityLogicException;

/**
 * @author eMakarov
 * @ created 30.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class SmsPasswordExistException extends SecurityLogicException
{
	private long leftTime;

	public SmsPasswordExistException(long diff)
	{
		leftTime = diff;
	}

	public long getLeftTime()
	{
		return leftTime;
	}
}
