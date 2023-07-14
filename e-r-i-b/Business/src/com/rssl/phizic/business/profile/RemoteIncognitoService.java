package com.rssl.phizic.business.profile;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.MultiInstanceSimpleService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

/**
 * сервис для обслуживания удаленной таблицы инкогнито.
 *
 * @author bogdanov
 * @ created 08.10.14
 * @ $Author$
 * @ $Revision$
 */

public class RemoteIncognitoService
{
	private static final MultiInstanceSimpleService simpleService = new MultiInstanceSimpleService();
	private static final String CSA_ADMIN = "CSAAdmin";

	/**
	 * Добавляем телефны.
	 *
	 * @param phones телефоны.
	 * @throws BusinessException
	 */
	public void addPhones(final Collection<String> phones, final long nodeId) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(CSA_ADMIN).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					for (String phone : phones)
					{
					    IncognitoPhone ph = new IncognitoPhone();
						ph.setActive(true);
						ph.setLastUpdateTime(Calendar.getInstance());
						ph.setPhone(phone);
						ph.setNodeId(nodeId);
						simpleService.add(ph, CSA_ADMIN);
					}

					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Удаляем телефоны.
	 *
	 * @param phones телефоны.
	 * @throws BusinessException
	 */
	public void deletePhones(final Collection<String> phones, final long nodeId) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(CSA_ADMIN).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					for (String phone : phones)
					{
					    IncognitoPhone ph = new IncognitoPhone();
						ph.setActive(false);
						ph.setLastUpdateTime(Calendar.getInstance());
						ph.setPhone(phone);
						ph.setNodeId(nodeId);
						simpleService.add(ph, CSA_ADMIN);
					}

					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param startTime начальная дата.
	 * @return список изменений после даты.
	 * @throws BusinessException
	 */
	public List<IncognitoPhone> getDelta(final Calendar startTime, final int startRow, final int finishRow) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(CSA_ADMIN).execute(new HibernateAction<List<IncognitoPhone>>()
			{
				public List<IncognitoPhone> run(Session session) throws Exception
				{
					return session.getNamedQuery("com.rssl.phizic.business.profile.IncognitoPhone.getDelta")
							.setParameter("time", startTime)
							.setParameter("notNodeId", ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber())
							.setFirstResult(startRow)
							.setMaxResults(finishRow - startRow)
							.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
