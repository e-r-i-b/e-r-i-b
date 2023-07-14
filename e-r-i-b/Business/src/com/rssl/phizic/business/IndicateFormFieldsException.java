package com.rssl.phizic.business;

import java.util.Collections;
import java.util.Map;

/**
 * Ошибка, требующая указания пользователю, какие именно поля формы изменены по независящим от него причинам
 * либо заполнены некорректно и требуют редактирования.
 @author Pankin
 @ created 04.03.2011
 @ $Author$
 @ $Revision$
 */
public class IndicateFormFieldsException extends BusinessLogicException
{
	private final Map<String, String> fieldMessages;
	private final boolean isError;

	public IndicateFormFieldsException(Map<String, String> fieldMessages, boolean isError, Throwable cause)
	{
		super(cause.getMessage(), cause);
		this.isError = isError;
		this.fieldMessages = fieldMessages;
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
