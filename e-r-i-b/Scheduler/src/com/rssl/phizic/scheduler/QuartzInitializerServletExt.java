package com.rssl.phizic.scheduler;

import org.quartz.*;
import org.quartz.ee.servlet.QuartzInitializerServlet;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

/**
 * @author Omeliyanchuk
 * @ created 05.02.2008
 * @ $Author$
 * @ $Revision$
 */

public class QuartzInitializerServletExt extends QuartzInitializerServlet
{
	private Scheduler scheduler = null;
	private static final String FACTORY_KEY = "servlet-context-factory-key";

	public void init(ServletConfig cfg) throws ServletException
	{
		ClassLoader old = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader( getClass().getClassLoader() );
		try
		{
			super.init(cfg);

			String factoryKey = getServletConfig().getInitParameter(FACTORY_KEY);
			if(factoryKey == null)
				factoryKey = QUARTZ_FACTORY_KEY;

			StdSchedulerFactory factory =
					(StdSchedulerFactory) getServletContext().getAttribute(factoryKey);

			// сохраняем шедулер в переменной, чтоб использовать его при остановке
			scheduler =  factory.getScheduler();
		}
		catch (SchedulerException e)
		{
			throw new ServletException(e);
		}
		finally
		{
			Thread.currentThread().setContextClassLoader( old );
		}
	}

	public void destroy()
	{
		// отключаем запуск джобов
		super.destroy();

		try
		{
			if (scheduler != null)
			{
				// информируем джобы что пора закругляться
				interruptSchedulers(scheduler);
				// ждем окончания работы джобов
				waitSchedulers(scheduler);
            }
		}
		catch (SchedulerException e)
		{
			log("Quartz Scheduler failed to shutdown cleanly: " + e.toString());
		}
    }

	private void interruptSchedulers(Scheduler scheduler) throws SchedulerException
	{

		for(Object context : scheduler.getCurrentlyExecutingJobs())
		{
			JobDetail detail = ((JobExecutionContext) context).getJobDetail();

			try
			{
				scheduler.interrupt(detail.getName(), detail.getGroup());
			}
			catch (UnableToInterruptJobException e)
			{
				// если джоб не реализует InterruptableJob или метод interrupt() выкидывает исключение
				log("Job (name is ' "+ detail.getName() + "', group is '"
						+ detail.getGroup() + "') not relise InterruptableJob or method interrupt() throw exception", e);
			}
		}
	}

	private void waitSchedulers(Scheduler scheduler) throws SchedulerException
	{
		while (scheduler.getCurrentlyExecutingJobs().size() > 0)
		{
			try
			{
				Thread.sleep(100);
			}
			catch (Exception ignore)
			{
				log("Error at expectation of the termination of working threads", ignore);
			}
		}
	}
}
