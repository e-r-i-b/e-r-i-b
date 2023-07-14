package com.rssl.phizic.web.common;

import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.web.io.ServletOutputStreamLoger;
import com.rssl.phizic.web.servlet.HttpServletResponseJSPLoger;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ‘ильтр, логирующий запросы и ответы mAPI
 * @author Rydvanskiy
 * @ created 13.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class LogMobileApiFilter implements Filter
{
	private static final System SYSTEM_ID = System.mobile;
	// перечень запрешенных полей
	private static final String[] PASSWORD_KEYS = {"password", "smsPassword", "confirmSmsPassword", "mGUID"};
	private static final String MOCK_PASSWORD = "***";
	private static final String REG_ADDRESS = "registerApp.do";
	private static final String MGUID_REGEX = "(?<=<mGUID>).+(?=</mGUID>)";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public void init(FilterConfig config)
	{
	}

	// ‘ормируем запрос дл€ логов
	private String getRequestMessage(HttpServletRequest request)
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("<request><user_ip>").append(LogThreadContext.getIPAddress()).append("</user_ip>");

		for (Enumeration params = request.getParameterNames(); params.hasMoreElements();)
		{
			String key = (String) params.nextElement();
			String[] values = request.getParameterValues(key);
			boolean isPassword = ArrayUtils.contains(PASSWORD_KEYS, key);
			for (String value : values)
			{
				if (isPassword)
					buffer.append("<" + key + ">" + MOCK_PASSWORD + "</" + key + ">");
				else
					buffer.append("<" + key + ">" + value + "</" + key + ">");
			}
		}
		if (ServletFileUpload.isMultipartContent(request))
			buffer.append("<multipart>форма с content-type='multipart/form-data'</multipart>");

		buffer.append("</request>");
		return buffer.toString();
	}

	private void log(HttpServletRequest req, HttpServletResponseJSPLoger logResponse, long executionTime) throws IOException
	{
		// запись в журнале
		MessagingLogEntry logEntry = MessageLogService.createLogEntry();
		try {
			String logMessage = ((ServletOutputStreamLoger)logResponse.getOutputStream()).getLogMessage();
			logEntry.setSystem(getSystem());

			logEntry.setMessageRequestId("0");
			logEntry.setMessageType( req.getRequestURI() );
			logEntry.setMessageRequest(getRequestMessage (req));

			logEntry.setMessageResponseId("0");
			if (logEntry.getMessageType().endsWith(REG_ADDRESS))
				logMessage = logMessage.replaceAll(MGUID_REGEX, MOCK_PASSWORD);
			logEntry.setMessageResponse(logMessage);

			logEntry.setExecutionTime(executionTime);

			MessageLogWriter writer = MessageLogService.getMessageLogWriter();
			writer.write(logEntry);
		}
		catch (Exception ex)
		{
			log.error("ѕроблемы с записью в журнал сообщений", ex);
		}
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException
	{
		// ќборачиваем response дл€ перехвата лога
		HttpServletResponseJSPLoger logResponse = new HttpServletResponseJSPLoger((HttpServletResponse) resp);
		long begin = java.lang.System.currentTimeMillis();;
		try
		{
			chain.doFilter(req, logResponse);
		}
		finally
		{
			logResponse.flushBuffer();
			long end = java.lang.System.currentTimeMillis();
			log( (HttpServletRequest) req, logResponse, end - begin);
		}
	}

	public void destroy()
	{
	}

	protected System getSystem()
	{
		return SYSTEM_ID;
	}
}
