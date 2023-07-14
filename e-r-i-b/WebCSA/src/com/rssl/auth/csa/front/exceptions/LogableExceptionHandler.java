package com.rssl.auth.csa.front.exceptions;

import com.rssl.phizic.web.common.LookupDispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.commons.logging.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.LogModule;
import org.apache.struts.config.ExceptionConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ’ендлер обработки ошибок с записью в лог приложени€.
 */
public class LogableExceptionHandler extends ExceptionHandler
{
	public static final String FORWARD_SHOW_FULL_ERROR_PAGE = "errorFullPage";
	public static final String ASYNC_SHOW_FULL_ERROR_PAGE_FORWARD = "asyncErrorFullPage";

	public static final String IS_REQUEST_AJAX_ATTRIBUTE_NAME       = "request-is-ajax";

	protected static final Log log = PhizICLogFactory.getLog(LogModule.Web);

	protected void logException(Exception e)
	{
		log.error(e.getMessage(), e);
    }

	public ActionForward execute(Exception ex, ExceptionConfig ae, ActionMapping mapping, ActionForm formInstance,
		                             HttpServletRequest request, HttpServletResponse response) throws ServletException
	{

		ActionForward forward = super.execute(ex, ae,mapping,formInstance,request, response);
		//если требуетс€ полна€ страница с ошибкой(не popup)
		Boolean requestAttribute = (Boolean) request.getAttribute(IS_REQUEST_AJAX_ATTRIBUTE_NAME);
		if (requestAttribute != null && requestAttribute)
			return mapping.findForward(ASYNC_SHOW_FULL_ERROR_PAGE_FORWARD);

		requestAttribute = (Boolean) request.getAttribute(LookupDispatchAction.IS_USE_FULL_ERROR_PAGE_ATTRIBUTE_NAME);
		if(requestAttribute != null && requestAttribute)
			return mapping.findForward(FORWARD_SHOW_FULL_ERROR_PAGE);
		return forward;

	}
}
