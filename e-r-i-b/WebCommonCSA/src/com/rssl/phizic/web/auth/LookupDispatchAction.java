package com.rssl.phizic.web.auth;

import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.WebContext;
import org.apache.struts.Globals;
import org.apache.struts.action.*;
import org.apache.struts.actions.DispatchAction;

import java.util.Collections;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author osminin
 * @ created 03.08.13
 * @ $Author$
 * @ $Revision$
 */
public abstract class LookupDispatchAction extends DispatchAction
{
	private static final String DEFAULT_METHOD_NAME = "start";
	private static final String REQUEST_PARAMETER_NAME = "operation";

	protected static final Log log = PhizICLogFactory.getLog(LogModule.Web);

	public abstract ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception;

	protected Map<String, String> getKeyMethodMap()
	{
		return Collections.emptyMap();
	}

	protected String lookupMethodName(String key, ActionMapping mapping) throws ServletException
	{
		String methodName = getKeyMethodMap().get(key);

		if (methodName == null)
		{
			String message = messages.getMessage("dispatch.lookup", mapping.getPath(), key);
			throw new ServletException(message);
		}

		return methodName;
	}

	protected String getMethodName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String parameter) throws Exception
	{
		return StringHelper.isEmpty(parameter) ? DEFAULT_METHOD_NAME : lookupMethodName(parameter, mapping);
	}

	protected String getParameter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return request.getParameter(REQUEST_PARAMETER_NAME);
	}

	protected void saveErrors(HttpServletRequest request, ActionMessages errors)
	{
		if (errors == null || errors.isEmpty())
			return;

		ActionMessages allErrors = new ActionMessages();
		allErrors.add((ActionMessages) request.getAttribute(Globals.ERROR_KEY));
		allErrors.add(errors);

		request.setAttribute(Globals.ERROR_KEY, allErrors);
	}

	protected void saveMessages(HttpServletRequest request, ActionMessages messages)
	{
		if (messages != null || messages.isEmpty())
			return;

		ActionMessages allMessages = new ActionMessages();
		allMessages.add((ActionMessages) request.getAttribute(Globals.MESSAGE_KEY));
		allMessages.add(messages);

		request.setAttribute(Globals.MESSAGE_KEY, allMessages);
	}

	protected HttpServletRequest currentRequest()
	{
		return WebContext.getCurrentRequest();
	}

	protected void saveError(HttpServletRequest request, ActionMessage error)
	{
		ActionMessages errors = new ActionMessages();
		errors.add(ActionMessages.GLOBAL_MESSAGE, error);
		saveErrors(request, errors);
	}

	protected void saveError(HttpServletRequest request, String error)
	{
		saveError(request, new ActionMessage(error,false));
	}
}
