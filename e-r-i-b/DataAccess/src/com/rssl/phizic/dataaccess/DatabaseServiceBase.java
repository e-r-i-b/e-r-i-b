package com.rssl.phizic.dataaccess;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.*;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.Calendar;
import java.util.List;

/**
 * @author krenev
 * @ created 27.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class DatabaseServiceBase
{
	protected static final int BATCH_SIZE = 1000;
	/**
	 * Добавить сужность в БД
	 * @param obj замапленный объект для добавления
	 * @param instanceName инстанс БД
	 * @param <T> тип сущности
	 * @return добавленная сущность
	 * @throws Exception
	 */
	public <T> T add(final T obj, String instanceName) throws Exception
	{
		return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<T>()
		{
			public T run(Session session) throws Exception
			{
				session.save(obj);
				return obj;
			}
		}
		);
	}

	/**
	 * Обновить сущность в БД.
	 * @param obj замапленный объект для обновления
	 * @param instanceName инстанс БД
	 * @param <T> тип сущности
	 * @return обновленная сущность
	 * @throws Exception
	 */
	public <T> T update(final T obj, String instanceName) throws Exception
	{
		return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<T>()
		{
			public T run(Session session) throws Exception
			{
				session.update(obj);
				return obj;
			}
		}
		);
	}

	/**
	 * Обновить сущность в БД.
	 * @param obj замапленный объект для обновления
	 * @param instanceName инстанс БД
	 * @param <T> тип сущности
	 * @return обновленная сущность
	 * @throws Exception
	 */
	public <T> T addOrUpdate(final T obj, String instanceName) throws Exception
	{
		return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<T>()
		{
			public T run(Session session) throws Exception
			{
				session.saveOrUpdate(obj);
				return obj;
			}
		}
		);
	}

	/**
	 * Удалить сущность из БД.
	 * @param obj замапленный объект для обновления
	 * @param instanceName инстанс БД
	 * @param <T> тип сущности
	 * @return обновленная сущность
	 * @throws Exception
	 */
	public <T> T  delete(final T obj, String instanceName) throws Exception
	{
		return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<T>()
		{
			public T run(Session session) throws Exception
			{
				session.delete(obj);
				return obj;
			}
		}
		);
	}

	/**
	 * Удалить список сущностей из БД.
	 * @param list список
	 * @param instanceName инстанс БД
	 * @throws Exception
	 */
	public void deleteAll(final List<?> list, String instanceName) throws Exception
	{
		HibernateExecutor trnExecutor = HibernateExecutor.getInstance(instanceName);
		trnExecutor.execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				for (Object obj : list)
				{
					session.delete(obj);
				}

				return null;
			}
		});
	}

	/**
	 * найти список объектов по критерию
	 * @param detachedCriteria критерий поиска
	 * @param lockMode тип блокировки.
	 * @param instanceName инстанс БД
	 * @param <T> тип сущности
	 * @return списко сущностей.
	 * @throws Exception
	 */
	public <T> List<T> find(final DetachedCriteria detachedCriteria, final LockMode lockMode, String instanceName) throws Exception
	{
		return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<T>>()
		{
			public List<T> run(Session session) throws Exception
			{
				Criteria criteria = detachedCriteria.getExecutableCriteria(session);
				if (lockMode != null)
				{
					criteria.setLockMode(lockMode);
				}
				//noinspection unchecked
				return criteria.list();
			}
		}
		);
	}

	/**
	 * найти единственный объект по критерию
	 * @param detachedCriteria критерий поиска
	 * @param lockMode тип блокировки.
	 * @param instanceName инстанс БД
	 * @param <T> тип сущности
	 * @return списко сущностей.
	 */
	public <T> T findSingle(final DetachedCriteria detachedCriteria, final LockMode lockMode, String instanceName) throws Exception
	{
		return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<T>()
		{
			public T run(Session session) throws Exception
			{
				Criteria criteria = detachedCriteria.getExecutableCriteria(session);
				if (lockMode != null)
					criteria.setLockMode(lockMode);
				//noinspection unchecked
				return (T) criteria.uniqueResult();
			}
		}
		);
	}

	/**
	 * найти сужность по первичному ключу.
	 * @param clazz класс сущности
	 * @param id идентфикатор
	 * @param lockMode режим блокиовки
	 * @param instanceName инстанс БД
	 * @param <T> тип сущности
	 * @return найденная сущность. если не найдено или найдено более 1 возврщается null
	 * @throws Exception
	 */
	public <T> T findById(Class<T> clazz, Long id, LockMode lockMode, String instanceName) throws Exception
	{
		if (id == null)
			return null;

		return this.<T>findSingle(DetachedCriteria.forClass(clazz).add(Expression.eq("id", id)), lockMode, instanceName);
	}

	/**
	 * Получить текущие дату и время сервера базы данных с его таймзоной
	 * @param instanceName инстанс БД
	 * @return дата и время
	 * @throws Exception
	 */
	public Calendar getDatabaseSysDate(String instanceName) throws Exception
	{
		return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Calendar>()
		{
			public Calendar run(Session session) throws Exception
			{
				SQLQuery query = session.createSQLQuery("SELECT SYSTIMESTAMP systemdate FROM DUAL");
				query.addScalar("systemdate", Hibernate.CALENDAR);
				return (Calendar) query.uniqueResult();
			}
		});
	}

	/**
	 * Получить текущие дату и время сервера базы данных
	 * @param instanceName инстанс БД
	 * @return дата и время
	 * @throws Exception
	 */
	public Calendar getDatabaseCurSysDate(String instanceName) throws Exception
	{
		return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Calendar>()
		{
			public Calendar run(Session session) throws Exception
			{
				SQLQuery query = session.createSQLQuery("SELECT SYSDATE systemdate FROM DUAL");
				query.addScalar("systemdate", Hibernate.CALENDAR);
				return (Calendar) query.uniqueResult();
			}
		});
	}

	/**
	 * Добавить список сущностей в БД
	 * @param list список сущностей
	 * @param instanceName имя инстанса БД
	 * @param <T> тип сущности
	 * @return добавленынй список
	 * @throws Exception
	 */
	public <T> List<T> addList(final List<T> list, String instanceName) throws Exception
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

	/**
	 * Метод для добавления/обновления пачки данных в БД
	 * @param list список добавляемых объектов
	 * @param instanceName инстансе базы
	 * @param <T> тип объекта
	 * @return список добавляемых объектов
	 * @throws Exception
	 */
	public <T> List<T> addOrUpdateList(final List<T> list, String instanceName) throws Exception
	{
		HibernateExecutor trnExecutor = HibernateExecutor.getInstance(instanceName);
		return trnExecutor.execute(new HibernateAction<List<T>>()
		{
			public List<T> run(Session session) throws Exception
			{
				int count = 0;
				for (T obj : list)
				{
					session.saveOrUpdate(obj);
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
		});
	}
}
