package com.rssl.phizic.business.push;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * ������ ��� ������ � Push ���������
 * @author basharin
 * @ created 04.10.13
 * @ $Author$
 * @ $Revision$
 */

public class PushResourcesService
{
	private SimpleService simpleService = new SimpleService();

	/**
	 * ��������� ������ push ��������
	 *
	 * @return List&lt;PushResource&gt; ������ push-��������
	 * @throws Exception
	 */
	public List<PushResource> getPushResources() throws BusinessException
	{
		return simpleService.getAll(PushResource.class);
	}

	/**
	 * ��������� ����� push �������
	 * @param key - ���� ������� push-���������
	 * @return PushResource push-������
	 * @throws Exception
	 */
	public PushParams getPushParams(String key) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(PushParams.class)
															.add(Expression.eq("key", key));
		return simpleService.findSingle(criteria);
	}

	/**
	 * ��������� ������� ������ push �������
	 * @param key - ���� ������� push-���������
	 * @return String ����� push-�������
	 * @throws Exception
	 */
	public String getPushFullMessage(final String key) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<String>()
			{
			    public String run(Session session) throws Exception
			    {
				    Query query = session.getNamedQuery("com.rssl.phizic.business.push.getFullMessage");
				    query.setParameter("externalKey", key);
				    return (String)query.uniqueResult();
			    }
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ��������� ��������� ������ push �������
	 * @param key - ���� ������� push-���������
	 * @return String ����� push-�������
	 * @throws Exception
	 */
	public String getPushShortMessage(final String key) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<String>()
			{
			    public String run(Session session) throws Exception
			    {
				    Query query = session.getNamedQuery("com.rssl.phizic.business.push.getShortMessage");
				    query.setParameter("externalKey", key);
				    return (String)query.uniqueResult();
			    }
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ��������� ��������� ���-������� �� �����
	 * @param key ���� ������� push-���������
	 * @return ���������
	 * @throws BusinessException
	 */
	public String getPushAttributes(final String key) throws BusinessException
	{
		if (StringHelper.isEmpty(key))
		{
			throw new IllegalArgumentException("���� ������� �� ����� ���� null.");
		}
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<String>()
			{
				public String run(Session session) throws Exception
				{
					return (String) session.getNamedQuery("com.rssl.phizic.business.push.getAttributes")
							.setParameter("externalKey", key)
							.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
