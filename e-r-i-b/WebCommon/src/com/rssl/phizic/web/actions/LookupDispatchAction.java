/*
 * $Id: LookupDispatchAction.java 54929 2004-10-16 16:38:42Z germuska $
 *
 * Copyright 2001-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rssl.phizic.web.actions;

import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.context.Constants;
import com.rssl.phizic.context.RSAContext;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.web.security.PageTokenUtil;
import com.rssl.phizic.web.util.FraudMonitoringUtils;
import com.rssl.phizic.web.util.SkinHelper;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings({"AbstractClassExtendsConcreteClass"})
public abstract class LookupDispatchAction extends LoggableAction
{
	public static final String IS_EMPTY_ERROR_PAGE_ATTRIBUTE_NAME = "isEmptyErrorPage";
	public static final String IS_AJAX_ATTRIBUTE_NAME = "isAjax";
	public static final String IS_PART_ERROR_PAGE_ATTRIBUTE_NAME = "isPartErrorPage";
	//ключ дл€ атрибута риквеста, хран€щего экземпл€р ActionMapping-а
	public static final String MAPPING_ATTRIBUTE_NAME = "com.rssl.phizic.web.actions.LookupDispatchAction.mapping.instance";
	private static final String REQUEST_PARAMETER_NAME = "operation";
	private static final String SKIN_URL_CONTEXT_NAME = "skinUrl";

	/** Resource key to method name lookup. */
	private Map<String, String> keyMethodMap = null;

	private Object synkRoot = new Object();

	/**
	 * Provides the mapping from resource key to method name.
	 *
	 * @return Resource key / method name map.
	 */
	protected  Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	protected String getLookupMapName(HttpServletRequest request, String keyName, ActionMapping mapping) throws ServletException
	{
		synchronized (synkRoot)
		{
			if(keyMethodMap == null)
			{
				keyMethodMap = getKeyMethodMap();
			}	
		}

		// Find the method name
		String methodName = keyMethodMap.get(keyName);
		if (methodName == null)
		{
			String message = messages.getMessage( "dispatch.lookup", mapping.getPath(), keyName);
			throw new ServletException(message);
		}

		return methodName;
	}

	protected final String getDefaultMethodName()
	{
		return "start";
	}

	protected String getParameter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String requestParam = request.getParameter(REQUEST_PARAMETER_NAME);
		if ( requestParam != null)
				return requestParam;

		// дл€ мобильной версии в св€зи с тем, что мы используем submit, то название операций заложено в имени кнопки submit
		// пример название параметра будет operation.button.filter где button.filter название операции
		Pattern pattern = Pattern.compile(REQUEST_PARAMETER_NAME + "\\.(.*)");

		for (Enumeration params = request.getParameterNames(); params.hasMoreElements() ;) {
			Matcher matcher = pattern.matcher((String) params.nextElement());
			 if (matcher.matches())
				 return matcher.group(1); // возвращаем первый совпавший результат
        }

		return null;
	}

	protected ActionForward dispatchMethod(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String name) throws Exception
	{
		if (needSaveMapping())
			request.setAttribute(MAPPING_ATTRIBUTE_NAME, mapping);
		
		ActionForward forward = super.dispatchMethod(mapping, form, request, response, name);

//		accessService.checkForm(form);

		String helpId = getHelpId(mapping, form);
		if(!helpId.equals("/help"))
			request.setAttribute("$$helpId", StringHelper.encodeURL(helpId));

		String webPageHelpId = getWebPageHelpId(mapping, form);
		if(StringHelper.isNotEmpty(webPageHelpId))
			request.setAttribute("$$webPageHelpId", StringHelper.encodeURL(webPageHelpId));

		return forward;
	}

	protected final String getMethodName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String parameter)
			throws Exception
	{
															//провер€ем защищенность реквеста
		if (parameter == null || parameter.length() == 0 || !PageTokenUtil.isSecureRequest(request))
		{
			return getDefaultMethodName();
		}

		return getLookupMapName(request, parameter, mapping);
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			return super.execute(mapping, form, request, response);
		}
		finally
		{
			applyCurrentSkin(form, request);
			//”станавливаем вс€кий мусор дл€ корректной отрисовки страниц с ошибками.
			request.setAttribute(IS_EMPTY_ERROR_PAGE_ATTRIBUTE_NAME, getEmptyErrorPage());
			request.setAttribute(IS_AJAX_ATTRIBUTE_NAME, isAjax());
			request.setAttribute(IS_PART_ERROR_PAGE_ATTRIBUTE_NAME, getPartErrorPage());

			FraudMonitoringUtils.storeTokens();         //сохран€ем полученные от ‘ћ значени€ токенов в сессию
			FraudMonitoringUtils.storeCookie();         //обновл€ем значение cookie
		}
	}

	public abstract ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception;

	protected String getHelpId(ActionMapping mapping, ActionForm form) throws Exception
	{
		return  mapping.getPath();
	}

	protected String getWebPageHelpId(ActionMapping mapping, ActionForm form) throws Exception
	{
		return  getHelpId(mapping, form);
	}

	/**
	 * Ќужно ли сохран€ть инстанс ActionMapping-а в атрибуте request-а
	 * @return true - сохран€ем
	 */
	protected boolean needSaveMapping()
	{
		return true;
	}

	protected void saveErrors(HttpServletRequest request, ActionMessages errors)
	{
		ActionMessageHelper.saveErrors(request, errors);
	}

	protected void clearErrors(HttpServletRequest request)
	{
		request.removeAttribute(Globals.ERROR_KEY);
	}

	protected void saveErrors(HttpSession session, ActionMessages errors)
	{
		ActionMessageHelper.saveErrors(session, errors);
	}

	protected void clearErrors(HttpSession session)
	{
		session.removeAttribute(Globals.ERROR_KEY);
	}

	protected void saveMessages(HttpServletRequest request, ActionMessages messages)
	{
		if (messages == null || messages.isEmpty())
			return;

		ActionMessages allMessages = (ActionMessages) request.getAttribute(Globals.MESSAGE_KEY);
		if (allMessages != null)
			allMessages.add(messages);
		else allMessages = messages;

		request.setAttribute(Globals.MESSAGE_KEY, allMessages);
	}

	protected void clearMessages(HttpServletRequest request)
	{
		request.removeAttribute(Globals.MESSAGE_KEY);
	}

	protected void saveMessages(HttpSession session, ActionMessages messages)
	{
		if (messages == null || messages.isEmpty())
			return;

		ActionMessages allMessages = (ActionMessages) session.getAttribute(Globals.MESSAGE_KEY);
		if (allMessages != null)
			allMessages.add(messages);
		else allMessages = messages;

		session.setAttribute(Globals.MESSAGE_KEY, allMessages);
	}

	private void applyCurrentSkin(ActionForm form, HttpServletRequest request)
	{
		try
		{
			request.setAttribute("globalUrl", getGlobalUrl());
			request.setAttribute("skinUrl", getSkinUrl(form));
		}
		catch (BusinessException e)
		{
			log.error("—бой при установке скина", e);
		}
	}

	private String getGlobalUrl() throws BusinessException
	{
		return SkinHelper.getGlobalSkinUrl();
	}

	protected String getSkinUrl(ActionForm form) throws BusinessException
	{
        ServletContext servletContext = getServlet().getServletContext();
        String specialUrl = servletContext.getInitParameter(SKIN_URL_CONTEXT_NAME);
        if (!StringHelper.isEmpty(specialUrl))
	        return specialUrl;
		return SkinHelper.getSkinUrl();
	}

	/**
	 * ѕолучить признак форварда на пустую страницу в случае возникновени€ Exception
	 * @return признак форварда на пустую страницу при ошибке.
	 */
	protected boolean getEmptyErrorPage()
	{
		return false;
	}

	/**
	 * ѕолучить признак форварда на неполную страницу (без меню) в случае возникновени€ исключени€
	 * @return признак форварда на неполную страницу при ошибке
	 */
	private boolean getPartErrorPage()
	{
		Store store = StoreManager.getCurrentStore();
		if (store != null)
		{
			UserVisitingMode visitingMode = ConfirmationManager.getUserVisitingMode();
			return visitingMode != null && visitingMode != UserVisitingMode.BASIC;
		}
		return false;
	}

	/**
	 * явл€етс€ ли action ajax-запросом.
	 * ѕо умолчанию не €вл€етс€. ¬ ajax action-ах переопредел€ть метод и возвращать true.
	 * @return true - да, false - нет
	 */
	protected boolean isAjax()
	{
		return false;
	}
}
