package com.rssl.phizic.context;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.logging.LogThreadContext;

import java.util.HashMap;
import java.util.Map;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 17.10.2005 Time: 13:09:08 */
public class PersonContext
{
    private static Map<Application, PersonDataProvider> personDataProvider = new HashMap<Application, PersonDataProvider>();

    public static void setPersonDataProvider(PersonDataProvider dataProvider)
    {
	    setPersonDataProvider(dataProvider, LogThreadContext.getApplication());
    }

	public static void setPersonDataProvider(PersonDataProvider dataProvider, Application application)
    {
        personDataProvider.put(application, dataProvider);
    }

    public static PersonDataProvider getPersonDataProvider()
    {
       return personDataProvider.get(LogThreadContext.getApplication());
    }

	public static PersonDataProvider getPersonDataProvider(String application)
    {
        return personDataProvider.get(application);
    }

	public static PersonDataProvider getPersonDataProvider(Application application)
    {
        return personDataProvider.get(application);
    }

	/**
	 * @return true - персона проинициализирована
	 */
	public static boolean isAvailable()
	{
		return personDataProvider != null && personDataProvider.get(LogThreadContext.getApplication()) != null
		       && personDataProvider.get(LogThreadContext.getApplication()).getPersonData() != null;
	}
}
