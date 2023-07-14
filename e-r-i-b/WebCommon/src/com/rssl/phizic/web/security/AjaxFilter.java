package com.rssl.phizic.web.security;

import com.rssl.phizic.auth.modes.UserVisitingMode;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

/**
 * User: Moshenko
 * Date: 27.07.2011
 * Time: 16:03:32
 */
public class AjaxFilter extends LoginFilter
{
	private UserVisitingMode userVisitingMode;

	public void init(FilterConfig filterConfig) throws ServletException
	{
		super.init(filterConfig);		 
		userVisitingMode = UserVisitingMode.valueOf(filterConfig.getInitParameter("visitingMode"));
	}

	public UserVisitingMode getUserMode()
	{
		return userVisitingMode;
	}

	protected void sendToDublicateUrl(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		//поступаем так же как и при таймауте сессии, возвращаем пустой ответ,
		// скрипт на клиенте перезагрузит страницу и направит на необходимый УРЛ.
		response.getWriter().write(" ");
	}
}
