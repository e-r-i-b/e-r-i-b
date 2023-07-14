package com.rssl.phizic.business.dictionaries.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.business.image.ImageService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Collections;
import java.util.List;

/**
 * @author akrenev
 * @ created 15.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Базовый сервис справочников ПФП
 */

public class PFPDictionaryServiceBase extends SimpleService
{
	private static final ImageService imageService = new ImageService();
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();

	/**
	 * @param clazz класс
	 * @param ids идентификатор сущности
	 * @param instanceName имя инстанса модели БД
	 * @return сущности
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public <R> List<R> getListByIds(final Class<R> clazz, Long[] ids, String instanceName) throws BusinessException
	{
		if (ArrayUtils.isEmpty(ids))
			return Collections.emptyList();
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz).add(Expression.in("id", ids));
		return super.find(criteria, instanceName);
	}

	/**
	 * список сущностей одного класса с заданным порядком
	 * @param clazz класс сущностей
	 * @param order порядок
	 * @param instanceName имя инстанса модели БД
	 * @param <T> тип сущностей
	 * @return список
	 * @throws BusinessException
	 */
	public <T> List<T> getAll(final Class<T> clazz, Order order, String instanceName) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria.addOrder(order);
		return find(criteria, instanceName);
	}

	@Override
	public <T> T add(final T obj, final String instanceName) throws BusinessException
	{
		return execute(instanceName, new HibernateAction<T>()
		{
			public T run(Session session) throws BusinessException
			{
				PFPDictionaryServiceBase.super.add(obj, instanceName);
				addToLog((MultiBlockDictionaryRecordBase) obj, ChangeType.update);
				return obj;
			}
		}
		);
	}

	@Override
	public <T> T update(final T obj, final String instanceName) throws BusinessException
	{
		return execute(instanceName, new HibernateAction<T>()
		{
			public T run(Session session) throws BusinessException
			{
				PFPDictionaryServiceBase.super.update(obj, instanceName);
				addToLog((MultiBlockDictionaryRecordBase) obj, ChangeType.update);
				return obj;
			}
		}
		);
	}

	@Override
	public <T> T addOrUpdate(final T obj, final String instanceName) throws BusinessException
	{
		return execute(instanceName, new HibernateAction<T>()
		{
			public T run(Session session) throws BusinessException
			{
				PFPDictionaryServiceBase.super.addOrUpdateWithConstraintViolationException(obj, instanceName);
				addToLog((MultiBlockDictionaryRecordBase) obj, ChangeType.update);
				return obj;
			}
		}
		);
	}

	/**
	 * удалить сущность
	 * @param record удаляемая сущность
	 * @param instance имя инстанса модели БД
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public <R extends PFPDictionaryRecord> void removeWithImage(final R record, final String instance) throws BusinessException
	{
		execute(instance, new HibernateAction<Void>()
		{
			public Void run(Session session) throws BusinessException
			{
				session.delete(record);
				if (record.getImageId() != null)
					imageService.removeImageById(record.getImageId(), instance);
				addToLog(record, ChangeType.delete);
				return null;
			}
		}
		);
	}

	public <T> void remove(final T obj, final String instance) throws BusinessException
	{
		execute(instance, new HibernateAction<Void>()
		{
			public Void run(Session session) throws BusinessException
			{
				PFPDictionaryServiceBase.super.removeWithConstraintViolationException(obj, instance);
				addToLog((MultiBlockDictionaryRecordBase) obj, ChangeType.delete);
				return null;
			}
		}
		);
	}

	private <T> void addToLog(MultiBlockDictionaryRecordBase record, ChangeType changeType) throws BusinessException
	{
		dictionaryRecordChangeInfoService.addChangesToLog(record, changeType);
	}

	private <T> T execute(final String instance, HibernateAction<T> action) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(action);
		}
		catch (ConstraintViolationException e)
		{
			throw e;
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
}
