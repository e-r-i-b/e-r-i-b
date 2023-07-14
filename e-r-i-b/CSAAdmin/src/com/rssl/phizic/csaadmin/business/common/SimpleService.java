package com.rssl.phizic.csaadmin.business.common;

import com.rssl.phizic.dataaccess.DatabaseServiceBase;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * @author akrenev
 * @ created 30.09.13
 * @ $Author$
 * @ $Revision$
 *
 * Базовый сервис
 */

public abstract class SimpleService<E>
{
	private static final DatabaseServiceBase databaseService = new DatabaseServiceBase();

	protected abstract Class<E> getEntityClass();

	protected String getInstanceName()
	{
		return "CSAAdmin";
	}

	protected String getIdFieldName()
	{
		return "id";
	}

	/**
	 * получить сущность по идентификатору
	 * @param id идентиыикатор
	 * @return найденная сущность
	 * @throws AdminException
	 */
	public E findById(Object id) throws AdminException
	{
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass()).add(Expression.eq(getIdFieldName(), id));
			return databaseService.<E>findSingle(criteria, LockMode.READ, getInstanceName());
		}
		catch (Exception e)
		{
			throw new AdminException(e);
		}
	}

	/**
	 * сохранить сущность
	 * @param entity сущность
	 * @return сохраненная сущность
	 * @throws AdminException
	 */
	public E save(E entity) throws AdminException
	{
		try
		{
			return databaseService.addOrUpdate(entity, getInstanceName());
		}
		catch (ConstraintViolationException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new AdminException(e);
		}
	}

	/**
	 * удалить сущность
	 * @param entity сущность
	 * @return удаленная сущность
	 * @throws AdminException
	 */
	public E delete(E entity) throws AdminException, AdminLogicException
	{
		try
		{
			return databaseService.delete(entity, getInstanceName());
		}
		catch (ConstraintViolationException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new AdminException(e);
		}
	}

	protected <T> List<T> find(DetachedCriteria criteria) throws AdminException
	{
		try
		{
			return databaseService.find(criteria, LockMode.READ, getInstanceName());
		}
		catch (Exception e)
		{
			throw new AdminException(e);
		}
	}

	/**
	 * найти единственный объект по критерию
	 * @param criteria - критерий поиска
	 * @param <E> - тип сущности
	 * @return объект
	 * @throws AdminException
	 */
	protected <E> E findSingle(DetachedCriteria criteria) throws AdminException
	{
		try
		{
			return databaseService.<E>findSingle(criteria, LockMode.READ, getInstanceName());
		}
		catch (Exception e)
		{
			throw new AdminException(e);
		}
	}

	protected HibernateExecutor getExecutor()
	{
		return HibernateExecutor.getInstance(getInstanceName());
	}

	protected <E> E execute(HibernateAction<E> action) throws AdminException, AdminLogicException
	{
		try
		{
			return getExecutor().execute(action);
		}
		catch (ConstraintViolationException e)
		{
			throw e;
		}
		catch (AdminLogicException e)
		{
			throw e;
		}
		catch (AdminException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new AdminException(e);
		}
	}
}
