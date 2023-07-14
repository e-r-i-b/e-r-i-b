package com.rssl.phizic.web.common;

import com.rssl.auth.csa.front.business.regions.RegionHelper;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.util.FraudMonitoringUtils;
import org.apache.commons.logging.Log;
import org.apache.struts.Globals;
import org.apache.struts.action.*;
import org.apache.struts.actions.DispatchAction;

import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author niculichev
 * @ created 17.08.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class LookupDispatchAction extends DispatchAction
{
	public static final String IS_USE_FULL_ERROR_PAGE_ATTRIBUTE_NAME = "useFullErrorPage";

	private static final String DEFAULT_METHOD_NAME = "start";
	private static final String REQUEST_PARAMETER_NAME = "operation";

	private static final String SKIN_URL_PARAMETER_NAME = "resourcesRealPath";

	private static final String HELP_URL = "/help";
	private static final String HELP_ID_PARAMETER_NAME = "$$helpId";
    
	private static final String SKIN_URL_ATTRIBUTE_NAME = "skinUrl";
	private static final String GLOBAL_URL_ATTRIBUTE_NAME = "globalUrl";

	private static final String REPLACE_METHOD_NAME = "replaceMethodName";

	protected static final Log log = PhizICLogFactory.getLog(LogModule.Web);

	protected abstract Map<String, String> getKeyMethodMap();

	public abstract ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception;

	protected void setMainCurrentUrl()
	{
		HttpServletRequest request = currentRequest();
		String mainURL = request.getRequestURI();
		String queryString = request.getQueryString();
		if (StringHelper.isNotEmpty(queryString))
			mainURL += "?".concat(queryString);
		URLContext.setMainUrlInfo(mainURL);
	}

	protected String getLookupMapName(HttpServletRequest request, String keyName, ActionMapping mapping) throws ServletException
	{
		String methodName = getKeyMethodMap().get(keyName);
		if (methodName == null)
		{
			String message = messages.getMessage( "dispatch.lookup", mapping.getPath(), keyName);
			throw new ServletException(message);
		}

		return methodName;
	}

	protected String getDefaultMethodName()
	{
		return DEFAULT_METHOD_NAME;
	}

	protected String getMethodName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String parameter)
			throws Exception
	{
		String replaceMethodName = getReplaceMethodName(request);
		if(replaceMethodName != null)
		{
			return replaceMethodName;
		}

		if (parameter == null || parameter.length() == 0)
		{
			return getDefaultMethodName();
		}

		return getLookupMapName(request, parameter, mapping);
	}

	protected String getParameter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return request.getParameter(REQUEST_PARAMETER_NAME);
	}

	public final ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ServletContext servletContext = getServlet().getServletContext();
		// устанавливае в запрос глобавльные переменные с url'aми
		request.setAttribute(SKIN_URL_ATTRIBUTE_NAME, servletContext.getInitParameter(SKIN_URL_PARAMETER_NAME));
		request.setAttribute(GLOBAL_URL_ATTRIBUTE_NAME, ApplicationInfo.getCurrentApplication().name());

		RegionHelper.setRegion(request);
		//устанавливаем признак отображения полной страницы с ошибкой(header+body+footer)
		request.setAttribute(IS_USE_FULL_ERROR_PAGE_ATTRIBUTE_NAME, useFullErrorPage());
		return doExecute(mapping, form, request, response);
	}

	public ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward forward = super.execute(mapping, form, request, response);

		String helpId = getHelpId(mapping, form);
		if(!helpId.equals(HELP_URL))
			request.setAttribute(HELP_ID_PARAMETER_NAME, StringHelper.encodeURL(helpId));
		RegionHelper.saveRequestRegionToCookie(response);

		FraudMonitoringUtils.storeTokens();         //сохраняем полученные от ФМ значения токенов в сессию
		FraudMonitoringUtils.storeCookie();         //обновляем значение cookie
		return forward;
	}

	protected void saveErrors(HttpServletRequest request, ActionMessages errors)
	{
		if (errors == null || errors.isEmpty())
			return;

		ActionMessages allErrors = (ActionMessages) request.getAttribute(Globals.ERROR_KEY);
		if (allErrors != null)
			allErrors.add(errors);
		else allErrors = errors;

		request.setAttribute(Globals.ERROR_KEY, allErrors);
	}

	protected void saveError(HttpServletRequest request, ActionMessage message)
	{
		ActionMessages errors = new ActionMessages();
		errors.add(ActionMessages.GLOBAL_MESSAGE, message);

		saveErrors(request, errors);
	}

    protected void saveError(HttpServletRequest request, String message)
	{
		saveError(request, new ActionMessage(message, false));
	}

	protected void saveMessages(HttpServletRequest request, ActionMessages messages)
	{
		if (messages == null || messages.isEmpty())
			return;

		ActionMessages allMessages = (ActionMessages) request.getAttribute(Globals.MESSAGE_KEY);
		if (allMessages != null)
			allMessages.add(messages);
		else
			allMessages = messages;

		request.setAttribute(Globals.MESSAGE_KEY, allMessages);
	}

	protected void saveMessage(HttpServletRequest request, ActionMessage message)
	{
		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, message);

		saveMessages(request, messages);
	}

    protected void saveMessage(HttpServletRequest request, String message)
	{
		saveMessage(request, new ActionMessage(message, false));
	}

	protected HttpServletRequest currentRequest()
	{
		return WebContext.getCurrentRequest();
	}

	protected String getHelpId(ActionMapping mapping, ActionForm form)
	{
		return mapping.getPath();
	}

	/**
	 * Установить имя веб-метода, заменяющий планируемый метод выполения
	 * @param request запрос
	 * @param methodName метод для замены
	 */
	protected void setReplaceMethodName(HttpServletRequest request, String methodName)
	{
		request.setAttribute(REPLACE_METHOD_NAME, methodName);
	}

	/**
	 * @param request запрос
	 * @return имя веб-метода, заменяющий планируемый метод выполения
	 */
	protected String getReplaceMethodName(HttpServletRequest request)
	{
		return (String) request.getAttribute(REPLACE_METHOD_NAME);
	}

	/**
	 * Признак отображения полной страницы с ошибкой.
	 * @return true/false
	 */
	protected boolean useFullErrorPage()
	{
		return false;
	}
}
