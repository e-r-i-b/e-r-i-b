package com.rssl.phizic.web.actions;

import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.security.SecurityUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.util.ExceptionLogHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ExceptionConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Хендлер срабатывающий при появлении ResourceNotFoundException
 * @author akrenev
 * @ created 15.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class ResourceNotFoundExceptionHandler extends org.apache.struts.action.ExceptionHandler
{
	public ActionForward execute ( Exception ex, ExceptionConfig ae, ActionMapping mapping, ActionForm formInstance,
	                               HttpServletRequest request, HttpServletResponse response ) throws ServletException
	{
		ExceptionLogHelper.writeLogMessage(ex);
		ResourceNotFoundBusinessException exception = (ResourceNotFoundBusinessException) ex;
		request.setAttribute("exceptionMessage", exception.getResourceClass().getName() + StringHelper.getEmptyIfNull(exception.getCaseOfException()));
		// атрибут для определения вида страницы с сообщением (с меню или нет)
		request.setAttribute("logonUser", SecurityUtil.isAuthenticationComplete());

		String path = ae.getPath();
		if (mapping instanceof UserAgentActionMapping)
			path = ((UserAgentActionMapping)mapping).getAgentRelativePath(path);
		return new ActionForward(path);
	}
}
