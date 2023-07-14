package com.rssl.auth.csa.back.servises;

import com.rssl.phizic.dataaccess.DatabaseServiceBase;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.logging.Log;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;

import java.util.Calendar;
import java.util.List;

/**
 * @author krenev
 * @ created 28.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class ActiveRecord
{
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Gate);
	protected static final DatabaseServiceBase databaseService = new DatabaseServiceBase();
	protected static String getInstanceName()
	{
		return null;
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
	public static <T> T findById(final Class<T> clazz, final Long id, LockMode lockMode,String instanceName) throws Exception
	{
		return databaseService.findById(clazz, id, lockMode, getInstanceName());
	}

	/**
	 * сохранить сущность.
	 */
	public void save() throws Exception
	{
		databaseService.addOrUpdate(this, getInstanceName());
	}

	/**
	 * Удалить сущность
	 */
	public void delete() throws Exception
	{
		databaseService.delete(this, getInstanceName());
	}

	/**
	 * Найти 1 объект по критерию
	 * @param criteria критерий
	 * @param mode режим блокировки.
	 * @param <T> тип результата
	 * @return значение или null
	 * @throws Exception
	 */
	public static <T> T findSingle(DetachedCriteria criteria, LockMode mode) throws Exception
	{
		List<T> results = databaseService.find(criteria, mode, getInstanceName());
		return results.size() == 1 ? results.get(0) : null;
	}

	/**
	 * Найти список объектов по критерию
	 * @param criteria критерий
	 * @param mode тип блокировки
	 * @param <T> тип результата
	 * @return список сущностей
	 * @throws Exception
	 */
	public static <T> List<T> find(DetachedCriteria criteria, LockMode mode) throws Exception
	{
		return databaseService.find(criteria, mode, getInstanceName());
	}

	/**
	 * Выполнить действие в транзакции без учета "вложенных транзакций".
	 * @param action действие
	 * @param <T> тип результата
	 * @return результат
	 * @throws Exception
	 */
	public static <T> T executeIsolated(HibernateAction<T> action) throws Exception
	{
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = HibernateExecutor.getSessionFactory(getInstanceName()).openSession();
			transaction = session.beginTransaction();
			T result = action.run(session);
			transaction.commit();
			return result;
		}
		catch (Exception e)
		{
			if (transaction != null)
			{
				transaction.rollback();
			}
			throw e;
		}
		finally
		{
			if (session != null)
			{
				session.close();
			}
		}
	}

	/**
	 * @return Текущие дата и время сервера БД c его таймзоной
	 */
	public static Calendar getCurrentDate()
	{
		try
		{
			return databaseService.getDatabaseSysDate(getInstanceName());
		}
		catch (Exception e)
		{
			log.error("Ошибка получения текущей системной даты сервера БД", e);
			throw new RuntimeException("Ошибка получения текущей системной даты сервера БД", e);
		}
	}

	/**
	 * Выполнить действие атомарно (в транзакации  с учетом вложенных транзакций)
	 * @param hibernateAction действие
	 * @param <T> тип результа
	 * @return результат
	 */
	public static <T> T executeAtomic(HibernateAction<T> hibernateAction) throws Exception
	{
		return getHibernateExecutor().execute(hibernateAction);
	}

	protected static HibernateExecutor getHibernateExecutor()
	{
		return HibernateExecutor.getInstance(getInstanceName());
	}
}
