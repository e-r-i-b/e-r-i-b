package com.rssl.phizic.web.actions;

import com.rssl.phizic.utils.http.UrlBuilder;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ExceptionConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Omeliyanchuk
 * @ created 27.11.2006
 * @ $Author$
 * @ $Revision$
 */

public class LastOneExceptionHandler extends org.apache.struts.action.ExceptionHandler
{
	public ActionForward execute ( Exception ex, ExceptionConfig ae, ActionMapping mapping, ActionForm formInstance,
	                               HttpServletRequest request, HttpServletResponse response ) throws ServletException
	{
		UrlBuilder builder = new UrlBuilder();

		String returnTo = request.getServletPath() +
				(request.getQueryString() == null ? "" : "?" + request.getQueryString());

		builder.setUrl(ae.getPath())
			   .addParameter("returnTo", returnTo);

		return new ActionForward(builder.getUrl(),true);
	}
}
