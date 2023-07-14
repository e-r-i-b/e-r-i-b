package com.rssl.phizic.web.common;

import com.Ostermiller.util.Base64;
import com.rssl.phizic.config.locale.MultiLocaleContext;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.LoggingHelper;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.util.RequestHelper;

import java.io.IOException;
import java.text.ParseException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Omeliyanchuk
 * @ created 21.06.2009
 * @ $Author$
 * @ $Revision$
 * фильтр для установки контекста гейтовых модулей
 */

public class GateContextFilter implements Filter
{
	private static final String CONTEXT_KEY = "com.rssl.phizic.web.common.GateContextFilter.key";

	private static final String OPERATION_CONTEXT_KEY = OperationContext.class.getName().toUpperCase() + ".UID";
	private static final String SESSION_ID_KEY = LogThreadContext.class.getName().toUpperCase() + ".SESSION_ID";
	private static final String LOGIN_ID_KEY = LogThreadContext.class.getName().toUpperCase() + ".LOGIN_ID";
	private static final String PERSON_SURNAME_KEY = LogThreadContext.class.getName().toUpperCase() + "PERSON_SURNAME";
	private static final String PERSON_PATRNAME_KEY = LogThreadContext.class.getName().toUpperCase() + "PERSON_PATRNAME";
	private static final String PERSON_FIRSTNAME_KEY = LogThreadContext.class.getName().toUpperCase() + "PERSON_FIRSTNAME";
	private static final String PERSON_SERIES_KEY = LogThreadContext.class.getName().toUpperCase() + "PERSON_SERIES";
	private static final String PERSON_NUMBER_KEY = LogThreadContext.class.getName().toUpperCase() + "PERSON_NUMBER";
	private static final String PERSON_BIRTHDAY = LogThreadContext.class.getName().toUpperCase() + "PERSON_BIRTHDAY";
	public static final String PERSON_TB_KEY = LogThreadContext.class.getName().toUpperCase() + "PERSON_TB";
	public static final String INITIATOR_APPLICATION_KEY = LogThreadContext.class.getName().toUpperCase() + "INITIATOR_APPLICATION";
	public static final String GUEST_CODE = LogThreadContext.class.getName().toUpperCase() + ".GUEST_CODE";
	public static final String GUEST_PHONE = LogThreadContext.class.getName().toUpperCase() + ".GUEST_PHONE";
	public static final String GUEST_LOGIN = LogThreadContext.class.getName().toUpperCase() + ".GUEST_LOGIN";

	private ServletContext servletContext;

	public void init(FilterConfig filterConfig) throws ServletException
	{
		servletContext = filterConfig.getServletContext();
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		WebContext.setCurrentRequest(request);
		WebContext.setCurrentResponce(response);
		StoreManager.setCurrentStore(new SessionStore());

		try
		{
			setCurrentContext(request);
			filterChain.doFilter(servletRequest, servletResponse);
		}
		finally
		{
			clearCurrentContext();

			StoreManager.setCurrentStore(null);
			WebContext.setCurrentRequest(null);
		    WebContext.setCurrentResponce(null);
			MultiLocaleContext.setLocaleId(null);
		}
	}

	public void destroy()
	{
		servletContext = null;
	}

	public static void clearCurrentContext()
	{
		LogThreadContext.clear();
		OperationContext.clear();
	}

	private void setCurrentContext(HttpServletRequest request)
	{
		String operUID = request.getHeader(OPERATION_CONTEXT_KEY);
		String sessionId = request.getHeader(SESSION_ID_KEY);
		String loginId = request.getHeader(LOGIN_ID_KEY);
		String surname = request.getHeader(PERSON_SURNAME_KEY);
		String firstname = request.getHeader(PERSON_FIRSTNAME_KEY); 
		String partname = request.getHeader(PERSON_PATRNAME_KEY);
		String docSeries = request.getHeader(PERSON_SERIES_KEY);
		String docNumber = request.getHeader(PERSON_NUMBER_KEY);
		String tb = request.getHeader(PERSON_TB_KEY);
		String application = request.getHeader(INITIATOR_APPLICATION_KEY);
		String localeId = request.getHeader(MultiLocaleContext.LOCALE_KEY);
		String birthDay = request.getHeader(PERSON_BIRTHDAY);
		String guestPhoneNumber = request.getHeader(GUEST_PHONE);
		String guestCode = request.getHeader(GUEST_CODE);
		String guestLogin = request.getHeader(GUEST_LOGIN);
		if (!StringHelper.isEmpty(sessionId))
			LogThreadContext.setSessionId(sessionId);
		if (!StringHelper.isEmpty(operUID))
			OperationContext.setCurrentOperUID(operUID);
		if (!StringHelper.isEmpty(loginId))
			LogThreadContext.setLoginId(Long.valueOf(loginId));
		if (!StringHelper.isEmpty(surname))
			LogThreadContext.setSurName(Base64.decode(surname));
		if (!StringHelper.isEmpty(firstname))
			LogThreadContext.setFirstName(Base64.decode(firstname));
		if (!StringHelper.isEmpty(partname))
			LogThreadContext.setPatrName(Base64.decode(partname));
		if (!StringHelper.isEmpty(docSeries))
			LogThreadContext.setSeries(Base64.decode(docSeries));
		if (!StringHelper.isEmpty(docNumber))
			LogThreadContext.setNumber(Base64.decode(docNumber));
		if (!StringHelper.isEmpty(tb))
			LogThreadContext.setDepartmentRegion(Base64.decode(tb));
		if (!StringHelper.isEmpty(application))
			LogThreadContext.setInitiatorApplication(Application.valueOf(Base64.decode(application)));
		if(StringHelper.isNotEmpty(localeId))
			MultiLocaleContext.setLocaleId(Base64.decode(localeId));
		if (StringHelper.isNotEmpty(birthDay))
		{
			try
			{
				LogThreadContext.setBirthday(DateHelper.toCalendar(DateHelper.fromXMlDateToDate(birthDay)));
			}
			catch (ParseException ignore) {}
		}
		if(StringHelper.isNotEmpty(guestCode))
			LogThreadContext.setGuestCode(Long.valueOf(guestCode));
		if(StringHelper.isNotEmpty(guestPhoneNumber))
			LogThreadContext.setGuestPhoneNumber(guestPhoneNumber);
		if(StringHelper.isNotEmpty(guestLogin))
			LogThreadContext.setGuestLogin(guestLogin);

		LogThreadContext.setIPAddress(RequestHelper.getIpAddress(request));

		LoggingHelper.setAppServerInfoToLogThreadContext(request);
	}
}
