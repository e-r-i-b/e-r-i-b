package com.rssl.phizic.monitoring.client.services.exception;

import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author akrenev
 * @ created 19.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class WSMointoringClientLogicException extends GateLogicException
{
	public static final String MESSAGE_PREFIX = "PHIZ_GATE_LOGIC_EXCEPTION_START";
	public static final String MESSAGE_SUFFIX = "PHIZ_GATE_LOGIC_EXCEPTION_END";
	private String message;

	public WSMointoringClientLogicException(Throwable cause)
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

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getMessage()
	{
		return message;
	}
}
