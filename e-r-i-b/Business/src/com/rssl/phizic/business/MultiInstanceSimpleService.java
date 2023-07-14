package com.rssl.phizic.business;

import com.rssl.phizic.dataaccess.DatabaseServiceBase;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;
import org.hibernate.*;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 08.07.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сервис позволяющий работать с несколькими базами данных
 */
public class MultiInstanceSimpleService extends DatabaseServiceBase
{
	protected static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);
	// максимальный размер "пачки", после которого надо сбрасывать данные в бд, для защиты от переполнения

	public <T> T refresh(final T obj, String instanceName) throws BusinessException
	{
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance(instanceName);

			return trnExecutor.execute(new HibernateAction<T>()
			{
				public T run(Session session) throws Exception
				{
					session.refresh(obj);
					return obj;
				}
			}
			);
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

	public <T> T add(final T obj, String instanceName) throws BusinessException
	{
		try
		{
			return super.add(obj, instanceName);
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

	/**
	 * создаем копию объекта
	 * Если объект уже существует, переписывает.
	 * @param obj объект
	 * @param instanceName имя экземпляра БД
	 * @return сохраненный объект
	 * @throws BusinessException ошибки при репликации
	 */
	public <T> T replicate(final T obj, String instanceName) throws BusinessException
	{
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance(instanceName);

			return trnExecutor.execute(new HibernateAction<T>()
			{
				public T run(Session session) throws Exception
				{
					session.replicate(obj, ReplicationMode.OVERWRITE);
					session.flush();
					return obj;
				}
			}
			);
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

	public <T> List<T> addList(final List<T> list, String instanceName) throws BusinessException
	{
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance(instanceName);
			return trnExecutor.execute(new HibernateAction<List<T>>()
			{
				public List<T> run(Session session) throws Exception
				{
					int count = 0;
					for (T obj : list)
					{
						session.save(obj);
						count++;
						// Если в сессию хибернейта набилось достаточно много данных, сбрасываем их в бд
						if (count >= BATCH_SIZE) {
							session.flush();
							session.clear();
							count = 0;
						}
					}

					return list;
				}
			}
			);
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

	public <T> List<T> addListWithConstraintViolationException(final List<T> list, String instanceName) throws BusinessException,ConstraintViolationException
	{
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance(instanceName);
			return trnExecutor.execute(new HibernateAction<List<T>>()
			{
				public List<T> run(Session session) throws Exception
				{
					int count = 0;
					for (T obj : list)
					{
						session.save(obj);
						count++;
						// Если в сессию хибернейта набилось достаточно много данных, сбрасываем их в бд
						if (count >= BATCH_SIZE) {
							session.flush();
							session.clear();
							count = 0;
						}
					}

					return list;
				}
			}
			);
		}
		catch (ConstraintViolationException e)
		{
			throw  e;
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

	/**
	 * Метод для добавления/обновления пачки данных в БД
	 * @param list список объектов
	 * @param instanceName инстанс базы
	 * @param <T> тип объекта
	 * @param isIgnorNonUnique игнорировать ли неуникальность при добалении в сессию
	 * @return список добавляемых объектов
	 * @throws BusinessException
	 */
	public <T> List<T> addOrUpdateList(final List<T> list, String instanceName, boolean isIgnorNonUnique) throws BusinessException
	{
		if (list.isEmpty())
			return list;
		try
		{
			return addOrUpdateListWithConstraintViolationException(list, instanceName, isIgnorNonUnique);
		}
		catch (ConstraintViolationException e)
		{
			throw new BusinessException(e);
		}
		catch (BusinessException e)
		{
			throw e;
		}
	}

	/**
	 * Метод для добавления/обновления пачки данных в БД
	 * @param list список объектов
	 * @param instanceName инстанс базы
	 * @param <T> тип объекта
	 * @return список добавляемых объектов
	 * @throws BusinessException
	 */
	public <T> List<T> addOrUpdateList(final List<T> list, String instanceName) throws BusinessException
	{
		return addOrUpdateList(list, instanceName, false);
	}

	/**
	 * Метод для добавления/обновления пачки данных в БД
	 * @param list список добавляемых объектов
	 * @param instanceName инстансе базы
	 * @param <T> тип объекта
	 * @param isIgnorNonUnique игнорировать ли неуникальность при добалении в сессию
	 * @return список добавляемых объектов
	 * @throws BusinessException, ConstraintViolationException
	 */
	public <T> List<T> addOrUpdateListWithConstraintViolationException(final List<T> list, String instanceName, final boolean isIgnorNonUnique) throws BusinessException, ConstraintViolationException
	{
		if (list.isEmpty())
			return list;
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance(instanceName);
			return trnExecutor.execute(new HibernateAction<List<T>>()
			{
				public List<T> run(Session session) throws Exception
				{
					int count = 0;
					for (T obj : list)
					{
						try
						{
							session.saveOrUpdate(obj);
							count++;
						}
						catch (NonUniqueObjectException e)
						{
							// если не игнорировать, пробрасываем дальше
							if(!isIgnorNonUnique)
								throw e;

							log.error("Не удалось добавить объект: " + StringHelper.getEmptyIfNull(obj), e);
						}

						// Если в сессию хибернейта набилось достаточно много данных, сбрасываем их в бд
						if (count >= BATCH_SIZE) {
							session.flush();
							session.clear();
							count = 0;
						}
					}

					return list;
				}
			});
		}
		catch (ConstraintViolationException e)
		{
			throw  e;
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

	/**
	 * Метод для добавления/обновления пачки данных в БД
	 * @param list список добавляемых объектов
	 * @param instanceName инстансе базы
	 * @param <T> тип объекта
	 * @return список добавляемых объектов
	 * @throws BusinessException, ConstraintViolationException
	 */
	public <T> List<T> addOrUpdateListWithConstraintViolationException(final List<T> list, String instanceName) throws BusinessException, ConstraintViolationException
	{
		return addOrUpdateListWithConstraintViolationException(list, instanceName, false);
	}

    /**
     * Копирует состояние переданного объекта(если в сессии он уже вдруг есть) типа List, в персистентный объект(т.е в бд) с таким же идентификаторов
     * @param list
     * @param instanceName
     * @param <T>
     * @return
     * @throws BusinessException
     */
    public <T> List<T> merge(final List<T> list, String instanceName) throws BusinessException
    {
        if (list.isEmpty())
            return list;
        try
        {
            HibernateExecutor trnExecutor = HibernateExecutor.getInstance(instanceName);
            return trnExecutor.execute(new HibernateAction<List<T>>()
            {
                public List<T> run(Session session) throws Exception
                {
                    int count = 0;
                    for (T obj : list)
                    {
                        session.merge(obj);
                        count++;
                        // Если в сессию хибернейта набилось достаточно много данных, сбрасываем их в бд
                        if (count >= BATCH_SIZE) {
                            session.flush();
                            session.clear();
                            count = 0;
                        }
                    }

                    return list;
                }
            }
            );
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

	public <T> T addOrUpdateWithConstraintViolationException(final T obj, String instanceName) throws BusinessException, ConstraintViolationException
	{
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance(instanceName);

			return trnExecutor.execute(new HibernateAction<T>()
			{
				public T run(Session session) throws Exception
				{
					session.saveOrUpdate(obj);
					return obj;
				}
			}
			);
		}
		catch (ConstraintViolationException e)
		{
			throw  e;
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

    /**
     * Копирует состояние переданного объекта(если в сессии он уже вдруг есть) в персистентный объект(т.е в бд) с таким же идентификаторов
     * @param obj
     * @param instanceName
     * @param <T>
     * @return
     * @throws BusinessException
     * @throws ConstraintViolationException
     */
    public <T> T merge(final T obj, String instanceName) throws BusinessException, ConstraintViolationException
    {
        try
        {
            HibernateExecutor trnExecutor = HibernateExecutor.getInstance(instanceName);

            return trnExecutor.execute(new HibernateAction<T>()
            {
                public T run(Session session) throws Exception
                {
                    session.merge(obj);
                    return obj;
                }
            }
            );
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

	public <T> T addOrUpdate(final T obj, String instanceName) throws BusinessException
	{
		try
		{
			return addOrUpdateWithConstraintViolationException(obj,instanceName);
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

	public <T> T update(final T obj, String instanceName) throws BusinessException
	{
		try
		{
			return super.update(obj, instanceName);
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


    /**
     * При использовании в операции ловить по завершению в экшене
     * @param obj
     * @param instanceName
     * @param <T>
     * @throws BusinessException
     * @throws ConstraintViolationException
     */
    public <T> void removeWithConstraintViolationException(final T obj, String instanceName) throws BusinessException,ConstraintViolationException
    {
        try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance(instanceName);

			trnExecutor.execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.delete(obj);
					return null;
				}
			}
			);
		}
        catch (ConstraintViolationException e)
		{
			throw  e;
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

	public <T> void remove(final T obj, String instanceName) throws BusinessException
	{
		try
		{
			removeWithConstraintViolationException(obj,instanceName);
		}
		catch (BusinessException e)
		{
			throw e;
		}
	}

	public void removeList(final List<?> list, String instanceName) throws BusinessException
	{
		try
		{
			super.deleteAll(list, instanceName);
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

	public <T> List<T> getAll(final Class<T> clazz, String instanceName) throws BusinessException
	{
		return find(DetachedCriteria.forClass(clazz), instanceName);
	}

	public <T> List<T> find(final T obj, String instanceName) throws BusinessException
	{
		return find(DetachedCriteria.forClass(obj.getClass()).add(Example.create(obj)), instanceName);
	}

	public <T> T findSingle(T obj, String instanceName) throws BusinessException
	{
		return this.<T>findSingle(DetachedCriteria.forClass(obj.getClass()).add(Example.create(obj)), instanceName);
	}

	public <T> T findById(final Class<T> clazz, final Long id, String instanceName) throws BusinessException
	{
		try
		{
			return super.findById(clazz, id, null, instanceName);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public <T> List<T> findByIds(Class<T> clazz, Collection<Long> ids, String instanceName) throws BusinessException
	{
		return ids.size() != 0 ? this.<T>find(DetachedCriteria.forClass(clazz).add(Expression.in("id", ids)), instanceName) : Collections.EMPTY_LIST;
	}

	public <T> List<T> find(final DetachedCriteria detachedCriteria, String instanceName) throws BusinessException
	{
		try
		{
			return super.find(detachedCriteria, null, instanceName);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public <T> List<T> find(final DetachedCriteria detachedCriteria, final int maxResults, String instanceName) throws BusinessException
	{
		HibernateExecutor lightExecutor = HibernateExecutor.getInstance(instanceName);
		try
		{
			return lightExecutor.execute(new HibernateAction<List<T>>()
			{
				public List<T> run(Session session) throws Exception
				{
					Criteria criteria = detachedCriteria.getExecutableCriteria(session);
					criteria.setMaxResults(maxResults);
					//noinspection unchecked
					return criteria.list();
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public <T> T findSingle(DetachedCriteria detachedCriteria, String instanceName) throws BusinessException
	{
		try
		{
			return super.<T>findSingle(detachedCriteria, null, instanceName);
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

	/**
	 * Выполнить именованный запрос
	 * @param queryName - запрос
	 * @param bean - объект с геттерами для параметров, которые используются запросе
	 * @return
	 * @throws BusinessException
	 */
	public <T> List<T> executeQuery(final String queryName, final Object bean, String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<T>>()
			{
				public List<T> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(queryName);
					query.setProperties(bean);
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

	public <T> T executeQuerySingle(final String queryName, final Object bean, String instanceName) throws BusinessException
	{
		List<T> results = executeQuery(queryName, bean, instanceName);
		return results.size() == 1 ? results.get(0) : null;
	}

	/**
	 * Добавить кретирии NVL функцию
	 * @param criteria криетря
	 * @param name наименование поля сущности, для которого используем функцию
	 * @param value значение
	 */
	public void addNvlParameter(DetachedCriteria criteria, String name, String value)
	{
		criteria.add(Restrictions.sqlRestriction("nvl({alias}." + name + ", 'NULL') = ?", StringUtils.defaultIfEmpty(value, "NULL"), Hibernate.STRING));
	}

	/**
	 * Добавить кретирии NVL функцию
	 * @param criteria криетря
	 * @param name наименование поля сущности, для которого используем функцию
	 * @param value перечисление
	 */
	public void addNvlParameter(DetachedCriteria criteria, String name, Enum value)
	{
		String stringValue = value == null ? StringUtils.EMPTY : value.name();
		criteria.add(Restrictions.sqlRestriction("nvl({alias}." + name + ", 'NULL') = ?", StringUtils.defaultIfEmpty(stringValue, "NULL"), Hibernate.STRING));
	}
}
