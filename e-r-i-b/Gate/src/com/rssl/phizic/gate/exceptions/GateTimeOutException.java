package com.rssl.phizic.gate.exceptions;

/**
 * Возникновение таймаута
 * @author niculichev
 * @ created 09.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class GateTimeOutException extends GateLogicException
{
	public static final String MESSAGE_PREFIX = "PHIZ_GATE_TIMEOUT_EXCEPTION_START";
	public static final String MESSAGE_SUFFIX = "PHIZ_GATE_TIMEOUT_EXCEPTION_END";
	private String message;

	public GateTimeOutException(Throwable couse)
	{
		super(couse);
		String message = couse.getMessage();
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

	public GateTimeOutException(String mesasage)
	{
		super(mesasage);
		setMessage(mesasage);
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public GateTimeOutException(String mesasage, Throwable cause)
	{
		super(mesasage, cause);
		setMessage(mesasage);
	}

	public String getMessage()
	{
		return message;
	}
}
