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
	 * ����������/���������� ������ ��������
	 * @param subscription - ��������
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
	 * �������� ��������
	 * @param subscription - ��������
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
	 * ����� ���������� ������ �������
	 * @param login - ����� �������
	 * @return ���������� ������ �������
	 */
	public PersonalSubscriptionData findPersonalData(CommonLogin login) throws BusinessException
	{
		return simpleService.findById(PersonalSubscriptionData.class, login.getId());
	}

	/**
	 * ����� ���������� ������ �������
	 * @param loginId - id ������ �������
	 * @return ���������� ������ �������
	 */
	public PersonalSubscriptionData findPersonalData(Long loginId) throws BusinessException
	{
		return simpleService.findById(PersonalSubscriptionData.class, loginId);
	}

	/**
	 * ��������� ���������� ������ �������
	 * @param data - ���������� ������ �������
	 */
	public void changePersonalData(PersonalSubscriptionData data) throws BusinessException
	{
		simpleService.addOrUpdate(data);
	}


	/**
	 * ��� �������� ������� ������������ �� ���� ��� ���������
	 * @param login - ����� �������
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
	 * ����� �������� �������
	 * @param loginId - id ������ �������
	 * @param types - ���� ����������
	 * @return - ������ ��������
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
	 * ����� �������� �������
	 * @param loginId - id ������ �������
	 * @param type - ��� ����������
	 * @return - ��������
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