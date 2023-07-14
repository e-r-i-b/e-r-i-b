package com.rssl.phizic.business.schemes;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.MultiInstanceSimpleService;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 10.07.2008
 * @ $Author$
 * @ $Revision$
 */

public class MultiInstanceAccessSchemeService
{
	private static final String PERSONAL_SCHEME_NAME = "personal";
	private static final String QUERY_PREFIX = AccessSchemeService.class.getName()+".";
	private static final MultiInstanceSimpleService simpleService = new MultiInstanceSimpleService();
	private static final Comparator<Service> serviceComparator = new Comparator<Service>()
	{
		public int compare(Service o1, Service o2)
		{
			return o1.getId().compareTo(o2.getId());
		}
	};

	/**
	 * Удалить схему
	 * @param accessScheme
	 * @throws com.rssl.phizic.business.BusinessLogicException
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void remove ( final AccessScheme accessScheme, String instanceName ) throws BusinessLogicException, BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run ( Session session ) throws Exception
				{
					session.getNamedQuery(QUERY_PREFIX + "deleteUnusedOwns")
							.setParameter("scheme", accessScheme)
							.executeUpdate();
					session.delete(accessScheme);
					session.flush();
					return null;
				}
			});
		}
		catch (ConstraintViolationException e)
		{
			throw new BusinessLogicException("Невозможно удалить схему доступа которая используется "
			                                 +accessScheme.getName()+". Удалите все ссылки на нее.");
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public <T extends AccessScheme> T save( T accessScheme, String instanceName ) throws BusinessException
	{
		sortServices(accessScheme);
		return simpleService.addOrUpdate(accessScheme,instanceName);
	}

	/**
	 * Сортирует services для обхода ConstrainViolation
	 * @param accessScheme
	 */
	private <T extends AccessScheme>void sortServices(T accessScheme)
	{
		Collections.sort(accessScheme.getServices(), serviceComparator);
	}

	public List<SharedAccessScheme> getAll (String instanceName) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(SharedAccessScheme.class);

		return simpleService.find(criteria,instanceName);
	}

	/**
	 * скопировать все общие схемы
	 * @param from
	 * @param to
	 * @throws BusinessException
	 */
	public void replicateAll(String to, String from) throws BusinessException
	{
		List<SharedAccessScheme> schemes = getAll(from);
		for (SharedAccessScheme scheme : schemes)
		{
			simpleService.replicate(scheme,to);
		}
	}

	/**
	 * Поиск схем доступа по категории
	 * @param category категория см AccessCategory
	 * @return список схем
	 * @throws BusinessException
	 */
	public List<SharedAccessScheme> findByCategory ( final String category, String instanceName ) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<SharedAccessScheme>>()
			{
				public List<SharedAccessScheme> run ( Session session ) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX+"findByCategory");
					query.setParameter("category", category);
					//noinspection unchecked
					return query.list();
				}
			});
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public <T extends AccessScheme> T findById ( Long schemeId, String instanceName ) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(AccessScheme.class)
				.add(Expression.eq("id", schemeId));

		List<T> result = simpleService.find(criteria,instanceName);

		return (result.size()>0 ? result.get(0) : null);
	}

	public AccessScheme findByName ( String name, String instanceName ) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(SharedAccessScheme.class)
				.add(Expression.eq("name", name));

		List<SharedAccessScheme> result = simpleService.find(criteria,instanceName);

		return (result.size()>0 ? result.get(0) : null);
	}

	/**
	 * Поиск схемы по коду.
	 * @param key
	 * @return
	 * @throws BusinessException
	 */
	public SharedAccessScheme findByKey ( final String key, String instanceName ) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<SharedAccessScheme>()
			{
				public SharedAccessScheme run ( Session session ) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX+"findByKey");
					query.setParameter("key", key);
					//noinspection unchecked
					return (SharedAccessScheme)query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public PersonalAccessScheme createPersonalScheme(String category, List<Service> services )
	{
		PersonalAccessScheme personalAccessScheme=new PersonalAccessScheme();
		personalAccessScheme.setName(PERSONAL_SCHEME_NAME);
		personalAccessScheme.setCategory(category);
		personalAccessScheme.setServices(services);
		return personalAccessScheme;
	}

	/**
	 * Найти схему прав по внешнему ключу
	 * @param externalId - внешний ключ
	 * @param instanceName - экземпляр БД
	 * @return схема прав
	 * @throws BusinessException
	 */
	public SharedAccessScheme findByExternalId(Long externalId, String instanceName) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(SharedAccessScheme.class);
		criteria.add(Expression.eq("externalId",externalId));
		return simpleService.findSingle(criteria,instanceName);
	}
}
