package com.rssl.phizic.web.util;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.pages.Page;
import com.rssl.phizic.business.dictionaries.pages.PageService;
import com.rssl.phizic.business.dictionaries.pages.messages.InformMessageService;
import com.rssl.phizic.business.dictionaries.pages.messages.client.temporary.TemporaryMessageConfig;
import com.rssl.phizic.common.types.csa.MigrationState;
import com.rssl.phizic.common.types.csa.ProfileType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.StringUtils;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.actions.LookupDispatchAction;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionMapping;

import java.util.*;
import javax.servlet.http.HttpServletRequest;

/**
 * Класс для работы с информационными сообщениями.
 * @author komarov
 * @ created 26.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class InformMessageUtils
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final InformMessageService informMessageService = new InformMessageService();
	private static final DepartmentService departmentService = new DepartmentService();
	private static final PageService pageService = new PageService();

	/**
	 * Получает список страниц через запятую, обрезает до нужной длины.
	 * @param pages страницы
	 * @param strLen длина
	 * @return список страниц через запятую
	 */
	public static String pagesToString(Set<Page> pages, int strLen)
	{
		StringBuffer str = new StringBuffer();
		Iterator<Page> iterator =  pages.iterator();
		if(iterator.hasNext())
		{
			Page page = iterator.next();
			str = str.append(page.getName());
		}

		for(; iterator.hasNext(); )
		{
			str.append(", ");
			str.append(iterator.next().getName());
			if(str.length() > strLen)
			{
				break;
			}
		}

		if(str.length() > strLen)
		{
			return StringUtils.safeTrunc(str.toString(), strLen) + "...";
		}
		return str.toString();
	}

	/**
	 * Получает список департаментов через запятую, обрезает до нужной длины.
	 * @param departments дкпартаменты
	 * @param strLen длина
	 * @return список департаментов через запятую
	 */
	public static String departmentsToString(Set<String> departments, int strLen)
	{
		StringBuffer str = new StringBuffer();
		Iterator<String> iterator =  departments.iterator();
		
		if(iterator.hasNext())
		{
			String department = iterator.next();
			str = str.append(DepartmentViewUtil.getDepartmentName(department, null, null));
		}

		for(; iterator.hasNext(); )
		{
			if(str.length() > 0)
				str.append(", ");
			str.append(DepartmentViewUtil.getDepartmentName(iterator.next(), null, null));
			if(str.length() > strLen)
			{
				break;
			}
		}
		if(str.length() > strLen)
		{
			return StringUtils.safeTrunc(str.toString(), strLen) + "...";
		}
		return str.toString();
	}

	/**
	 * Возвращает список сообщений для отображения пользователю
	 * @return сообщения
	 */
	public static List<String[]> getInformMessages()
	{
		HttpServletRequest request = WebContext.getCurrentRequest();
		//1. сначала пробуем наш атрибут
		ActionMapping mapping = (ActionMapping)request.getAttribute(LookupDispatchAction.MAPPING_ATTRIBUTE_NAME);
		//2. если не заполнен, то пробуем struts-овый атрибут
		if (mapping == null)
			mapping = (ActionMapping)request.getAttribute("org.apache.struts.action.mapping.instance");
		
		if (mapping == null)
		{
			log.error("ActionMapping отсутствует в атрибутах реквеста");
			return Collections.emptyList();
		}
        String url = mapping.getPath()+".do";

		try
		{
			Department tb =  DepartmentViewUtil.getCurrentTerbankFromContext();

			Map<String, String> parameters = new HashMap<String, String>();
			parameters.putAll(getRequestParameters(request));
			String formName = (String) request.getAttribute("paymentFormName");
			if (!StringHelper.isEmpty(formName))
				parameters.put("form",formName);

			List<Long> ids = pageService.findPagesByUrlAndParameters(url, parameters);
			if(CollectionUtils.isNotEmpty(ids) && tb!= null)
			{
				return informMessageService.getMessagesByUrlAndParameters(ids, tb.getRegion());
			}
			return Collections.emptyList();
		}
		catch(BusinessException be)
		{
			log.error("Ошибка поиска информационного сообщения", be);
			return Collections.emptyList();
		}		

	}

	/**
	 * Возвращает сообщение для пользователя
	 * @return сообщение
	 */
	public static String getClientMessage()
	{

		if(AuthenticationContext.getContext() ==  null)
			return null;

		ProfileType type = AuthenticationContext.getContext().getProfileType();
		MigrationState state = AuthenticationContext.getContext().getMigrationState();
		if(type != ProfileType.TEMPORARY && state != MigrationState.PROCESS)
			return null;

		HttpServletRequest request = WebContext.getCurrentRequest();
		//1. сначала пробуем наш атрибут
		ActionMapping mapping = (ActionMapping)request.getAttribute(LookupDispatchAction.MAPPING_ATTRIBUTE_NAME);
		//2. если не заполнен, то пробуем struts-овый атрибут
		if (mapping == null)
			mapping = (ActionMapping)request.getAttribute("org.apache.struts.action.mapping.instance");

		if (mapping == null)
		{
			log.error("ActionMapping отсутствует в атрибутах реквеста");
			return null;
		}

		return getMessage(type, mapping.getPath());
	}

	private static String getMessage(ProfileType type, String url)
	{

		if(type == ProfileType.TEMPORARY)
		{
			Set<String> pages =  ConfigFactory.getConfig(TemporaryMessageConfig.class).getPagesTemporary();
			if(CollectionUtils.isNotEmpty(pages) && pages.contains(url))
				return ConfigFactory.getConfig(TemporaryMessageConfig.class).getTemporaryMessage();
		}
		else
		{
			Set<String> pages =  ConfigFactory.getConfig(TemporaryMessageConfig.class).getPagesMigration();
			if(CollectionUtils.isNotEmpty(pages) && pages.contains(url))
				return ConfigFactory.getConfig(TemporaryMessageConfig.class).getMigrationMessage();
		}
		return null;
	}


	private static Map<String, String> getRequestParameters(HttpServletRequest request)
	{
		Map<String, String> parameters = new HashMap<String, String>();
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements())
		{
			String parameterKey   = parameterNames.nextElement();
			String parameterValue = request.getParameter(parameterKey);
			parameters.put(parameterKey, parameterValue);
		}
		return parameters;
	}
}
