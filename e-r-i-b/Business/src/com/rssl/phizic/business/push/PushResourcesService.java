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
 * —ервис дл€ работы с Push ресурсами
 * @author basharin
 * @ created 04.10.13
 * @ $Author$
 * @ $Revision$
 */

public class PushResourcesService
{
	private SimpleService simpleService = new SimpleService();

	/**
	 * получение списка push шаблонов
	 *
	 * @return List&lt;PushResource&gt; список push-шаблонов
	 * @throws Exception
	 */
	public List<PushResource> getPushResources() throws BusinessException
	{
		return simpleService.getAll(PushResource.class);
	}

	/**
	 * получение части push шаблона
	 * @param key - ключ шаблона push-сообщени€
	 * @return PushResource push-шаблон
	 * @throws Exception
	 */
	public PushParams getPushParams(String key) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(PushParams.class)
															.add(Expression.eq("key", key));
		return simpleService.findSingle(criteria);
	}

	/**
	 * получение полного текста push шаблона
	 * @param key - ключ шаблона push-сообщени€
	 * @return String текст push-шаблона
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
	 * получение короткого текста push шаблона
	 * @param key - ключ шаблона push-сообщени€
	 * @return String текст push-шаблона
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
	 * ѕолучение атрибутов пуш-шаблока по ключу
	 * @param key ключ шаблона push-сообщени€
	 * @return аттрибуты
	 * @throws BusinessException
	 */
	public String getPushAttributes(final String key) throws BusinessException
	{
		if (StringHelper.isEmpty(key))
		{
			throw new IllegalArgumentException(" люч шаблона не может быть null.");
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
