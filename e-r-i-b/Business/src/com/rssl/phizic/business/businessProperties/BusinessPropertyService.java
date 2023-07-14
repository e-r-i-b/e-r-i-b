package com.rssl.phizic.business.businessProperties;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lukina
 * @ created 25.10.2012
 * @ $Author$
 * @ $Revision$
 */

public class BusinessPropertyService
{
	private static final SimpleService simpleService = new SimpleService();
	public static final String BUSINESS_PROPERTIES_CACHE = "BusinessPropertyService.getBusinessProperties";
	private static final Object LOCKER = new Object();
	private static final String KEY_PREFIX = "key_";

	private static final Cache businessPropertiesCache;

	static
	{
		businessPropertiesCache = CacheProvider.getCache(BUSINESS_PROPERTIES_CACHE);

		if ( businessPropertiesCache == null )
			throw new RuntimeException("Не найден кеш для бизнес-настроек");
	}

	/**
	 * Возвращает список настроек типа T
	 * @param clazz - тип возвращаемых настроек
	 * @return список настроек
	 */
	public <T extends BusinessProperty> List<T> findProperties(Class<T> clazz) throws BusinessException
	{
		Element element  = businessPropertiesCache.get(clazz.getName());

		if ( element != null )
			return  (List<T>) element.getObjectValue();

		synchronized (LOCKER)
		{
			element  = businessPropertiesCache.get(clazz.getName());

			if ( element != null )
				return  (List<T>) element.getObjectValue();
			List<T> properties = findPropertiesFromDB(clazz);
			if (properties != null)
				businessPropertiesCache.put(new Element(clazz.getName(), properties));
			return properties;
		}
	}

	/**
	 * Возвращает список ключей настроек типа T
	 * @param clazz - тип возвращаемых настроек
	 * @return список ключей настроек
	 */
	public <T extends BusinessProperty> List<String> findPropertyKeys(Class<T> clazz) throws BusinessException
	{
		String cacheKey = KEY_PREFIX + clazz.getName();
		Element element  = businessPropertiesCache.get(cacheKey);

		if ( element != null )
			return  (List<String>) element.getObjectValue();

		synchronized (LOCKER)
		{
			element  = businessPropertiesCache.get(cacheKey);

			if ( element != null )
				return  (List<String>) element.getObjectValue();
			List<String> properties = findPropertyKeysFromDB(clazz);
			if (properties != null)
				businessPropertiesCache.put(new Element(cacheKey, properties));
			return properties;
		}
	}

	private <T extends BusinessProperty> List<T> findPropertiesFromDB(Class<T> clazz) throws BusinessException
	{
		return simpleService.find(DetachedCriteria.forClass(clazz).addOrder(Order.asc("id")));
	}

	private <T extends BusinessProperty> List<String> findPropertyKeysFromDB(Class<T> clazz) throws BusinessException
	{
		return simpleService.find(DetachedCriteria.forClass(clazz).addOrder(Order.asc("id")).setProjection(Projections.property("key")));
	}

	/**
	 * Обновление списка настроек
	 * @param newProperty - список настроек, которые необхадимо добавить/обновить
	 * @param removePropertyIds  - список id настроек, которые необходимо удалить
	 * @param clazz  - класс настроек
	 * @return список настроек
	 * @throws BusinessException
	 */
	public <T extends BusinessProperty> List<T>  updateList(List<T> newProperty, List<Long> removePropertyIds, final Class clazz) throws BusinessException
	{
		if (CollectionUtils.isNotEmpty(removePropertyIds))
			removePropertyByIds(removePropertyIds);
		List<T> properties = simpleService.addOrUpdateListWithConstraintViolationException(newProperty);
		clearCache(clazz);
		return properties;
	}

	/**
	 * Обновление списка настроек
	 * @param properties - список настроек, которые необхадимо добавить/обновить
	 * @param clazz  - класс настроек
	 * @return список настроек
	 * @throws BusinessException
	 */
	public <T extends BusinessProperty> List<T>  updateList(List<T> properties, final Class clazz) throws BusinessException
	{
		Map<Long, T> savedPropertiesMap = toMap(findProperties(clazz));

		for (T property : properties)
		{
			Long currentId = property.getId();
			T prop = savedPropertiesMap.get(currentId);
			if (prop == null)
			{
				property.setId(null); //новое свойство
			}
			else
			{
				//удаляем из справочника данный проперти, оставшиеся в справочнике
				// после цикла свойства необходимо удалить из базы
				savedPropertiesMap.remove(currentId);
			}
		}

		simpleService.addOrUpdateListWithConstraintViolationException(properties);

		removePropertyByIds(new ArrayList<Long>(savedPropertiesMap.keySet()));
		clearCache(clazz);
		return properties;
	}

	private void clearCache(Class clazz) throws BusinessException
	{
		businessPropertiesCache.remove(clazz.getName());
	}

	/**
	 * удалить настройки по id
	 * @param ids удаляемых настроек
	 * @throws BusinessException
	 */
	public void removePropertyByIds(final List<Long> ids) throws BusinessException
	{
		if (CollectionUtils.isEmpty(ids))
			return;
		
		try
        {
            HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
            {
                public Void run(Session session) throws Exception
                {
                    Query query = session.getNamedQuery(BusinessProperty.class.getName() + ".removeByIds");
					query.setParameterList("ids", ids);
	                query.executeUpdate();
	                return null;
                }
            });
        }
        catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private <T extends BusinessProperty> Map<Long, T> toMap(List<T> properties)
	{
		Map<Long, T> result = new HashMap<Long, T>(properties.size());
		for (T property: properties)
			result.put(property.getId(), property);

		return result;
	}
}
