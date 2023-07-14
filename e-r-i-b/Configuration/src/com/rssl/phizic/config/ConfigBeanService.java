package com.rssl.phizic.config;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Erkin
 * @ created 28.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class ConfigBeanService
{
	/**
	 * Возвращает данные конфига из базы
	 * @param codename - кодификатор конфига
	 * @return данные конфига
	 */
	public String getConfig(final String codename) throws ConfigurationException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<String>()
			{
				public String run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.phizic.config.ConfigBean.getDataByCodename");
					query.setParameter("codename", codename);
					return (String) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new ConfigurationException("Из базы не удалось загрузить конфиг " + codename, e);
		}
	}

	/**
	 * Сохраняет данные конфига в базу
	 * @param codename - кодификатор конфига
	 * @param configData - данные конфига
	 */
	public void saveConfig(final String codename, final String configData) throws ConfigurationException
	{
		final ConfigBean configBean = new ConfigBean();
		configBean.setCodename(codename);
		configBean.setData(configData);

		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session)
				{
					session.saveOrUpdate(configBean);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new ConfigurationException("Из базы не удалось загрузить конфиг " + codename, e);
		}
	}
}
