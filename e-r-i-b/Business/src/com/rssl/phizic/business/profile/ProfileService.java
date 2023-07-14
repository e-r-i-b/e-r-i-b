package com.rssl.phizic.business.profile;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.persons.PersonSocialID;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import static com.rssl.phizic.logging.Constants.LOG_MODULE_CORE;

import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.Person;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gulov
 * @ created 27.05.2010
 * @ $Authors$
 * @ $Revision$
 */
public class ProfileService
{
	private static final Log log = PhizICLogFactory.getLog(LOG_MODULE_CORE);
	private static final SimpleService simpleService = new SimpleService();

	public Profile findById(Long id) throws BusinessException
	{
		return simpleService.findById(Profile.class, id);
	}

	public Profile add(Profile profile) throws BusinessException
	{
		return simpleService.add(profile, null);
	}

	public Profile update(Profile profile) throws BusinessException
	{
		return simpleService.update(profile, null);
	}

	public void remove(Profile profile, String instanceName) throws BusinessException
	{
		simpleService.remove(profile, instanceName);
	}

	public Profile findByLogin(final CommonLogin login) throws BusinessException
	{
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance(null);

			return trnExecutor.execute(new HibernateAction<Profile>()
			{
				public Profile run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.profile.findByLoginId");
					query.setParameter("loginId", login.getId());
					
					return (Profile) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}

	}

	/**
	 * Обновить значение мобильного кошелька
	 * @param profile профиль
	 * @param money сумма
	 * @throws BusinessException
	 */
	public void updateMobileWallet(Profile profile, Money money) throws BusinessException
	{
		Session session = null;
		Transaction transaction = null;

		try
		{
			//noinspection HibernateResourceOpenedButNotSafelyClosed
			session = HibernateExecutor.getSessionFactory().openSession();
			transaction = session.beginTransaction();

			//блокируем использование логина
			CommonLogin login = simpleService.findById(CommonLogin.class, profile.getLoginId());
			session.lock(login, LockMode.UPGRADE);
			//обнуляем кошелек клиента
			profile.setMobileWallet(money);
			session.update(profile);

			transaction.commit();
		}
		catch (Exception e)
		{
			if (transaction != null)
			{
				log.error(e.getMessage(), e);
				transaction.rollback();
			}
			throw new BusinessException(e);
		}
		finally
		{
			if (session != null)
			{
				session.close();
			}
		}
	}

	/**
	 * @param login логин клиента.
	 * @return список идентификаторов социальной сети.
	 */
	public Map<String, PersonSocialID> getSocialIds(final CommonLogin login) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(null).execute(new HibernateAction<Map<String, PersonSocialID>>()
			{
				public Map<String, PersonSocialID> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.profile.ProfileService.getClientSocialIds");
					query.setParameter("loginId", login.getId());

					List<PersonSocialID> list = query.list();
					Map<String, PersonSocialID> ids = new HashMap<String, PersonSocialID>();
					for (PersonSocialID id : list)
					{
						ids.put(id.getSocialNetworkType(), id);
					}

					return ids;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param login логин клиента.
	 * @return список идентификаторов социальной сети.
	 */
	public void setSocialId(final CommonLogin login, String socialNetworkType, String socialNetworkId) throws BusinessException
	{
		Map<String, PersonSocialID> socialIds = getSocialIds(login);
		PersonSocialID personSocialID;
		if (socialIds.containsKey(socialNetworkType))
		{
			personSocialID = socialIds.get(socialNetworkType);
			personSocialID.setSocialNetworkId(socialNetworkId);
		}
		else
		{
			personSocialID = new PersonSocialID(socialNetworkType, socialNetworkId, login.getId());
		}
		simpleService.addOrUpdate(personSocialID);
	}

	/**
	 * Удаление аватара по логину
	 * @param loginId - идентификатор логина
	 * @throws BusinessException
	 */
	public void deleteAvatarInfoByLoginId(final long loginId) throws BusinessException
	{
		try
		{
			ExecutorQuery executorQuery = new ExecutorQuery(HibernateExecutor.getInstance(), "com.rssl.phizic.business.profile.deleteAvatarInfoByLoginId");
			executorQuery.setParameter("loginId", loginId).executeUpdate();
		}
		catch (DataAccessException e)
		{
			throw new BusinessException(e);
		}
	}
}
