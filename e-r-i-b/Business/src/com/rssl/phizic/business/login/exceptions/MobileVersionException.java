package com.rssl.phizic.business.login.exceptions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.config.ConfigFactory;

/**
 * Исключение о неверной версии мобильного приложения
 *
 * @author khudyakov
 * @ created 15.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class MobileVersionException extends BusinessException
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
