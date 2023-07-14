package com.rssl.phizic.scheduler;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.subscribeFee.SubscribeFeeService;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbSubscribeFeeConfig;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.module.ModuleManager;
import com.rssl.phizic.web.servlet.ModuleServletManager;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.quartz.ee.servlet.QuartzInitializerServlet;
import org.quartz.impl.StdSchedulerFactory;

import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

/**
 * @author Gulov
 * @ created 18.06.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Запускает quartz-шедулер для модульных джобов (StatefulModuleJob)
 * Для этого в контекст шедуллера помещается менеджер модулей (ModuleManager)
 */
public class SchedulerStarter extends QuartzInitializerServlet
{
	private final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private Scheduler scheduler;

	private final SubscribeFeeService subscribeFeeService = new SubscribeFeeService();

	///////////////////////////////////////////////////////////////////////////

	@Override
	public void init(ServletConfig cfg) throws ServletException
	{
		ClassLoader old = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
		super.init(cfg);
		String factoryKey = cfg.getInitParameter("servlet-context-factory-key");
		if (factoryKey == null)
			factoryKey = QUARTZ_FACTORY_KEY;
		StdSchedulerFactory factory = (StdSchedulerFactory) getServletContext().getAttribute(factoryKey);
		try
		{
			ModuleManager moduleManager = new ModuleServletManager(cfg.getServletContext());
			scheduler = factory.getScheduler();
			SchedulerContext schedulerContext = scheduler.getContext();
			schedulerContext.put(ModuleManager.class.getName(), moduleManager);
		}
		catch (SchedulerException e)
		{
			throw new ServletException(e);
		}
		finally
		{
			Thread.currentThread().setContextClassLoader(old);
		}
		//обновляем триггеры выгрузки ФПП
		updateSubscribeFeeTriggers();
	}

	private void updateSubscribeFeeTriggers() throws ServletException
	{
		Application currentApplication = null;
		if (ApplicationInfo.isDefinedCurrentApplication())
			currentApplication = ApplicationInfo.getCurrentApplication();

		ApplicationInfo.setCurrentApplication(Application.ErmbSubscribeFee);

		List<String> unloadTimeList = ConfigFactory.getConfig(ErmbSubscribeFeeConfig.class).getFppUnloadTime();
		try
		{
			subscribeFeeService.updateFPPTriggers(unloadTimeList);
		}
		catch (BusinessException e)
		{
			throw new ServletException(e);
		}
		finally
		{
			ApplicationInfo.setCurrentApplication(currentApplication);
		}
	}

	public void destroy()
	{
		try
		{
			if (scheduler != null)
			{
				scheduler.shutdown(true);

				SchedulerContext schedulerContext = scheduler.getContext();
				schedulerContext.remove(ModuleManager.class.getName());
			}
		}
		catch (SchedulerException e)
		{
			log.error("Сбой на останове механизма шедуллера", e);
		}
		catch (RuntimeException e)
		{
			log.error("Сбой на останове механизма шедуллера", e);
		}
	}
}
