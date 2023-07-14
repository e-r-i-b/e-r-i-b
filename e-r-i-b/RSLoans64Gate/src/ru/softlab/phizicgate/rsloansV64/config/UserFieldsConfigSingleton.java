package ru.softlab.phizicgate.rsloansV64.config;

import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Omeliyanchuk
 * @ created 13.12.2007
 * @ $Author$
 * @ $Revision$
 */

public final class UserFieldsConfigSingleton
{
	private static final UserFieldsConfig config = new UserFieldsConfig();
	private static boolean isInit = false;

    public static UserFieldsConfig getConfig() throws GateException
    {
	    if(!isInit)
	        config.init();

        return config;
    }
}
