package com.rssl.phizic.common.types;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Collections;
import java.io.Serializable;

/**
 * @author Erkin
 * @ created 27.02.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Коллектор сообщений: уведомлений и ошибок
 */
public final class MessageCollector implements Serializable
{
	private final Collection<String> messages = new LinkedHashSet<String>();

	private final Collection<String> errors = new LinkedHashSet<String>();

	private final Collection<String> inactiveSystemErrors = new LinkedHashSet<String>();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Возвращает собранные уведомления
	 * @return коллекция собранных сообщений-уведомлений (never null)
	 */
	public Collection<String> getMessages()
	{
		return Collections.unmodifiableCollection(messages);
	}

	/**
	 * Возвращает собранные ошибки
	 * @return коллекция собранных сообщений-ошибок (never null)
	 */
	public Collection<String> getErrors()
	{
		return Collections.unmodifiableCollection(errors);
	}

	/**
	 * Возвращает собранные ошибки о недосутпности внешних систем.
	 * @return коллекция собранных сообщений-ошибок (never null)
	 */
	public Collection<String> getInactiveErrors()
	{
		return Collections.unmodifiableCollection(inactiveSystemErrors);
	}

	/**
	 * Добавляет одно уведомление
	 * @param message - текст сообщения-уведомления
	 */
	public void addMessage(String message)
	{
		if (message == null || message.length() == 0)
			throw new IllegalArgumentException("Аргумент 'message' не может быть пустым");
		this.messages.add(message);
	}

	/**
	 * Добавляет одну ошибку
	 * @param error - текст сообщения-ошибки
	 */
	public void addError(String error)
	{
		if (error == null || error.length() == 0)
			throw new IllegalArgumentException("Аргумент 'error' не может быть пустым");
		this.errors.add(error);
	}

	/**
	 * Добавляет одну ошибку к сообщениям о неактивности внешней системы.
	 * @param error - текст сообщения-ошибки
	 */
	public void addInactiveSystemError(String error)
	{
		if (error == null || error.length() == 0)
			throw new IllegalArgumentException("Аргумент 'error' не может быть пустым");
		this.inactiveSystemErrors.add(error);
	}

	/**
	 * Добавляет несколько уведомлений
	 * @param messages - коллекция сообщений-уведомлений
	 */
	public void addMessages(Collection<String> messages)
	{
		this.messages.addAll(messages);
	}

	/**
	 * Добавляет несколько ошибок
	 * @param errors - коллекция сообщений-ошибок
	 */
	public void addErrors(Collection<String> errors)
	{
		this.errors.addAll(errors);
	}

	/**
	 * Добавляет несколько ошибок к сообщениям о неативности внешней системы.
	 * @param errors - коллекция сообщений-ошибок
	 */
	public void addInactiveSystemErrors(Collection<String> errors)
	{
		this.inactiveSystemErrors.addAll(errors);
	}

	/**
	 * Удаляет собранные сообщения (уведомления и ошибки)
	 */
	public void clear()
	{
		this.messages.clear();
		this.errors.clear();
		this.inactiveSystemErrors.clear();
	}

	/**
	 * Проверяет все контейнеры на пустоту
	 * @return true если все контейнеры пусты
	 */
	public boolean isEmpty()
	{
		return messages.isEmpty() && errors.isEmpty() && inactiveSystemErrors.isEmpty();
	}

	/**
	 * Проверяет, что хотя бы один из контейнеров не пуст
	 * @return true если не пуст хотя бы один
	 */
	public boolean isNotEmpty()
	{
		return !isEmpty();
	}
}
