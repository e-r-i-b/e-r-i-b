package com.rssl.auth.csa.front.business.regions;

import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.phizic.authgate.csa.CSARefreshConfig;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.config.CSAFrontConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author komarov
 * @ created 22.03.2013 
 * @ $Author$
 * @ $Revision$
 */

public class RegionHelper
{
	public static final  String SHOW_DEFAULT_REGION_REQUEST       = "$$show_default_region";
	private static final String SHOW_REGION_FUNCTIONALITY         = "$$show_region_functionality";
	private static final String REGION_COOKIE_NAME                = "region_cookie";
	private static final String REGION_SESSION_DEFAULT            = "session_region";
	private static final String REGION_SESSION_SELECTED           = "session_region_selected";

	private static final String ATTRIBUTE_REFERER_NAME = "Referer";

	private static final int COOKIE_LIFE_TIME_COEF = 60*60*24;   //время жизни куки установленной в респонс задаётся в секундах
	private static final String ALL_REGION_CODE    = "-1";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final RegionCSAService service   = new RegionCSAService();

	private static final Cache regionCache;

	static
	{
		regionCache = CacheProvider.getCache("region-by-name-csa-cache");
	}

	/**
	 * Получить информацию по региону из реквеста и отобразить окно выбора региона
	 * @param request реквест
	 * @param store хранилище
	 * @return регион
	 */
	private static Region getNewRegionInformation(HttpServletRequest request, Store store)
	{
		store.save(SHOW_DEFAULT_REGION_REQUEST, true);
		// Ищем наименование региона в параметре «Referer»
		String regionEnName = findRegionNameByPrefix(request.getHeader(ATTRIBUTE_REFERER_NAME), ConfigFactory.getConfig(CSARefreshConfig.class).getSberbankWebsitePrefix());
		if(StringHelper.isNotEmpty(regionEnName))
		{
			// Ищем регион в справочнике по названию на латинице
			Region dictionaryRegion = findRegionByEnName(regionEnName);
			// Если нашли такой регион, то проставляем его начальным значением для клиента
			if (dictionaryRegion != null)
			{
				return dictionaryRegion;
			}
		}
		return createAllRegion();
	}

	/**
	 * Определяет наименование региона через анализ значения параметра «Referer» - как текст после префикса перехода с сайта банка и до символа «/»(слеш)
	 * @param fullString - исходная строка
	 * @param prefix - префикс
	 * @return наименование региона
	 */
	private static String findRegionNameByPrefix(String fullString, String prefix)
	{
		if (StringHelper.isEmpty(fullString))
			return null;

		//Проверяем совпадает ли префикс (префикс - url сайта сбербанка)
		int prefixPos = fullString.indexOf(prefix);
		if (prefixPos != 0)
			return null;

		//Название региона находится после префикса и до следующего символа "/"
		String regionName = fullString.substring(prefix.length(), fullString.length());
		regionName = regionName.substring(0, regionName.indexOf("/"));
		return regionName;
	}

	/**
	 * Ищет регион в справочнике по названию на латинице
	 * @param regionEnName - название на латинице
	 * @return регион из справочника
	 */
	private static Region findRegionByEnName(String regionEnName)
	{
		Element element = regionCache.get(regionEnName);
		if(element == null)
		{
			try
			{
				Region region = service.findParentByEnName(regionEnName);
				regionCache.put(new Element(regionEnName, region));
				return region;
			}
			catch (FrontException fe)
			{
				log.error("Ошибка поиска региона пользователя", fe);
				return null;
			}
		}
		return (Region)element.getObjectValue();
	}

	/**
	 * Возвращает название региона.
	 * @param request запрос
	 */
	private static void saveRegionToSession(HttpServletRequest request)
	{
		Store store = StoreManager.getCurrentStore();
		if(store == null)
			return;
		Region region = (Region)store.restore(REGION_SESSION_DEFAULT);
		if (region != null)
			return;
		Cookie cookie = getCookie(request, REGION_COOKIE_NAME);
		if (cookie != null)
			region = getRegionByCookie(cookie.getValue());
		if (region == null)
			region = getNewRegionInformation(request,store);
		store.save(REGION_SESSION_DEFAULT,region);
	}

	private static Region getRegionByCookie(String cookie)
	{
		try
		{
			UserRegion uRegion = service.findByCookie(cookie);
			if (uRegion == null)
				return null;
			if(RegionHelper.ALL_REGION_CODE.equals(uRegion.getCode()))
				return createAllRegion();
			else
				return service.getRegionByCode(uRegion.getCode());
		}
		catch (FrontException fe)
		{
		    log.error("Ошибка получения данных по региону пользователя", fe);
		}
		return null;
	}

	/**
	 * Устанавливает регион в сессии
	 * @param request запрос
	 */
	public static void setRegion(HttpServletRequest request)
	{
		request.setAttribute(SHOW_REGION_FUNCTIONALITY, isRegionFunctionalityOn()&&isFullRegionMode());
		if(!isRegionFunctionalityOn()||!isFullRegionMode())
			return;
		saveRegionToSession(request);
	}

