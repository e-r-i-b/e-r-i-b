package com.rssl.phizic.web.security;

import com.rssl.phizic.logging.LoggingHelper;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.web.common.ContextFilter;
import com.rssl.phizic.web.util.HttpSessionUtils;
import com.rssl.phizic.web.util.RequestHelper;
import org.apache.commons.lang.BooleanUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Krenev
 * @ created 26.02.2009
 * @ $Author$
 * @ $Revision$
 * Защита от смены IP в рамках 1 сесиии.
 */

public class SessionIdFilter implements Filter
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);
	public static final String IP_ADDRES_KEY = SessionIdFilter.class + ".ipAddressKey";
	public static final String INVALIDATE_SESSION_KEY = SessionIdFilter.class + ".invalidateSessionKey";

	public void init(FilterConfig filterConfig) throws ServletException {}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		Object prevIP = HttpSessionUtils.getSessionAttribute(request, IP_ADDRES_KEY);
		String currentIP = RequestHelper.getIpAddress(request);
		boolean selfCall = false;

		if (prevIP != null && !prevIP.equals(currentIP))
		{
			boolean needClean = true;
			//перебираем все локальные адреса машины, чтоб разрешить с них доступ.
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			while (networkInterfaces.hasMoreElements())
			{
				NetworkInterface ni = networkInterfaces.nextElement();
				Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();
				while (inetAddresses.hasMoreElements())
				{
					InetAddress ip = inetAddresses.nextElement();
					if (currentIP.equals(ip.getHostAddress()))
					{
						needClean = false;
						selfCall = true;
						break;
					}
				}
				if (!needClean)
					break;
			}

			if (needClean)
			{
				HttpSession session = request.getSession(false);
				if (session != null)
					ContextFilter.refreshSessionParameters(request);
				if (ApplicationUtil.isMobileApi())
				{
					log.info("Произошла смена IP-адреса. Предыдущий IP: " + prevIP + ", текущий IP: " + currentIP + ".");
					if (session != null)
						session.setAttribute(IP_ADDRES_KEY, currentIP);
				}
				else
				{
					try
					{
						log.error("Произошла смена IP-адреса. Предыдущий IP: " + prevIP + ", текущий IP: " + currentIP + ". Сессия будет инвалидирована.");
						if (session != null)
							session.invalidate();
					}
					catch (IllegalStateException e)
					{
						log.error("Сессия уже инвалидирована", e);
					}
				}
				if (session != null)
					ContextFilter.clearCurrentContext();
			}
		}

		try
		{
			filterChain.doFilter(servletRequest, servletResponse);
		}
		finally
		{
			HttpSession session = request.getSession(false);

			if (session != null)
			{
				Boolean mustInvalidate = (Boolean) session.getAttribute(INVALIDATE_SESSION_KEY);
				LoggingHelper.setAppServerInfoToLogThreadContext(request);
				if (BooleanUtils.isTrue(mustInvalidate))
					session.invalidate();
				else if (!selfCall) //если это не самовызов, то сохраняем, иначе храним предыдущее значение.
					session.setAttribute(IP_ADDRES_KEY, currentIP);
			}
		}
	}

	public void destroy() {}
}
