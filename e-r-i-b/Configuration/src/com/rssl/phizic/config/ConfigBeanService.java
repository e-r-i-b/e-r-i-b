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
	 * ���������� ������ ������� �� ����
	 * @param codename - ����������� �������
	 * @return ������ �������
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
			throw new ConfigurationException("�� ���� �� ������� ��������� ������ " + codename, e);
		}
	}

	/**
	 * ��������� ������ ������� � ����
	 * @param codename - ����������� �������
	 * @param configData - ������ �������
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
			throw new ConfigurationException("�� ���� �� ������� ��������� ������ " + codename, e);
		}
	}
}
