package com.rssl.phizic.locale.services;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.locale.entities.ERIBStaticMessage;

import java.util.List;

/**
 * Сервис для работы с мультиязычными текстовками
 * @author koptyaev
 * @ created 11.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class EribStaticMessageService extends MultiInstanceEribStaticMessageService
{
	/**
	 * Добавить запись
	 * @param message текстовка
	 * @return добавленная текстовка
	 * @throws SystemException
	 */
	public ERIBStaticMessage add(ERIBStaticMessage message) throws SystemException
	{
		return super.add(message, null);
	}

	/**
	 * Добавить запись
	 * @param messages текстовки
	 * @return добавленная текстовка
	 * @throws SystemException
	 */
	public List<ERIBStaticMessage> add(List<ERIBStaticMessage> messages) throws SystemException
	{
		return super.add(messages, null);
	}

	/**
	 * Обновить запись
	 * @param message текстовка
	 * @return обновлённая текстовка
	 * @throws SystemException
	 */
	public ERIBStaticMessage update(ERIBStaticMessage message) throws SystemException
	{
		return super.update(message, null);
	}

	/**
	 * Обновить запись
	 * @param message текстовка
	 * @return обновлённая текстовка
	 * @throws SystemException
	 */
	public List<ERIBStaticMessage> update(List<ERIBStaticMessage> message) throws SystemException
	{
		return super.update(message, null);
	}

	/**
	 * Удалить текстовку
	 * @param message текстовка
	 * @throws SystemException
	 */
	public void delete(ERIBStaticMessage message) throws SystemException
	{
		super.delete(message, null);
	}

	/**
	 * Удалить текстовку
	 * @param message текстовка
	 * @throws SystemException
	 */
	public void delete(List<ERIBStaticMessage> message) throws SystemException
	{
		super.delete(message, null);
	}

	/**
	 * Возвращает все записи
	 * @param localeId идентификатор локали
	 * @return текстовка
	 * @throws SystemException
	 */
	public List<ERIBStaticMessage> getAll(String localeId) throws SystemException
	{
		return super.getAll(localeId, null);
	}
}
