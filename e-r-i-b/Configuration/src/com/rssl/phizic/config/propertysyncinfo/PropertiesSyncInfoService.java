package com.rssl.phizic.config.propertysyncinfo;

import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.List;
import javax.naming.NameNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: tisov
 * Date: 20.10.14
 * Time: 18:07
 * Сервис для работы с информацией по синхронизации настроек
 */
public class PropertiesSyncInfoService
{

	private static final String CSAADMIN_INSTANCE_NAME = "CSAAdmin";

	private String getInstance()
	{
		return CSAADMIN_INSTANCE_NAME;
	}

	public void save(final PropertySyncInfo info) throws Exception
	{
		HibernateExecutor.getInstance(getInstance()).execute(new HibernateAction<PropertySyncInfo>()
		{
			public PropertySyncInfo run(Session session) throws Exception
			{
				session.save(info);
				return info;
			}
		}
		);
	}

	/**
	 * Получение информации о статусе репликации настроек
	 * @param guid - идентификатор операции репликации
	 * @param date - дата проведения репликации
	 */
	public List<Object[]> getInfoByGUID(final String guid, final Calendar date) throws Exception
	{
		return (List<Object[]>) HibernateExecutor.getInstance(getInstance()).execute(new HibernateAction<List>()
		{
			public List run(Session session)
			{
				String queryName = "com.rssl.phizic.config.propertysyncinfo.PropertySyncInfo.list";
				Query query = session.getNamedQuery(queryName);
				query.setParameter("guid", guid);
				query.setParameter("start_date", date);
				return query.list();
			}
		});
	}
}
