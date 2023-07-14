package com.rssl.phizic.security.fraud;

import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.logging.Log;

import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.rssl.phizic.context.Constants.*;
import static com.rssl.phizic.logging.Constants.LOG_MODULE_CORE;

public class PassMarkSimpleFSOServlet extends HttpServlet
{
	private static final Log LOG = PhizICLogFactory.getLog(LOG_MODULE_CORE);


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.getWriter().println("Not implemented");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession(false);
		if (session == null)
		{
			LOG.error("PassMarkSimpleFSOServlet: session object is null in fso servlet");
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

		String pmdata = request.getParameter(FSO_PM_DATA_ATTRIBUTE_NAME);
		session.setAttribute(USER_DEVICE_TOKEN_PREFIX_NAME + FLASH_SO_ATTRIBUTE_NAME, StringHelper.isEmpty(pmdata) ? null : URLEncoder.encode(pmdata, "UTF-8"));
	}
}
