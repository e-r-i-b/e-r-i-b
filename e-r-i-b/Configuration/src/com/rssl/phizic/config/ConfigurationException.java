package com.rssl.phizic.config;

/**
 * @author Roshka
 * @ created 31.01.2007
 * @ $Author$
 * @ $Revision$
 */

public class ConfigurationException extends RuntimeException
{
	public ConfigurationException(String message)
	{
		super(message);
	}

	public ConfigurationException(String message, Throwable cause)
	{
		super(message, cause);
	}
}