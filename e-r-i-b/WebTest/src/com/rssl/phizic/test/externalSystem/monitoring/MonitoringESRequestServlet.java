package com.rssl.phizic.test.externalSystem.monitoring;

import com.rssl.phizic.test.externalSystem.monitoring.result.MonitoringESRequestInfoCSVBuilder;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 24.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Сервлет, собирающий инфу по обращениям к ВС (на основе логов)
 */

public class MonitoringESRequestServlet extends HttpServlet
{
	private static final int DEFAULT_MAX_WAIT_TIME = 1000;

	private static boolean started = false;

	public void init(ServletConfig servletConfig) throws ServletException
	{
		super.init(servletConfig);

		if (!Config.getInstance().useMonitoring())
			return;

		try
		{
			final int timeout = getIntInitialParameter(servletConfig, "timeout", DEFAULT_MAX_WAIT_TIME);
			final String queueName = servletConfig.getInitParameter("queue-name");
			final String queueFactoryName = servletConfig.getInitParameter("queue-factory-name");

			MonitoringESRequestTreadManager.getInstance().run(timeout, queueName, queueFactoryName);
		}
		catch (Exception e)
		{
			e.printStackTrace(System.out);
			stopThreads();
			throw new ServletException(e);
		}
		started = true;
	}

	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String operation = request.getParameter("operation");
		if ("start".equals(operation))
		{
			doStart(request, response);
			return;
		}
		if ("stop".equals(operation))
		{
			doStop(request, response);
			return;
		}
		if ("clear".equals(operation))
		{
			doClear(request, response);
			return;
		}
		if ("unload".equals(operation))
		{
			doUnload(request, response);
			return;
		}
		if ("stopThreads".equals(operation))
		{
			doStopThreads(request, response);
			return;
		}

		doError(request, response, "Неизвестное действие!");
	}

	private void saveMessage(HttpServletRequest request, String message)
	{
		request.getSession().setAttribute("message", message);
	}

	private void doStart(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		try
		{
			MonitoringESRequestCollector.getInstance().start();
			doOk(response);
		}
		catch (Exception e)
		{
			e.printStackTrace(System.out);
			doError(request, response, "Ошибка старта мониторинга: " + e.getMessage());
		}
	}

	private void doStop(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		try
		{
			MonitoringESRequestCollector.getInstance().stop();
			doOk(response);
		}
		catch (Exception e)
		{
			e.printStackTrace(System.out);
			doError(request, response, "Ошибка остановки мониторинга: " + e.getMessage());
		}
	}

	private void doClear(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		try
		{
			MonitoringESRequestCollector.getInstance().init();
			doOk(response);
		}
		catch (Exception e)
		{
			e.printStackTrace(System.out);
			doError(request, response, "Ошибка очистки информации мониторинга: " + e.getMessage());
		}
	}

	private void doUnload(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		try
		{
			byte[] data = MonitoringESRequestInfoCSVBuilder.build();
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			buffer.write(data);

			response.setContentLength(buffer.size());
			response.setContentType("application/octetstream");
			String fileName = DateHelper.formatDateToStringOnPattern(Calendar.getInstance(), "yyyy.MM.dd.HH.mm") + "-monitoring.csv";
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			response.setHeader("cache-control", "");

			ServletOutputStream outStream = response.getOutputStream();
			buffer.writeTo(outStream);
			outStream.flush();
			outStream.close();
		}
		catch (Exception e)
		{
			e.printStackTrace(System.out);
			doError(request, response, "Ошибка выгрузки информации мониторинга: " + e.getMessage());
		}
	}

	private void doStopThreads(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		try
		{
			stopThreads();
			doOk(response);
		}
		catch (Exception e)
		{
			e.printStackTrace(System.out);
			doError(request, response, "Ошибка остановки потоков мониторинга: " + e.getMessage());
		}
	}

	private void doOk(HttpServletResponse response) throws IOException
	{
		response.sendRedirect("/PhizIC-test/externalSystem/monitoring.do");
	}

	private void doError(HttpServletRequest request, HttpServletResponse response, String message) throws IOException
	{
		saveMessage(request, message);
		response.sendRedirect("/PhizIC-test/externalSystem/monitoring.do");
	}


	/**
	 * @return запущен ли сервлет
	 */
	public static boolean isStarted()
	{
		return started;
	}

	public void destroy()
	{
		stopThreads();
		super.destroy();
	}

	private void stopThreads()
	{
		MonitoringESRequestTreadManager.getInstance().stop();
	}

	private int getIntInitialParameter(ServletConfig servletConfig, String parameterName, int defaultValue)
	{
		String parameter = servletConfig.getInitParameter(parameterName);
		if (StringHelper.isEmpty(parameter))
		{
			return defaultValue;
		}
		return Integer.valueOf(parameter);
	}
}
