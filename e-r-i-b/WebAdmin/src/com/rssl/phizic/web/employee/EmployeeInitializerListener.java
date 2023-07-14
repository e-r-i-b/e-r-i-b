package com.rssl.phizic.web.employee;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeDataProvider;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.init.InitializerListenerBase;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Evgrafov
 * @ created 16.08.2007
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */
public class EmployeeInitializerListener extends InitializerListenerBase
{
	public void onInitialized(ServletContextEvent servletContextEvent)
	{
		EmployeeDataProvider employeeDataProvider = new HttpEmployeeDataProvider();
		EmployeeContext.setEmployeeDataProvider(employeeDataProvider, Application.PhizIA);
		//инициализируем персон контекст
		PersonDataProvider personDataProvider = new HttpPersonDataProvider();
		PersonContext.setPersonDataProvider(personDataProvider, Application.PhizIA);
	}

	public void onDestroyed(ServletContextEvent servletContextEvent)
	{
	}
}