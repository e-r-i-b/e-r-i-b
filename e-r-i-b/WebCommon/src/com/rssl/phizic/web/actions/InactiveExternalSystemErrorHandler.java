package com.rssl.phizic.web.actions;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.common.types.exceptions.CompositeInactiveExternalSystemException;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.web.struts.forms.ActionMessagesKeys;
import com.rssl.phizic.config.ConfigFactory;
import org.apache.commons.lang.BooleanUtils;
import org.apache.struts.action.*;
import org.apache.struts.config.ExceptionConfig;
import org.apache.struts.Globals;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author khudyakov
 * @ created 30.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class InactiveExternalSystemErrorHandler extends org.apache.struts.action.ExceptionHandler
{
	public static final String FORWARD_SHOW_AJAX_ERROR_PAGE = "ShowAjaxInactiveSystemErrorPage";

	public ActionForward execute (Exception ex, ExceptionConfig ae, ActionMapping mapping, ActionForm formInstance, HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		ActionMessages messages = (ActionMessages) request.getAttribute(ActionMessagesKeys.inactiveExternalSystem.getKey());
		if (messages == null)
		{
			messages = new ActionMessages();
		}
		
		if (ex instanceof CompositeInactiveExternalSystemException)
		{
			//обрабатываем ошибку от нескольких внешних систем
			CompositeInactiveExternalSystemException ciese = (CompositeInactiveExternalSystemException) ex;
			for (String error : ciese.getErrors())
			{
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(error, false));
			}
		}
		else
		{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ex.getMessage(), false));
		}
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
		if(applicationConfig.getLoginContextApplication() == Application.PhizIA)
			request.setAttribute(Globals.ERROR_KEY, messages);
		else
			request.getSession().setAttribute(ActionMessagesKeys.inactiveExternalSystem.getKey(), messages);


		String path = ae.getPath();
		if(BooleanUtils.isTrue((Boolean)request.getAttribute(LookupDispatchAction.IS_AJAX_ATTRIBUTE_NAME)))
		{
			ActionForward forward = mapping.findForward(FORWARD_SHOW_AJAX_ERROR_PAGE);
			if(forward != null)
				path = forward.getPath();
		}

		if (mapping instanceof UserAgentActionMapping)
		{
			path = ((UserAgentActionMapping)mapping).getAgentRelativePath(path);
		}

		return new ActionForward(path);
	}
}
