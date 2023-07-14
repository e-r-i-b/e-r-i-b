package com.rssl.phizic.web.actions;

import com.rssl.phizic.common.types.exceptions.InvalidSessionException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ExceptionConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author krenev
 * @ created 23.03.2012
 * @ $Author$
 * @ $Revision$
 * Обработчик исключений по умолчанию.
 * 1) складывает в атирбут реквеста "errorMessageKey" значение ключа бандла сообщения ошибки
 * 2) в зависимости от признаков  isEmptyErrorPage, isPartErrorPage, isAjax переходит на соотвествующие форварды.
 */

public class DefaultExceptionHandler  extends org.apache.struts.action.ExceptionHandler
{
	public static final String FORWARD_SHOW_EMPTY_ERROR_PAGE = "ShowEmptyErrorPage";
	public static final String FORWARD_SHOW_PART_ERROR_PAGE = "ShowPartErrorPage";
	public static final String FORWARD_SHOW_AJAX_ERROR_PAGE = "ShowAjaxErrorPage";
    public static final String FORWARD_SHOW_AJAX_PART_ERROR_PAGE = "ShowAjaxPartErrorPage";
	public static final String FORWARD_SHOW_ERROR_PAGE = "ShowErrorPage";
	public static final String FORWARD_UNAUTHORIZED_INDEX = "GotoUnauthorizedIndex";

	public ActionForward execute(Exception ex, ExceptionConfig ae, ActionMapping mapping, ActionForm formInstance,
	                             HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		request.setAttribute("errorMessageKey", ae.getKey());
		if (getRequestBooleanParameter(request, LookupDispatchAction.IS_EMPTY_ERROR_PAGE_ATTRIBUTE_NAME))
		{
			return mapping.findForward(FORWARD_SHOW_EMPTY_ERROR_PAGE);
		}
        //ajax-action в оплате с внешних ссылок.
        else if (getRequestBooleanParameter(request, LookupDispatchAction.IS_AJAX_ATTRIBUTE_NAME)
                && getRequestBooleanParameter(request, LookupDispatchAction.IS_PART_ERROR_PAGE_ATTRIBUTE_NAME))
        {
            return mapping.findForward(FORWARD_SHOW_AJAX_PART_ERROR_PAGE);
        }
        //ajax-action
        else if (getRequestBooleanParameter(request, LookupDispatchAction.IS_AJAX_ATTRIBUTE_NAME))
		{
			return mapping.findForward(FORWARD_SHOW_AJAX_ERROR_PAGE);
		}
        //оплата с внешних ссылок
		else if (getRequestBooleanParameter(request, LookupDispatchAction.IS_PART_ERROR_PAGE_ATTRIBUTE_NAME))
		{
			return mapping.findForward(FORWARD_SHOW_PART_ERROR_PAGE);
		}
		//сессия инвалидирована переходим на страницу входа
		else if(ex instanceof InvalidSessionException)
		{
			return mapping.findForward(FORWARD_UNAUTHORIZED_INDEX);
		}
		return mapping.findForward(FORWARD_SHOW_ERROR_PAGE);
	}

	protected boolean getRequestBooleanParameter(HttpServletRequest request, String name)
	{
		Boolean attribute = (Boolean) request.getAttribute(name);
		return (attribute != null && attribute);
	}
}
