package com.rssl.phizic.business.messaging.info;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Collections;
import java.util.List;

/**
 * @author Roshka
 * @ created 22.06.2006
 * @ $Author$
 * @ $Revision$
 */

public class SubscriptionService
{
	private SimpleService simpleService = new SimpleService();

	/**
	 * добавление/обновление записи подписки
	 * @param subscription - подписка
	 */
	public void addOrUpdate(final SubscriptionImpl subscription) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.saveOrUpdate(subscription);
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
	 * Удаление подписки
	 * @param subscription - подписка
	 */
	public void remove(final SubscriptionImpl subscription) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.delete(subscription);
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
	 * Поиск контактных данных клиента
	 * @param login - логин клиента
	 * @return контактные данные клиента
	 */
	public PersonalSubscriptionData findPersonalData(CommonLogin login) throws BusinessException
	{
		return simpleService.findById(PersonalSubscriptionData.class, login.getId());
	}

	/**
	 * Поиск контактных данных клиента
	 * @param loginId - id логина клиента
	 * @return контактные данные клиента
	 */
	public PersonalSubscriptionData findPersonalData(Long loginId) throws BusinessException
	{
		return simpleService.findById(PersonalSubscriptionData.class, loginId);
	}

	/**
	 * Изменение контактных данных клиента
	 * @param data - контактные данные клиента
	 */
	public void changePersonalData(PersonalSubscriptionData data) throws BusinessException
	{
		simpleService.addOrUpdate(data);
	}


	/**
	 * При удалении клиента отписываемся от всех его сообщений
	 * @param login - логин клиента
	 * @throws BusinessException
	 */
	public void unsubscribeSubscriptions(final CommonLogin login) throws BusinessException
	{
		try
	    {
		   HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		   {
			   public Void run(Session session) throws Exception
			   {
				   Query query = session.getNamedQuery("com.rssl.phizic.messaging.removeSubscriptions");
				   query.setParameter("loginId", login.getId());
				   query.executeUpdate();
				   return null;
			   }
		   });
	    }
	    catch(Exception ex)
	    {
			throw new BusinessException(ex);
	    }
	}

	/**
	 * Поиск подписок клиента
	 * @param loginId - id логина клиента
	 * @param types - типы оповещений
	 * @return - список подписок
	 */
	public List<SubscriptionImpl> findSubscriptions(final Long loginId, final List<UserNotificationType> types) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<SubscriptionImpl>>()
			{
				public List<SubscriptionImpl> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.messaging.findSubscriptions");
					query.setParameter("loginId", loginId);
					query.setParameterList("types", types);

					return query.list();
				}
			});
		}
		catch(Exception ex)
		{
			throw new BusinessException(ex);
		}
	}

	/**
	 * Поиск подписки клиента
	 * @param loginId - id логина клиента
	 * @param type - тип оповещения
	 * @return - подписка
	 */
	public SubscriptionImpl findSubscription(final Long loginId, final UserNotificationType type, final LockMode lockMode) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<SubscriptionImpl>()
			{
				public SubscriptionImpl run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.messaging.findSubscriptions");
					query.setParameter("loginId", loginId);
					query.setParameterList("types", Collections.singletonList(type));
					if (lockMode!= null)
						query.setLockMode("subscription", lockMode);

					return (SubscriptionImpl) query.uniqueResult();
				}
			});
		}
		catch(Exception ex)
		{
			throw new BusinessException(ex);
		}
	}
}