package com.rssl.phizic.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author krenev
 * @ created 01.10.2010
 * @ $Author$
 * @ $Revision$
 * ”тилитный сервлет дл€ тестировани€ факта отсылки  HTTP запроса
 * ѕишет в лог тело запроса и возвращает в респонс оригинальный запрос. 
 */
public class EchoServlet extends GenericServlet
{
	private static final Log log = LogFactory.getLog(EchoServlet.class);

	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException
	{
		StringBuilder logBuilder = new StringBuilder("ѕолучен новый запрос\n");
		try
		{
			PrintWriter writer = response.getWriter();
			BufferedReader reader = request.getReader();
			response.setContentType(request.getContentType());
			String line = reader.readLine();
			while (line != null)
			{
				writer.println(line);
				logBuilder.append(line);
				logBuilder.append("\n");
				line = reader.readLine();
			}
		}
		finally
		{
			log.info(logBuilder);
		}
	}
}
