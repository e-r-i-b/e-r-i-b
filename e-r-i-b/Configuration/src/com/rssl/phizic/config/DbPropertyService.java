package com.rssl.phizic.config;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.config.propertysyncinfo.PropertiesSyncInfoService;
import com.rssl.phizic.config.propertysyncinfo.PropertySyncInfo;
import com.rssl.phizic.config.propertysyncinfo.PropertySyncInfoStatus;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.xstream.XStreamSerializer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import java.util.*;

/**
 * Сервис для работы с настройками в БД
 * @author gladishev
 * @ created 25.01.14
 * @ $Author$
 * @ $Revision$
 */
public class DbPropertyService
{
	public static final String REPLICATE_PROPERTIES_MESSAGE_KEY = "ReplicateProperties";
	public static final String SYNCHRONIZATION_GUID_KEY = "SynchronizationGuid";
	private static final String PROPERTY_CATEGORY = "PropertyCategory";
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);
	private static final String REPLICATION_START = "Получен запрос на репликацию настроек: ";
	private static final String EMPTY_REPLICATION_PARAMS = "Не найдено настроек для репликации. Выполнение метода прекращается.";
	private static final String REPLICATION_PARAMS = "Параметры репликации: category=%s, operationGUID=%s, dbInstance=%s";
	private static final String REPLICATION_SUCCESS = "Настройки успешно сохранены в БД";
	private static final String REPLICATION_RESULT_SAVED = "PropertySyncInfo обновлен";

	/**
	 * Поиск настроек по ключам и категории
	 * @param propertyKeys - список ключей
	 * @param category - категория
	 * @param dbInstance - префикс БД
	 * @return список настроек
	 */
	public static List<Property> findProperties(final Set<String> propertyKeys, final String category, final String dbInstance)
	{
		try
		{
			return HibernateExecutor.getInstance(dbInstance).execute(new HibernateAction<List<Property>>()
			{
				public List<Property> run(Session session) throws Exception
				{
					DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Property.class).
							add(Expression.eq("category", category)).add(Expression.in("key", propertyKeys));
					//noinspection unchecked
					return (List<Property>) detachedCriteria.getExecutableCriteria(session).list();
				}
			});
		}
		catch (Exception e)
		{
			throw new ConfigurationException(e.getMessage(), e);
		}
	}

	/**
	 * Поиск настроек по категории
	 * @param category - категория
	 * @param dbInstance - префикс БД
	 * @return список настроек
	 */
	public static List<Property> findProperties(final String category, final String dbInstance)
	{
		try
		{
			return HibernateExecutor.getInstance(dbInstance).execute(new HibernateAction<List<Property>>()
			{
				public List<Property> run(Session session) throws Exception
				{
					DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Property.class).
							add(Expression.eq("category", category));
					//noinspection unchecked
					return (List<Property>) detachedCriteria.getExecutableCriteria(session).list();
				}
			});
		}
		catch (Exception e)
		{
			throw new ConfigurationException(e.getMessage(), e);
		}
	}

	/**
	 * Поиск настройки по категории
	 * @param key - ключ
	 * @param category - категория
	 * @param dbInstance - префикс БД
	 * @return список настроек
	 */
	public static Property findProperty(final String key, final String category, final String dbInstance)
	{
		try
		{
			return HibernateExecutor.getInstance(dbInstance).execute(new HibernateAction<Property>()
			{
				public Property run(Session session) throws Exception
				{
					DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Property.class);
					detachedCriteria.add(Expression.eq("key", key)).add(Expression.eq("category", category));
					return (Property) detachedCriteria.getExecutableCriteria(session).uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new ConfigurationException(e.getMessage(), e);
		}
	}


	/**
	 * Получение префикса БД по категории
	 * @param category - категория
	 * @return префикс БД
	 */
	public static String getDbInstance(String category)
	{
		return ConfigFactoryHelper.getConfigFactoryDocument().getDbInfoByName("editPropertiesDB").getDbInstance(category);
	}

	/**
	 * Поиск списка настроек по префиксу ключа и по категории
	 * @param key - ключ
	 * @param category - категория
	 * @param dbInstance - префикс БД
	 * @return список настроек
	 */
	public static List<Property> findPropertiesLike(final String key, final String category, String dbInstance)
	{
		try
		{
			return HibernateExecutor.getInstance(dbInstance).execute(new HibernateAction<List<Property>>()
			{
				public List<Property> run(Session session) throws Exception
				{
					DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Property.class);
					detachedCriteria.add(Expression.like("key", key + "%")).
									add(Expression.eq("category", category)).
									addOrder(Order.asc("key"));
					return (List<Property>) detachedCriteria.getExecutableCriteria(session).list();
				}
			});
		}
		catch (Exception e)
		{
			throw new ConfigurationException(e.getMessage(), e);
		}
	}

	//*************************************************************

	/**
	 * Обновить список настроек в БД
	 * @param properties - мап<ключ, значение>
	 * @param propertyCategory - категория настроек
	 * @param dbInstance - префикс БД
	 */
	public static void updateProperties(final Map<String, String> properties, final PropertyCategory propertyCategory, final String dbInstance, String operationGUID)
	{
		if (MapUtils.isEmpty(properties))
			return;


		PropertySyncInfoStatus syncStatus = null;
		try
		{
			HibernateExecutor.getInstance(dbInstance).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					String category = propertyCategory.getValue();

					Map<String, Property> dbPropertiesMap = toMap(DbPropertyService.findProperties(properties.keySet(), category, dbInstance));

					for (String key : properties.keySet())
					{
						Property dbProperty = dbPropertiesMap.get(key);
						String value = properties.get(key);

						if (dbProperty == null)
						{
							if (StringHelper.isEmpty(value))
								continue;

							session.save(new Property(key, value, category));
						}
						else if (StringHelper.isNotEmpty(value))
						{
							if (!dbProperty.getValue().equals(value))
							{
								dbProperty.setValue(value);
								session.update(dbProperty);
							}
						}
						else
							session.delete(dbProperty);
					}

					return null;
				}
			});
			syncStatus = PropertySyncInfoStatus.OK;
		}
		catch (Exception e)
		{
			syncStatus = PropertySyncInfoStatus.ERROR;
			log.error(e);
			throw new ConfigurationException(e.getMessage(), e);
		}
		finally
		{
			if (operationGUID != null)
			{
				PropertiesSyncInfoService syncInfoService = new PropertiesSyncInfoService();
				PropertySyncInfo syncInfo = new PropertySyncInfo(ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber(), Calendar.getInstance(), operationGUID, syncStatus);

				try
				{
					syncInfoService.save(syncInfo);
					log.info(REPLICATION_RESULT_SAVED);
				}
				catch (Exception e)
				{
					log.error(e);
				}
			}
		}
	}

	/**
	 * Затычка для updateProperties, в случае если пишем настройки в базу не в контексте репликации
	 */
	public static void updateProperties(final Map<String, String> properties, final PropertyCategory propertyCategory, final String dbInstance)
	{
		updateProperties(properties, propertyCategory, dbInstance, null);
	}

	/**
	 * Обновить список настроек в БД имеющих определенный префикс, удаляет настройки которых нет в мапе с таким префиксом.
	 * @param key - префикс в кллюче
	 * @param properties - мап<ключ, значение>.
	 * @param propertyCategory - категория
	 * @param dbInstance - префикс БД
	 * @return список настроек
	 */
	public static void updatePropertiesWithLikeKeys(final String key, final Map<String, String> properties, final PropertyCategory propertyCategory, final String dbInstance)
	{
		try
		{
			HibernateExecutor.getInstance(dbInstance).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					String category = propertyCategory.getValue();
					Map<String, Property> dbPropertiesMap = toMap(DbPropertyService.findPropertiesLike(key, category, dbInstance));

					for (String key : properties.keySet())
					{
						Property dbProperty = dbPropertiesMap.get(key);
						dbPropertiesMap.remove(key);
						String value = properties.get(key);

						if (dbProperty == null)
						{
							if (StringHelper.isEmpty(value))
								continue;

							session.save(new Property(key, value, category));
						}
						else if (StringHelper.isNotEmpty(value))
						{
							if (!dbProperty.getValue().equals(value))
							{
								dbProperty.setValue(value);
								session.update(dbProperty);
							}
						}
						else
							session.delete(dbProperty);
					}

					for (Property property : dbPropertiesMap.values())
						session.delete(property);

					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new ConfigurationException(e.getMessage(), e);
		}
	}

	/**
	 * Обновить настройку в БД основного приложения
	 * @param key - ключ
	 * @param value - значение
	 */
	public static void updateProperty(String key, String value)
	{
		updateProperty(key, value, PropertyCategory.Phizic);
	}

	/**
	 * Обновить настройку в БД
	 * @param key - ключ
	 * @param value - значение
	 * @param propertyCategory - категория настройки
	 */
	public static void updateProperty(final String key, final String value, PropertyCategory propertyCategory)
	{
		final String category = propertyCategory.getValue();
		final String dbInstance = DbPropertyService.getDbInstance(category);
		if (StringHelper.isEmpty(value))
		{
			removeProperty(key, category, dbInstance, false);
			return;
		}

		try
		{
			HibernateExecutor.getInstance(dbInstance).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Property dbProperty = DbPropertyService.findProperty(key, category, dbInstance);
					if (dbProperty != null)
					{
						dbProperty.setValue(value);
						session.update(dbProperty);
					}
					else
						session.save(new Property(key, value, category));

					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new ConfigurationException(e.getMessage(), e);
		}
	}

	/**
	 * Обновить списочную настройку в БД
	 * @param key - ключ
	 * @param values - значение
	 * @param propertyCategory - значение
	 * @param dbInstance - значение
	 */
	public static void updateListProperty(final String key, final List<String> values, PropertyCategory propertyCategory, final String dbInstance)
	{
		final String category = propertyCategory.getValue();
		if (CollectionUtils.isEmpty(values))
		{
			removeProperty(key, category, dbInstance, true);
			return;
		}

		try
		{
			HibernateExecutor.getInstance(dbInstance).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					List<Property> dbProperties = DbPropertyService.findPropertiesLike(key, category, dbInstance);
					List<Property> newDbProperties = new ArrayList<Property>(values.size());
					int dbPropertiesSize = dbProperties.size();
					int i;
					for (i = 0; i<values.size(); i++)
					{
						String value = values.get(i);
						Property property;
						if (i < dbPropertiesSize)
						{
							property = dbProperties.get(i);
							if (property.getValue().equals(value))
								continue;

							property.setValue(value);
						}
						else
							property = new Property(key + i, value, category);

						newDbProperties.add(property);
					}

					for (int j=i; j<dbPropertiesSize; j++)
						session.delete(dbProperties.get(j));

					session.flush();

					for (Property property : newDbProperties)
						session.saveOrUpdate(property);

					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new ConfigurationException(e.getMessage(), e);
		}
	}

	/**
	 * Обновить настройки
	 * @param propertiesXml - строка со списком настроек
	 */
	public static void updateProperties(final String propertiesXml)
	{
		log.trace(REPLICATION_START + propertiesXml);
		// мап<категория, мап<ключ, значение>>
		final Map<String, Object> properties = (Map<String, Object>) XStreamSerializer.deserialize(propertiesXml);
		if (MapUtils.isEmpty(properties))
		{
			log.trace(EMPTY_REPLICATION_PARAMS);
			return;
		}

			final String category = (String) properties.remove(PROPERTY_CATEGORY);
			final String operationGUID = (String) properties.remove(SYNCHRONIZATION_GUID_KEY);
			final PropertyCategory propertyCategory = new PropertyCategory(category);
			final String dbInstance = getDbInstance(category);
			log.trace(String.format(REPLICATION_PARAMS, category, operationGUID, dbInstance));
			try
			{
				HibernateExecutor.getInstance(dbInstance).execute(new HibernateAction<Void>()
				{
					public Void run(Session session) throws Exception
					{
						Map<String, String> props = new HashMap<String, String>();
						for (String key : properties.keySet())
						{
							Object property = properties.get(key);
							if (property.getClass().isArray())
								updateListProperty(key, Arrays.asList((String[]) property), propertyCategory, dbInstance);
							else if(String.class.isInstance(property))
								props.put(key, (String) property);
							else
							   throw new ConfigurationException("Некорректый тип параметра.");
						}
						updateProperties(props, propertyCategory, dbInstance, operationGUID);
						log.trace(REPLICATION_SUCCESS);
						return null;
					}
				});
			}
			catch (Exception e)
			{
				log.error(e);
				throw new ConfigurationException(e.getMessage(), e);
			}
	}

	private static void removeProperty(final String key, final String category, final String dbInstance, final boolean like)
	{
		try
		{
			HibernateExecutor.getInstance(dbInstance).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.config.Property.remove" + (like ? "Like" : ""));
					query.setParameter("key", key + (like ? "%" : "")).setParameter("category", category);
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new ConfigurationException(e.getMessage(), e);
		}
	}

	private static Map<String, Property> toMap(List<Property> properties)
	{
		Map<String, Property> propertiesMap = new HashMap<String, Property>(properties.size());
		for (Property property : properties)
			propertiesMap.put(property.getKey(), property);

		return propertiesMap;
	}
}
