package com.rssl.phizic.locale.services;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.dataaccess.DatabaseServiceBase;
import com.rssl.phizic.locale.entities.ERIBLocale;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * Сервис для работы с мультиязычными локалями
 * @author koptyaev
 * @ created 10.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Сервис для работы с локалями
 */
public class MultiInstanceEribLocaleService
{
	private static final DatabaseServiceBase databaseService = new DatabaseServiceBase();

	/**
	 * Добавить или обновить локаль
	 * @param locale локаль
	 * @param instanceName имя инстанса БД
	 * @return добавлленная локаль
	 * @throws SystemException
	 */
	public ERIBLocale addOrUpdate(ERIBLocale locale, String instanceName) throws SystemException
	{
		try
		{
			return databaseService.addOrUpdate(locale, instanceName);
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}

	/**
	 * Обновить локаль
	 * @param locale локаль
	 * @param instanceName имя инстанса БД
	 * @return обновлённая локаль
	 * @throws SystemException
	 */
	public ERIBLocale update(ERIBLocale locale, String instanceName) throws SystemException
	{
		try
		{
			return databaseService.update(locale, instanceName);
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}
	/**
	 * Добавить локаль
	 * @param locale локаль
	 * @param instanceName имя инстанса БД
	 * @return добавленная локаль
	 * @throws SystemException
	 */
	public ERIBLocale add(ERIBLocale locale, String instanceName) throws SystemException
	{
		try
		{
			return databaseService.add(locale, instanceName);
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}

	/**
	 * Удалить локаль
	 * @param locale локаль
	 * @param instanceName имя инстанса БД
	 * @throws SystemException
	 */
	public void delete(ERIBLocale locale, String instanceName) throws SystemException
	{
		try
		{
			databaseService.delete(locale, instanceName);
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}

	/**
	 * Получить локаль по id
	 * @param id идентификатор
	 * @param instanceName имя инстанса БД
	 * @return локаль
	 * @throws SystemException
	 */
	public ERIBLocale getById(String id, String instanceName) throws SystemException
	{
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(ERIBLocale.class).add(Expression.eq("id", id));
			return databaseService.findSingle(criteria, null, instanceName);
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}

	/**
	 * Получить весь список локалей
	 * @param instanceName имя инстанса БД
	 * @return список локалей
	 * @throws SystemException
	 */
	public List<ERIBLocale> getAll(String instanceName) throws SystemException
	{
		try
		{
			return databaseService.find(DetachedCriteria.forClass(ERIBLocale.class), null, instanceName);
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}
}
