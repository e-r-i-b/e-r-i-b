package com.rssl.phizic.gate.exceptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @see com.rssl.common.forms.DocumentFieldsIndicateException
 *
 * @author bogdanov
 * @ created 11.08.15
 * @ $Author$
 * @ $Revision$
 */

public class GateFieldIncorrectException extends GateLogicException
{
	private final Map<String, String> fieldMessages;
	private final boolean isError;

	public GateFieldIncorrectException(Map<String, String> fieldMessages, boolean isError, String message)
	{
		super(message);
		this.isError = isError;
		this.fieldMessages = fieldMessages;
	}

	public GateFieldIncorrectException(Set<String> fieldNames, boolean isError, String message)
	{
		super(message);
		this.isError = isError;
		fieldMessages = new HashMap<String, String>();
		for (String fieldName : fieldNames)
		{
			fieldMessages.put(fieldName, "");
		}
	}

	public GateFieldIncorrectException(String fieldName, boolean isError, String message)
	{
		super(message);
		this.isError = isError;
		fieldMessages = new HashMap<String, String>();
		fieldMessages.put(fieldName, isError ? message : "");
	}

	/**
	 * @return карта имен полей, на которые нужно обратить внимание пользователя, и сообщений для полей
	 */
	public Map<String, String> getFieldMessages()
	{
		return Collections.unmodifiableMap(fieldMessages);
	}

	/**
	 * @return нужно ли записывать сообщения в ошибки
	 */
	public boolean isError()
	{
		return isError;
	}
}
