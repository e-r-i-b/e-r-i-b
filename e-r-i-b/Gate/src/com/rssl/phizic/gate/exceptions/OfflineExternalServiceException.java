package com.rssl.phizic.gate.exceptions;

/**
 * @author mihaylov
 * @ created 21.01.2013
 * @ $Author$
 * @ $Revision$
 */
/**
 * Исключение о недоступности сервиса за внешней системой.
 * Показывает, что запрос в бизнесе должен быть переведен в офлайн режим.
 */
public class OfflineExternalServiceException extends GateLogicException
{
	public static final String MESSAGE_PREFIX = "PHIZ_GATE_OFFLINE_SERVICE_EXCEPTION_START";
	public static final String MESSAGE_SUFFIX = "PHIZ_GATE_OFFLINE_SERVICE_EXCEPTION_END";
	private String message;

	public OfflineExternalServiceException(String message)
	{
		super(message);
		setMessage(message);
	}

	public OfflineExternalServiceException(Throwable cause)
	{
		super(cause);
		String message = cause.getMessage();
		int start = message.indexOf(MESSAGE_PREFIX);
		if (start < 0)
		{
			return;
		}
		int end = message.indexOf(MESSAGE_SUFFIX);
		if (end < 0)
		{
			return;
		}
		start += MESSAGE_PREFIX.length();
		setMessage(message.substring(start, end));
	}	

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}
}
