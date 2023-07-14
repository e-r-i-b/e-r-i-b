package com.rssl.phizicgate.mdm.business.profiles;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizicgate.mdm.business.MDMDatabaseServiceBase;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author akrenev
 * @ created 10.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Сервис работы с профилем
 */

public class ProfileService extends MDMDatabaseServiceBase
{
	/**
	 * получить идентификатор мдм по идентификатору профиля
	 * @param profileId идентификатор
	 * @return идентификатор мдм
	 * @throws GateException
	 */
	public String getMdmId(final Long profileId) throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance(getInstance()).execute(new HibernateAction<String>()
			{
				public String run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(ProfileService.class.getName() + ".findMdmId");
					query.setParameter("profileId", profileId);
					return (String) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Сохранить профиль мдм
	 * @param profile профиль мдм
	 * @return профиль мдм
	 * @throws GateException
	 */
	public Profile save(Profile profile) throws GateException
	{
		try
		{
			return databaseService.addOrUpdate(profile, getInstance());
		}
		catch (Exception e)
		{
			throw new GateException("Ошибка обновления профиля МДМ.", e);
		}
	}

	/**
	 * получить профиль по идентификатору мдм
	 * @param mdmId идентификатор
	 * @return профиль
	 * @throws GateException
	 */
	public Profile getProfile(final String mdmId) throws GateException
	{
		try
		{
			return executeAtomic(new HibernateAction<Profile>()
			{
				public Profile run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(ProfileService.class.getName() + ".findProfile");
					query.setParameter("mdmId", mdmId);
					return (Profile) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
