package com.rssl.phizic.business;

import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 26.09.2005
 * Time: 17:18:00
 */
public class SimpleService extends MultiInstanceSimpleService
{

	public <T> T add(final T obj) throws BusinessException
	{
		return super.add(obj, null);
	}

	public <T> List<T> addList(final List<T> list) throws BusinessException
	{
		return super.addList(list, null);
	}

	public <T> T addOrUpdateWithConstraintViolationException(final T obj) throws BusinessException, ConstraintViolationException
	{
		return super.addOrUpdateWithConstraintViolationException(obj, null);
	}

	public <T> T addOrUpdate(final T obj) throws BusinessException
	{
		return super.addOrUpdate(obj, null);
	}

	/**
	 * Метод для добавления/модификации сразу списка объектов
	 * @param list
	 * @param <T>
	 * @return
	 * @throws BusinessException
	 */
	public <T> List<T> addOrUpdateList(final List<T> list) throws BusinessException
	{
		return super.addOrUpdateList(list, null);
	}
	public <T> List<T> addOrUpdateListWithConstraintViolationException(final List<T> list) throws BusinessException, ConstraintViolationException
	{
		return super.addOrUpdateListWithConstraintViolationException(list, null);
	}

    public <T> T merge(final T obj) throws BusinessException
    {
        return super.merge(obj, null);
    }

    public <T> List<T> merge(final List<T> list) throws BusinessException
    {
        return super.merge(list, null);
    }
	public <T> List<T> executeQuery(final String queryName, final Object bean) throws BusinessException
	{
		return super.executeQuery(queryName, bean, null);
	}

	public <T> T executeQuerySingle(final String queryName, final Object bean) throws BusinessException
	{
		return super.<T>executeQuerySingle(queryName, bean, null);
	}

	public <T> List<T> find(final DetachedCriteria detachedCriteria) throws BusinessException
	{
		return super.find(detachedCriteria, null);
	}

	public <T> List<T> find(final DetachedCriteria detachedCriteria, final int maxResults) throws BusinessException
	{
		return super.find(detachedCriteria, maxResults, null);
	}

	public <T> List<T> find(final T obj) throws BusinessException
	{
		return super.find(obj, null);
	}

	public <T> T findById(final Class<T> clazz, final Long id) throws BusinessException
	{
		return super.findById(clazz, id, null);
	}

	public <T> List<T> findByIds(final Class<T> clazz, final List<Long> ids) throws BusinessException
	{
		return super.<T>findByIds(clazz, ids, null);
	}

	public <T> T findSingle(final DetachedCriteria detachedCriteria) throws BusinessException
	{
		return super.<T>findSingle(detachedCriteria, null);
	}

	public <T> T findSingle(final T obj) throws BusinessException
	{
		return super.findSingle(obj, null);
	}

	public <T> List<T> getAll(final Class<T> clazz) throws BusinessException
	{
		return super.getAll(clazz, null);
	}

	public <T> T refresh(final T obj) throws BusinessException
	{
		return super.refresh(obj, null);
	}

    public <T> void removeWithConstraintViolationException(final T obj) throws BusinessException, ConstraintViolationException
	{
		super.removeWithConstraintViolationException(obj, null);
	}

	public <T> void remove(final T obj) throws BusinessException
	{
		super.remove(obj, null);
	}

	public void removeList(final List<?> list) throws BusinessException
	{
		super.removeList(list, null);
	}

	public <T> T update(final T obj) throws BusinessException
	{
		return super.update(obj, null);
	}
}
