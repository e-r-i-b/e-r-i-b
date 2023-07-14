package com.rssl.phizic.web.webApi.exceptions;

import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * Ошибка "невалидная сессия"
 * @author Jatsky
 * @ created 04.12.13
 * @ $Author$
 * @ $Revision$
 */

public class InvalidSessionException extends LogicException
{
	private static final String DEFAULT_MESSAGE = "Не найдено ни одной сессии";

	public InvalidSessionException()
	{
		this(DEFAULT_MESSAGE);
	}

	public InvalidSessionException(String message)
	{
		super(message);
	}
}