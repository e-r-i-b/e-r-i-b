package com.rssl.phizic.web.security;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.mobileDevices.MobilePlatform;
import com.rssl.phizic.business.mobileDevices.MobilePlatformService;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.util.HttpSessionUtils;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Фильтр запрещенных мобильных браузеров.
 * Перенаправляет в зависимости от настройки com.rssl.iccs.users.browsers.unallowed.тип устройства=(список)
 * по указанным там User-Agent на страницу с сылкой на скачивание мобильного клиента.
 * @ author: Vagin
 * @ created: 28.11.2012
 * @ $Author
 * @ $Revision
 */
public class MobileBrowserFilter implements Filter
{
	protected static final org.apache.commons.logging.Log log = PhizICLogFactory.getLog(LogModule.Web);
	private String redirectUrl = null;
	private static Cache listCache = CacheProvider.getCache("unallowed-client-browser-cache");
	private static String cacheKey = "unallowedBrowsers";
	// ключ определяющий, что выбран вход в полную версию.
	// проверка на запрещенность браузера игнорируется при признаке true
	private static final String FULL_VERSION_KEY = "load.full_version";
	private static final MobilePlatformService mobilePlatformService = new MobilePlatformService();

	public void init(FilterConfig filterConfig) throws ServletException
	{
		redirectUrl = filterConfig.getInitParameter("redirectUrl");
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
	{
		checkBrowser(servletRequest, servletResponse);
		filterChain.doFilter(servletRequest, servletResponse);
	}

	public void destroy() {}

	private void checkBrowser(ServletRequest request, ServletResponse response) throws IOException
	{
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
		//параметр сессии, характеризующий игнорирование проверки на доступность мобильного прилжения
		Object isFullVersionNeed = HttpSessionUtils.getSessionAttribute(req, FULL_VERSION_KEY);
		if (isFullVersionNeed != null && (Boolean) isFullVersionNeed)
			return;
		String userAgent = req.getHeader("User-Agent");

		if (checkBrowser(userAgent))
			resp.sendRedirect(redirectUrl);
	}

	private List<String> getBrowsersRegExpList() throws BusinessException
	{
		Element element = listCache.get(cacheKey);
		if (element == null)
		{
			List<String> listUnallowed = new ArrayList<String>();
			List<MobilePlatform> mobilePlatforms = mobilePlatformService.getMobile();
			for (MobilePlatform mobilePlatform : mobilePlatforms)
			{
				String unallowed = mobilePlatform.getUnallowedBrowsers();
				if (!StringHelper.isEmpty(unallowed))
				{
					listUnallowed.add(unallowed);
				}
			}
			listCache.put(new Element(cacheKey, listUnallowed));
			return listUnallowed;
		}
		return (List) element.getObjectValue();
	}

	/**
	 * Соответтствие userAgenta браузера регулярному выражению настройки
	 * @param userAgent - userAgent
	 * @return true - есть спец. версия/false-нет.
	 */
	private boolean checkBrowser(String userAgent)
	{
		try
		{
			List<String> unallowedBrowsers = getBrowsersRegExpList();
			for (String regExp : unallowedBrowsers)
			{
				Pattern pattern = Pattern.compile(regExp);
				if(pattern.matcher(userAgent).matches())
					return true;
			}
			return false;
		}
		catch(Exception e)
		{
			log.error("Ошибка получения настроек списка запрещенных мобильных браузеров.",e);
			return false;
		}
	}
}
