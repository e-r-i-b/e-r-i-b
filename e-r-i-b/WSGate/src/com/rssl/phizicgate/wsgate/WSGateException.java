package com.rssl.phizicgate.wsgate;

import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Krenev
 * @ created 23.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class WSGateException extends GateException
{
	public static final String MESSAGE_PREFIX = "PHIZ_GATE_EXCEPTION_START";
	public static final String MESSAGE_SUFFIX = "PHIZ_GATE_EXCEPTION_END";
	private String message;

	public WSGateException(Throwable couse)
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

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getMessage()
	{
		return message;
	}
}
