package com.rssl.phizic.common.types.exceptions;

/**
 * Ошибка таймаута запроса к БД
 * @author lepihina
 * @ created 24.10.13
 * @ $Author$
 * @ $Revision$
 */
public class DatabaseTimeoutException extends SystemException
{
	public DatabaseTimeoutException(Throwable cause) {
		super(cause);
	}
}
