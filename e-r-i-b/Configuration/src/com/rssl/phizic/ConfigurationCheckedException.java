package com.rssl.phizic;

/**
 * @author Omeliyanchuk
 * @ created 20.12.2007
 * @ $Author$
 * @ $Revision$
 */

public class ConfigurationCheckedException extends Exception
{
    public ConfigurationCheckedException(String message)
    {
        super(message);
    }

    public ConfigurationCheckedException(Throwable cause)
    {
        super(cause);
    }

	public ConfigurationCheckedException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
