package com.rssl.phizic.userSettings;

import com.rssl.phizic.config.CompositePropertyReader;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.utils.StringHelper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Чтение настроек пользователя из базы.
 *
 * @author bogdanov
 * @ created 10.04.14
 * @ $Author$
 * @ $Revision$
 */

public class UserPropertyReader extends CompositePropertyReader
{
	/**
	 * Список настроек пользователя.
	 */
	private Cache userPropertiesCache;
	/**
	 * Идентификатор логина по умолчанию для текущей нити.
	 */
	private static final ThreadLocal<Long> predefinedLoginIds = new ThreadLocal<Long>();

	/**
	 * @param readers список ридеров.
	 */
	public UserPropertyReader(PropertyReader[] readers)
	{
		super(readers);

		String cacheName = getClass().getName();
		synchronized (UserPropertyReader.class)
		{
			userPropertiesCache = CacheManager.getInstance().getCache(cacheName);
			if (userPropertiesCache == null)
			{
				//noinspection MagicNumber
				userPropertiesCache = new Cache(cacheName, 500, true, false, 900, 900);
				CacheManager.getInstance().addCache(userPropertiesCache);
			}
		}
	}

	@Override
	protected Map<String, String> doRefresh()
	{
		return new HashMap<String, String>();
	}

	/**
	 * Работать с пользователем. Устанавливается из джобов и других мест, где необходимо получить конкретную настройку пользователя.
	 *
	 * @param loginId идентификатор логина пользователя.
	 */
	void workWithUser(Long loginId)
	{
		predefinedLoginIds.set(loginId);
	}

	/**
	 * Завершение работы с пользователем.
	 */
	void finishWorkWithUser()
	{
		predefinedLoginIds.remove();
	}

	private Long getLoginId()
	{
		if (predefinedLoginIds.get() != null)
			return predefinedLoginIds.get();

		if (PersonContext.isAvailable())
			return PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin().getId();

		throw new ConfigurationException("Не установены данные пользователя. Используйте processUserSettingsWithoutPersonContext.");
	}

	/**
	 * @return пользовательские настройки.
	 */
	private Properties getUserProperties()
	{
		Long loginId = getLoginId();
		if (!PersonContext.isAvailable())
		{
			{
				Element userPropertiesElement = userPropertiesCache.get(loginId);
				if (userPropertiesElement != null)
					return (Properties) userPropertiesElement.getValue();
			}

			//noinspection SynchronizationOnLocalVariableOrMethodParameter
			synchronized (loginId)
			{
				Element userPropertiesElement = userPropertiesCache.get(loginId);
				if (userPropertiesElement != null)
					return (Properties) userPropertiesElement.getValue();

				userPropertiesCache.put(new Element(loginId, UserPropertyService.getIt().loadSettings(loginId)));
				return (Properties) userPropertiesCache.get(loginId).getValue();
			}
		}
		else
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			if (personData.getUserProperties() != null)
				return personData.getUserProperties();

			personData.setUserProperties(UserPropertyService.getIt().loadSettings(loginId));
			return personData.getUserProperties();
		}
	}

	/**
	 * @param prefix префикс (подстрока, с которой начинаются именв свойств)
	 * @return список свойств
	 */
	public Properties getProperties(String prefix)
	{
		Properties prop = getUserProperties();

		Properties result = super.getProperties(prefix);
		for (Map.Entry entry : prop.entrySet())
		{
			String key = (String) entry.getKey();
			if (!key.startsWith(prefix))
				continue;

			result.setProperty(key, (String) entry.getValue());
		}

		return result;
	}

	/**
	 * @return все строковые настнойки из ридера
	 */
	public Properties getAllProperties()
	{
		Properties prop = getUserProperties();

		Properties result = super.getAllProperties();
		for (Map.Entry entry : prop.entrySet())
		{
			result.setProperty((String) entry.getKey(), (String) entry.getValue());
		}

		return result;
	}

	public String getProperty(String key, String defaultValue)
	{
		String value = getUserProperties().getProperty(key);
		if (StringHelper.isEmpty(value))
		{
			return super.getProperty(key, defaultValue);
		}
		else
		{
			return value.trim();
		}
	}

	/**
	 * Установка пользовательской настройки.
	 * @param key ключ.
	 * @param value значение.
	 */
	public void setProperty(String key, String value)
	{
		getUserProperties().setProperty(key, value);
		UserPropertyService.getIt().setSetting(getLoginId(), key, value);
	}

	/**
	 * Удаление пользовательской настройки.
	 * @param key ключ.
	 */
	public void removeProperty(String key)
	{
		getUserProperties().remove(key);
		UserPropertyService.getIt().removeSettigns(getLoginId(), key);
	}
}
