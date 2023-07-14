package com.rssl.phizgate.common.filters;

import com.Ostermiller.util.Base64;
import com.rssl.auth.csa.back.servises.UserLogonType;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.util.RequestHelper;

import java.io.IOException;
import java.text.ParseException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

/**
 * Фильтр приложений гейта
 *
 * @author khudyakov
 * @ created 02.07.14
 * @ $Author$
 * @ $Revision$
 */
public class GateContextFilter implements Filter
{
	public static final String OPERATION_CONTEXT_KEY               = OperationContext.class.getName().toUpperCase() + ".UID";
	public static final String SESSION_ID_KEY                      = LogThreadContext.class.getName().toUpperCase() + ".SESSION_ID";
	public static final String LOGIN_ID_KEY                        = LogThreadContext.class.getName().toUpperCase() + ".LOGIN_ID";
	public static final String PERSON_SURNAME_KEY                  = LogThreadContext.class.getName().toUpperCase() + "PERSON_SURNAME";
	public static final String PERSON_PATRNAME_KEY                 = LogThreadContext.class.getName().toUpperCase() + "PERSON_PATRNAME";
	public static final String PERSON_FIRSTNAME_KEY                = LogThreadContext.class.getName().toUpperCase() + "PERSON_FIRSTNAME";
	public static final String PERSON_SERIES_KEY                   = LogThreadContext.class.getName().toUpperCase() + "PERSON_SERIES";
	public static final String PERSON_NUMBER_KEY                   = LogThreadContext.class.getName().toUpperCase() + "PERSON_NUMBER";
	public static final String PERSON_BIRTHDAY                     = LogThreadContext.class.getName().toUpperCase() + "PERSON_BIRTHDAY";
	public static final String GUEST_CODE                          = LogThreadContext.class.getName().toUpperCase() + ".GUEST_CODE";
	public static final String GUEST_PHONE                         = LogThreadContext.class.getName().toUpperCase() + ".GUEST_PHONE";
	public static final String GUEST_LOGIN                         = LogThreadContext.class.getName().toUpperCase() + ".GUEST_LOGIN";


	public void init(FilterConfig filterConfig) throws ServletException
	{

	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) servletRequest;

		try
		{
			setCurrentContext(request);
			filterChain.doFilter(servletRequest, servletResponse);
		}
		finally
		{
			LogThreadContext.clear();
			OperationContext.clear();
		}
	}

	public void destroy()
	{

	}

	private void setCurrentContext(HttpServletRequest request)
	{
		String sessionId = request.getHeader(SESSION_ID_KEY);
		if (StringHelper.isNotEmpty(sessionId))
		{
			LogThreadContext.setSessionId(sessionId);
		}

		String operationUID = request.getHeader(OPERATION_CONTEXT_KEY);
		if (StringHelper.isNotEmpty(operationUID))
		{
			OperationContext.setCurrentOperUID(operationUID);
		}

		String loginId = request.getHeader(LOGIN_ID_KEY);
		if (StringHelper.isNotEmpty(loginId))
		{
			LogThreadContext.setLoginId(Long.valueOf(loginId));
		}

		String surName = request.getHeader(PERSON_SURNAME_KEY);
		if (StringHelper.isNotEmpty(surName))
		{
			LogThreadContext.setSurName(Base64.decode(surName));
		}

		String firstName = request.getHeader(PERSON_FIRSTNAME_KEY);
		if (StringHelper.isNotEmpty(firstName))
		{
			LogThreadContext.setFirstName(Base64.decode(firstName));
		}

		String partName = request.getHeader(PERSON_PATRNAME_KEY);
		if (StringHelper.isNotEmpty(partName))
		{
			LogThreadContext.setPatrName(Base64.decode(partName));
		}

		String docSeries = request.getHeader(PERSON_SERIES_KEY);
		if (StringHelper.isNotEmpty(docSeries))
		{
			LogThreadContext.setSeries(Base64.decode(docSeries));
		}

		String docNumber = request.getHeader(PERSON_NUMBER_KEY);
		if (StringHelper.isNotEmpty(docNumber))
		{
			LogThreadContext.setNumber(Base64.decode(docNumber));
		}

		String birthDay = request.getHeader(PERSON_BIRTHDAY);
		if (StringHelper.isNotEmpty(birthDay))
		{
			try
			{
				LogThreadContext.setBirthday(DateHelper.toCalendar(DateHelper.fromXMlDateToDate(birthDay)));
			}
			catch (ParseException ignore) {}
		}

		String guestCode = request.getHeader(GUEST_CODE);
		if(StringHelper.isNotEmpty(guestCode))
		{
			LogThreadContext.setGuestCode(Long.valueOf(guestCode));
		}
		String guestPhone = request.getHeader(GUEST_PHONE);
		if(StringHelper.isNotEmpty(guestPhone))
		{
			LogThreadContext.setGuestPhoneNumber(guestPhone);
		}
		String guestLogin = request.getHeader(GUEST_LOGIN);
		if(StringHelper.isNotEmpty(guestLogin))
		{
			LogThreadContext.setGuestLogin(guestLogin);
		}

		LogThreadContext.setIPAddress(RequestHelper.getIpAddress(request));
	}
}
