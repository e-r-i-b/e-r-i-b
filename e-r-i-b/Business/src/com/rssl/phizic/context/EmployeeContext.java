package com.rssl.phizic.context;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.logging.LogThreadContext;

import java.util.Map;
import java.util.HashMap;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 17.10.2005 Time: 13:09:08 */
public class EmployeeContext
{
	private static Map<Application, EmployeeDataProvider> employeeDataProvider = new HashMap<Application, EmployeeDataProvider>();

    public static void setEmployeeDataProvider(EmployeeDataProvider dataProvider)
    {
        setEmployeeDataProvider(dataProvider, LogThreadContext.getApplication());
    }

	public static void setEmployeeDataProvider(EmployeeDataProvider dataProvider, Application application)
    {
        employeeDataProvider.put(application, dataProvider);
    }

    public static EmployeeDataProvider getEmployeeDataProvider()
    {
        return employeeDataProvider.get(LogThreadContext.getApplication());
    }

	public static EmployeeDataProvider getEmployeeDataProvider(String application)
    {
        return employeeDataProvider.get(application);
    }

	public static boolean isAvailable()
	{
		return getEmployeeDataProvider() != null;
	}
}
