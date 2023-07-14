package com.rssl.auth.csamapi.exceptions;

import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.mobile.MobileApiConfig;

/**
 * @author osminin
 * @ created 26.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Ошибка версии МАПИ
 */
public class MobileVersionException extends FrontException
{
	public MobileVersionException()
	{
		super(ConfigFactory.getConfig(MobileApiConfig.class).getInvalidAPIVersionText());
	}

	public MobileVersionException(String message)
	{
		super(message);
	}

	public MobileVersionException(Throwable cause)
	{
		super(cause);
	}

	public MobileVersionException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
