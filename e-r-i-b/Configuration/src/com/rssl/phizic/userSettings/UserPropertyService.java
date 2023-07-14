package com.rssl.phizic.userSettings;

import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Session;

import java.util.List;
import java.util.Properties;

/**
 * Сервис для работы с пользовательскими настройками.
 *
 * @author bogdanov
 * @ created 10.04.14
 * @ $Author$
 * @ $Revision$
 */

public class UserPropertyService
{
	private static final UserPropertyService it = new UserPropertyService();

	/**
	 * @return сервис по работе с пользовательскими настройками.
	 */
	public static UserPropertyService getIt() {return it;}

	private UserPropertyService(){}

	/**
	 * Загрузка настроек.
	 *
	 * @param loginId ид логина.
	 * @return список настроек пользователя.
	 * @throws ConfigurationException
	 */
	public Properties loadSettings(final Long loginId) throws ConfigurationException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Properties>()
			{
				public Properties run(Session session) throws Exception
				{
					Properties prop = new Properties();
					List<UserProperty> list = session.getNamedQuery("com.rssl.phizic.userSettings.UserProperty.getAll")
							.setParameter("loginId", loginId)
							.list();
					for (UserProperty p : list)
					{
						prop.put(p.getKey(), p.getValue());
					}
					return prop;
				}
			});
		}
		catch (Exception e)
		{
			throw new ConfigurationException("Ошибка во время получения пользовательских данных", e);
		}
	}

	/**
	 * Устанавливает настройку.
	 *
	 * @param key ключ настройки.
	 * @param loginId идентификатор логина.
	 * @param value значение настройки.
	 * @throws ConfigurationException
	 */
	public void setSetting(final Long loginId, final String key, final String value) throws ConfigurationException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					UserProperty prop = (UserProperty) session.getNamedQuery("com.rssl.phizic.userSettings.UserProperty.get")
							.setParameter("loginId", loginId)
							.setParameter("key", key)
							.uniqueResult();

					if (prop == null)
					{
						prop = new UserProperty();
						prop.setLoginId(loginId);
						prop.setKey(key);
					}
					prop.setValue(value);
					session.saveOrUpdate(prop);

					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new ConfigurationException("Ошибка во время обновления настроек", e);
		}
	}

	/**
	 * Удаляет настройку из бд.
	 *
	 * @param loginId идентификатор логина.
	 * @param key ключ настройки.
	 * @throws ConfigurationException
	 */
	public void removeSettigns(final Long loginId, final String key) throws ConfigurationException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.getNamedQuery("com.rssl.phizic.userSettings.UserProperty.remove")
							.setParameter("loginId", loginId)
							.setParameter("key", key)
							.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new ConfigurationException("Ошибка во время обновления настроек", e);
		}
	}
}
