package com.rssl.phizic.common.types.exceptions;

/**
 * Количество открытых курсоров превысило допустимый максимум
 * @author lepihina
 * @ created 25.10.13
 * @ $Author$
 * @ $Revision$
 */
public class TooManyDatabaseCursorsException extends SystemException
{
	public TooManyDatabaseCursorsException(Throwable cause) {
		super(cause);
	}
}