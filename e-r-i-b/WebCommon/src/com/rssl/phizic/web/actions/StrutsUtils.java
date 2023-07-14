package com.rssl.phizic.web.actions;

import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.common.messages.MessageConfigRouter;
import com.rssl.phizic.web.servlet.jsp.WebPageContext;
import com.rssl.phizic.web.util.HttpSessionUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.MessageResources;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Evgrafov
 * @ created 17.01.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 1860 $
 */

public final class StrutsUtils
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);
	private static final String SPLITER = " |,|;";

	public static Object currentForm(PageContext pageContext)
	{
		try
		{
			return TagUtils.getInstance().lookup(pageContext, Constants.BEAN_KEY, null);
		}
		catch (Exception e)
		{
			log.error("Ошибка получения текущей формы", e);
			return null;
		}
	}

	/**
	 * @param pageContext page context
	 * @param action struts action
	 * @return URL
	 */
	public static final String calculateActionURL(PageContext pageContext, String action)
	{
		try
		{
			// Переопределяем contextPath в ссылках
			WebPageContext webPageContext = WebPageContext.create(pageContext, null);
			return TagUtils.getInstance().computeURL(webPageContext, null, null, null, action, null, null, null, false);
		}
		catch (Exception e)
		{
			log.error("Ошибка определения URL по page context и action", e);
			return "";
		}
	}

	/**
	 * @param pageContext page context
	 * @param action struts action
	 * @return URL
	 */
	public static final String calculateActionURLToPhizIC(PageContext pageContext, String action)
	{
		try
		{
			// Переопределяем contextPath в ссылках
			WebPageContext webPageContext = WebPageContext.create(pageContext, "application");
			return TagUtils.getInstance().computeURL(webPageContext, null, null, null, action, null, null, null, false);
		}
		catch (Exception e)
		{
			log.error("Ошибка определения URL по page context и action", e);
			return "";
		}
	}

	public static final String simpleCalculateActionURL(PageContext pageContext, String action)
	{
		try
		{
			return TagUtils.getInstance().computeURL(pageContext, null, null, null, action, null, null, null, false);
		}
		catch (Exception e)
		{
			log.error("Ошибка определения URL по page context и action", e);
			return "";
		}
	}

	//возвращает URL с использованием anchor
	public static final String calculateActionURLWithAnchor(PageContext pageContext, String action, String anchor)
	{
		try
		{
			// Переопределяем contextPath в ссылках
			WebPageContext webPageContext = WebPageContext.create(pageContext, null);
			return TagUtils.getInstance().computeURL(webPageContext, null, null, null, action, null, null, anchor, false);
		}
		catch (Exception e)
		{
			log.error("Ошибка определения URL по page context, action и anchor", e);
			return "";
		}
	}

	public static Object getFormProperty(ActionForm form, String propertyName)
	{
		try
		{
			return PropertyUtils.getSimpleProperty(form, propertyName);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public static List<Long> parseIds(String[] stringIds)
	{
		List<Long> result = new ArrayList<Long>();
		for (String s : stringIds)
		{
			result.add(Long.valueOf(s));
		}
		return result;
	}

	/**
	 * Преобразуем строку в список
	 * @param stringIds строка
	 * @return список
	 */
	public static List<Long> parseIds(String stringIds)
	{
		return parseIds(stringIds.split(SPLITER));
	}

	public static Long parseId(String[] stringIds)
	{
		if(stringIds.length != 1)
			return null;
		return Long.valueOf(stringIds[0]);
	}

	public static String loginContextName()
	{
		return ApplicationInfo.getCurrentApplication().name();
	}

	/**
	 * Поиск ресурса в переданном bundl'e по ключу
	 * @param key - ключ ресурса для поиска
	 * @param bundle - bundle в которым ищется сообщение
	 * @param values - массив дополнительных аргументов, которые вставляются в сообщение
	 * @return Значение ресурса или ключ, если ресурс не найден
	 */
	public static String getMessage(String key, String bundle, Object... values)
	{
		String message = MessageConfigRouter.getInstance().message(bundle, key, values);
		return StringHelper.isEmpty(message) ? key : message;		
	}

	/**
	 * Поиск токена текущей транзакции
	 * @return токен (can be null)
	 * @deprecated
	 */
	@Deprecated
	public static String getCurrentTransactionToken()
	{
		HttpServletRequest request = WebContext.getCurrentRequest();
		if (request != null)
			return HttpSessionUtils.getSessionAttribute(request, Globals.TRANSACTION_TOKEN_KEY);
		return null;
	}

	/**
	 * Удаление атрибута сессии по ключу
	 * @param key - ключ атрибута
	 * @return значение удалённого атрибута
	 */
	public static String removeSessionAttribute(String key)
	{
		return HttpSessionUtils.removeSessionAttribute(WebContext.getCurrentRequest(), key);
	}

	/**
	 * Создать переход по id сущности
	 * @param url урл
	 * @param id идентификатор сущности
	 * @return переход
	 */
	public static ActionForward createDefaultByIdForward(String url, Long id)
	{
		UrlBuilder urlBuilder = new UrlBuilder(url);
		urlBuilder.addParameter("id", StringHelper.getEmptyIfNull(id));
		return new ActionForward(urlBuilder.getUrl());
	}
}
