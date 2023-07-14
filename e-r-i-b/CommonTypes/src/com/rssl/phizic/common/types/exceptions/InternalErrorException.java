package com.rssl.phizic.common.types.exceptions;

/**
 * @author Erkin
 * @ created 07.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Блокирующее исключение.
 * Должно расцениваться как проблема, блокирующая дальнейшее выполнение текущей задачи.
 * Не предполагает сколько-нибудь содержательной обработки (т.е. необрабатываемое).
 * Должно быть перехвачено на "верхнем уровне" и выведено в системный журнал.
 * Для пользователя исключение выглядит как "операция временно недоступна".
 * Примеры:
 * - потеря связи с базой
 * - ошибка в SQL-запросе
 */
public class InternalErrorException extends RuntimeException
{
	/**
	 * ctor
	 */
	public InternalErrorException()
	{
	}

	/**
	 * ctor
	 * @param message
	 */
	public InternalErrorException(String message)
	{
		super(message);
	}

	/**
	 * ctor
	 * @param cause
	 */
	public InternalErrorException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * ctor
	 * @param message
	 * @param cause
	 */
	public InternalErrorException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
