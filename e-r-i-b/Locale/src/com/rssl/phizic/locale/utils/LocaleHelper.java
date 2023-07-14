package com.rssl.phizic.locale.utils;

import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.locale.ERIBLocaleConfig;
import com.rssl.phizic.config.locale.MultiLocaleContext;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.locale.entities.LocaleState;
import com.rssl.phizic.locale.services.MultiInstanceEribLocaleService;
import com.rssl.phizic.utils.StringHelper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ������ ��� ������ � �������������� �����������
 * @author koptyaev
 * @ created 15.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class LocaleHelper
{
	private static final String LOCALES_CACHE_KEY = "allLocales";
	private static final MultiInstanceEribLocaleService localesService = new MultiInstanceEribLocaleService();
	private static Cache localesCache;

	static
	{
		localesCache = CacheProvider.getCache("locales-cache");
	}

	/**
	 * �������� �� ������ ���������
	 * @param localeId ������������� ������
	 * @return true - ������ �������� ���������, false - ��������������
	 */
	public static boolean isDefaultLocale(String localeId)
	{
		return StringHelper.isEmpty(localeId) ||StringHelper.equals(localeId, ConfigFactory.getConfig(ERIBLocaleConfig.class).getDefaultLocaleId());
	}

	/**
	 * ���������� ������
	 * @param localeId ������������� ������
	 * @return ������
	 */
	public static ERIBLocale getLocale(String localeId)
	{
		List<ERIBLocale> locales = getAllLocales();
		for (ERIBLocale locale : locales)
		{
			if (locale.getId().equals(localeId))
				return locale;
		}
		return null;
	}

	/**
	 * ���������� ������ ������������
	 * @return ������
	 */
	public static ERIBLocale getUserLocale()
	{
		ERIBLocale locale = getLocale(MultiLocaleContext.getLocaleId());
		return locale == null? getDefaultLocale(): locale;
	}

	/**
	 * @return ������ �� ���������
	 */
	public static ERIBLocale getDefaultLocale()
	{
		ERIBLocaleConfig config = ConfigFactory.getConfig(ERIBLocaleConfig.class);
		return getLocale(config.getDefaultLocaleId());
	}

	/**
	 * @return ������ ������� ������� ��� ���������
	 */
	public static List<ERIBLocale> getLocalesWithoutDefault()
	{
		final String localeId = ConfigFactory.getConfig(ERIBLocaleConfig.class).getDefaultLocaleId();
		List<ERIBLocale> allLocales = getAllLocales();
		List<ERIBLocale> result = new ArrayList<ERIBLocale>();

		for (ERIBLocale locale : allLocales)
		{
			if (!locale.getId().equals(localeId))
			{
				result.add(locale);
			}
		}
		return result;
	}

	/**
	 * @return ������ ��������� ������� �������
	 */
	public static List<ERIBLocale> getAvailableLocales()
	{
		List<ERIBLocale> allLocales = getAllLocales();
		List<ERIBLocale> result = new ArrayList<ERIBLocale>();

		for (ERIBLocale locale : allLocales)
		{
			if (locale.getState() == LocaleState.ENABLED)
			{
				result.add(locale);
			}
		}
		return result;
	}

	/**
	 * @return ������ ��������� ������� ������� ��� ������� (��������� ��������)
	 */
	public static List<ERIBLocale> getAvailableLocalesWithoutCurrent()
	{
		String localeId = getCurrentLocale();
		List<ERIBLocale> allLocales = getAllLocales();
		List<ERIBLocale> result = new ArrayList<ERIBLocale>();

		for (ERIBLocale locale : allLocales)
		{
			if (!locale.getId().equals(localeId) && locale.getState() == LocaleState.ENABLED)
			{
				result.add(locale);
			}
		}
		return result;
	}

	/**
	 * @return ������ ���� ������� �������
	 */
	public static List<ERIBLocale> getAllLocales()
	{
		try
		{
			Element cachedList = localesCache.get(LOCALES_CACHE_KEY);

			if (cachedList == null)
			{
				List<ERIBLocale> locales = localesService.getAll(getInstanceName());
				localesCache.put(new Element(LOCALES_CACHE_KEY, locales));
				return locales;
			}
			return (List<ERIBLocale>)cachedList.getObjectValue();
		}
		catch (Exception ignore)
		{
			return Collections.emptyList();
		}
	}

	public static String getCurrentLocale()
	{
		return MultiLocaleContext.getLocaleId();
	}

	private static String getInstanceName()
	{
		return StringHelper.getNullIfEmpty(ConfigFactory.getConfig(ERIBLocaleConfig.class).getDbInstanceName());
	}
}
