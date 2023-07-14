package com.rssl.phizic.business.commission;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Session;
import org.hibernate.Query;

import java.util.List;
import java.util.Collection;

/**
 * @author Evgrafov
 * @ created 11.09.2007
 * @ $Author: Evgrafov $
 * @ $Revision: 4951 $
 */

public class CommissionService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * ��������� (����� ��� ��� ������������)
	 * @param type
	 * @throws BusinessException
	 */
	public void saveType(CommissionType type) throws BusinessException
	{
		simpleService.add(type);
	}

	/**
	 * �������� ������� ���������� ������� ��� ��������� ����
	 * @param type ��� ��������
	 * @param rules ������� (������ ����� �������, ���������� ������������ �� ��������������!)
	 * @throws BusinessException
	 */
	public void updateRules(final CommissionType type, final Collection<? extends CommissionRule> rules) throws BusinessException
	{
		try
		{
		    HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		    {
		        public Void run(Session session) throws Exception
		        {
			        Query query = session.getNamedQuery("com.rssl.phizic.business.commission.CommissionService.removeByType");
			        query.setParameter("type", type).executeUpdate();

			        for (CommissionRule rule : rules)
			        {
				        rule.setType(type);
				        session.saveOrUpdate(rule);
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
	 * ����� ������� ������� �������� ��� ��������� ���� �������� � ������
	 * @param type ��� �������
	 * @param currencyCode ��� ������
	 * @return ��������� ������� ��� null
	 * @throws BusinessException
	 */
	public CommissionRule findRule(final CommissionType type, final String currencyCode) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<CommissionRule>()
			{
				public CommissionRule run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.commission.CommissionService.findRule");
					return (CommissionRule) query.setParameter("type", type)
							.setParameter("currencyCode", currencyCode)
							.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� ��� ������� ������� �������� ��� ��������� ����
	 * @param type ��� ��������
	 * @return ������ ����� ��������
	 */
	public List<? extends CommissionRule> findRules(final CommissionType type) throws BusinessException
	{
		try
		{
		    return HibernateExecutor.getInstance().execute(new HibernateAction<List<? extends CommissionRule>>()
		    {
		        public List<? extends CommissionRule> run(Session session) throws Exception
		        {
			        Query query = session.getNamedQuery("com.rssl.phizic.business.commission.CommissionService.findRules");
			        //noinspection unchecked
			        return (List<? extends CommissionRule>) query.setParameter("type", type)
					        .list();
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	/**
	 * ����� ��� �������� �� ��� ID
	 * @param commissionId ID ��������
	 * @return ��� ��������
	 */
	public CommissionType findTypeById(final Long commissionId) throws BusinessException
	{
		try
		{
		    return HibernateExecutor.getInstance().execute(new HibernateAction<CommissionType>()
		    {
		        public CommissionType run(Session session) throws Exception
		        {
		            return (CommissionType) session.get(CommissionType.class, commissionId);
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	/**
	 * ����� ��� �������� �� ��� �����
	 * @param typeKey ����
	 * @return ��������� ��� ��������
	 */
	public CommissionType findTypeByKey(final String typeKey) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<CommissionType>()
			{
				public CommissionType run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.commission.CommissionService.typeByKey");
					return (CommissionType) query.setParameter("key", typeKey).uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}