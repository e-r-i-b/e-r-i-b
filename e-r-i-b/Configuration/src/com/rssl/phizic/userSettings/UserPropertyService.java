package com.rssl.phizic.userSettings;

import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Session;

import java.util.List;
import java.util.Properties;

/**
 * ������ ��� ������ � ����������������� �����������.
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
	 * @return ������ �� ������ � ����������������� �����������.
	 */
	public static UserPropertyService getIt() {return it;}

	private UserPropertyService(){}

	/**
	 * �������� ��������.
	 *
	 * @param loginId �� ������.
	 * @return ������ �������� ������������.
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
			throw new ConfigurationException("������ �� ����� ��������� ���������������� ������", e);
		}
	}

	/**
	 * ������������� ���������.
	 *
	 * @param key ���� ���������.
	 * @param loginId ������������� ������.
	 * @param value �������� ���������.
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
			throw new ConfigurationException("������ �� ����� ���������� ��������", e);
		}
	}

	/**
	 * ������� ��������� �� ��.
	 *
	 * @param loginId ������������� ������.
	 * @param key ���� ���������.
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
			throw new ConfigurationException("������ �� ����� ���������� ��������", e);
		}
	}
}
