package com.rssl.phizic.business.exception.locale;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * @author komarov
 * @ created 15.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class ExceptionMappingResourcesService
{
	private static DictionaryRecordChangeInfoService changeInfoService = new DictionaryRecordChangeInfoService();
	private static SimpleService simpleService = new SimpleService();

	/**
	 * Возвращает список текстовок для заданной локали
	 * @param hash хеш ошибки
	 * @param localeId локаль
	 * @return текстовки
	 * @throws BusinessException
	 */
	public List<ExceptionMappingResources> getResourcesByHash(String hash, String localeId) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(ExceptionMappingResources.class);
		criteria.add(Expression.and(Expression.eq("hash", hash), Expression.eq("localeId", localeId)));
		return simpleService.find(criteria, MultiBlockModeDictionaryHelper.getDBInstanceName());
	}

	/**
	 * Добавляет список текстовок
	 * @param resource текстовки
	 * @throws BusinessException
	 */
	public void addOrUpdateList(List<ExceptionMappingResources> resource) throws BusinessException
	{
		simpleService.addOrUpdateList(resource, MultiBlockModeDictionaryHelper.getDBInstanceName());
		addToLog(resource, ChangeType.update);
	}

	/**
	 * @param hash хеш
	 * @param groupIds номер
	 * @return список текстовок
	 * @throws BusinessException
	 */
	public List<ExceptionMappingResources> getByHashAndGroup(final String hash, final Long[] groupIds)throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(MultiBlockModeDictionaryHelper.getDBInstanceName()).execute(new HibernateAction<List<ExceptionMappingResources>>()
			{
				public List<ExceptionMappingResources> run(Session session) throws Exception
				{
					return (List<ExceptionMappingResources>) session.getNamedQuery(ExceptionMappingResources.class.getName() + ".getByHashAndGroup")
							.setParameter("hash", hash)
							.setParameterList("group", groupIds)
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
	 * @param hash хеш
	 * @return список текстовок
	 * @throws BusinessException
	 */
	public List<ExceptionMappingResources> getByHash(final String hash)throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(MultiBlockModeDictionaryHelper.getDBInstanceName()).execute(new HibernateAction<List<ExceptionMappingResources>>()
			{
				public List<ExceptionMappingResources> run(Session session) throws Exception
				{
					return (List<ExceptionMappingResources>) session.getNamedQuery(ExceptionMappingResources.class.getName() + ".getByHash")
							.setParameter("hash", hash)
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
	 * Получить текстовку по составному идентификатору
	 * @param hash хеш
	 * @param group группа
	 * @param localeId локаль
	 * @return текстовка
	 * @throws BusinessException
	 */
	public ExceptionMappingResources getByCompositeId(final String hash, final Long group, final String localeId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<ExceptionMappingResources>()
			{
				public ExceptionMappingResources run(Session session) throws Exception
				{
					return (ExceptionMappingResources) session.getNamedQuery(ExceptionMappingResources.class.getName() + ".getByCompositeId")
							.setParameter("hash", hash)
							.setParameter("group_id", group)
							.setParameter("locale_id", localeId)
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
	 * Удаляет текстовки
	 * @param hash хеш
	 * @param groupIds номер
	 * @throws BusinessException
	 */
	public void delete(String hash, Long[] groupIds)throws BusinessException
	{
		delete(getByHashAndGroup(hash, groupIds));
	}

	/**
	 * Удаляет все текстовки с заданным хешом
	 * @param hash хеш
	 * @throws BusinessException
	 */
	public void deleteAll(String hash)throws BusinessException
	{
		delete(getByHash(hash));
	}

	private void delete(List<ExceptionMappingResources> resources) throws BusinessException
	{
		try
		{
			if (CollectionUtils.isNotEmpty(resources))
			{
				simpleService.deleteAll(resources, MultiBlockModeDictionaryHelper.getDBInstanceName());
				addToLog(resources, ChangeType.delete);
			}
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private void addToLog(List<ExceptionMappingResources> resources, ChangeType type) throws BusinessException
	{
		changeInfoService.addChangesToLog(resources, type);
	}
}