	/**
	 * Устанавливает регион пользователя при выборе из всплывающего окна
	 * @param region регион
	 */
	public static void updateRegion(Region region)
	{
		if(!isRegionFunctionalityOn())
			return;
		Store store = StoreManager.getCurrentStore();
		if(store == null)
			return;
		store.remove(SHOW_DEFAULT_REGION_REQUEST);
		store.save(REGION_SESSION_SELECTED, region);
		store.save(REGION_SESSION_DEFAULT, region);
	}

	/**
	 * Сохранение данных о регионе пользователя.
	 * @param profileId идентификатор профиля
	 */
	public static void saveUserRegion(Long profileId)
	{
		if(!isRegionFunctionalityOn())
			return;

		Store store = StoreManager.getCurrentStore();
		if(store == null)
			return;
		try
		{
			UserRegion userRegion = service.findByUserId(profileId);
			Region region = (Region)store.restore(RegionHelper.REGION_SESSION_SELECTED);
			String regionKey = region==null?null:(String)region.getSynchKey();
			if(userRegion == null)
				userRegion = createNewUserRegion(profileId,regionKey);
			else if (StringHelper.isNotEmpty(regionKey))
				userRegion = updateUserRegion(userRegion, regionKey);
			store.save(RegionHelper.REGION_COOKIE_NAME, userRegion.getCookie());
		}
		catch (FrontException fe)
		{
			log.error("Ошибка получения информации о регионе пользователя", fe);
		}
	}

	private static UserRegion createNewUserRegion(Long profileId, String regionCode)  throws FrontException
	{
		UserRegion region = new UserRegion(new RandomGUID().getStringValue());
		region.setProfileId(profileId);
		region.setCode(regionCode);
		return service.addUserRegion(region);
	}

	private static UserRegion updateUserRegion(UserRegion region, String regionCode) throws FrontException
	{
		if (StringHelper.equals(region.getCode(), regionCode))
			return region;
		region.setCode(regionCode);
		return service.updateUserRegion(region);
	}

	/**
	 * Ищет куку с задланным именем
	 * @param request запрос
	 * @param name имя куки
	 * @return кука
	 */
	private static Cookie getCookie(HttpServletRequest request, String name)
	{
		if(name == null)
			return null;

		if(request.getCookies() != null)
		{
			for(Cookie cookie : request.getCookies())
			{
				if(name.equals(cookie.getName()))
					return cookie;
			}
		}
		return null;
	}

	/**
	 * @return время жизни куки в днях
	 */
	private static int getCookieLifeTime()
	{
		return ConfigFactory.getConfig(CSARefreshConfig.class).getCookieLifeTime();
	}

	/**
	 * @return включен ли функционал выбора региона.
	 */
	private static boolean isRegionFunctionalityOn()
	{
		return ConfigFactory.getConfig(CSARefreshConfig.class).getRegionSelectionFunctionalityMode();
	}

	private static boolean isFullRegionMode()
	{
		CSAFrontConfig config = ConfigFactory.getConfig(CSAFrontConfig.class);
		return config.isChooseRegionMode();
	}

	/**
	 * Возвращает тербанк региона
	 * @return тб
	 */
	public static String getRegionTB()
	{
		if(!isFullRegionMode() || !isRegionFunctionalityOn())
			return null;

		Store store = StoreManager.getCurrentStore();

		if(store == null)
			return null;

		Region storedRegion = (Region)store.restore(REGION_SESSION_DEFAULT);
		if (storedRegion == null)
			return null;
		return storedRegion.getCodeTB();
	}

	/**
	 * @return все регионы
	 */
	public static Region createAllRegion()
	{
		Region region = new Region();
		region.setId(-1L);
		region.setSynchKey(ALL_REGION_CODE);
		region.setName("Все регионы");
		return region;
	}

	/**
	 * Сохранение кода региона из сессии в куки
	 * @param response - ответ
	 */
	public static void saveRequestRegionToCookie(HttpServletResponse response)
	{
		if(!isRegionFunctionalityOn())
			return;
		Store store = StoreManager.getCurrentStore();
		if( store!= null )
		{
			String cookie = (String)store.restore(RegionHelper.REGION_COOKIE_NAME);
			if (StringHelper.isEmpty(cookie))
				return;
			Cookie regionCookie = new Cookie(REGION_COOKIE_NAME, cookie);
			regionCookie.setMaxAge(COOKIE_LIFE_TIME_COEF * getCookieLifeTime());
			response.addCookie(regionCookie);
			store.remove(RegionHelper.REGION_COOKIE_NAME);
			store.remove(RegionHelper.REGION_SESSION_DEFAULT); //зачистим за собой
			store.remove(RegionHelper.REGION_SESSION_SELECTED); //потому что кто-то не инвалидирует сессию
		}
	}
}
