package com.rssl.phizgate.common.ws.exceptions;

import com.rssl.phizic.gate.exceptions.InvalidTargetException;

/**
 * @author krenev
 * @ created 04.10.2010
 * @ $Author$
 * @ $Revision$
 * InvalidTargetException для передачи через веб сервисы
 */
public class WSGateInvalidTargerException extends InvalidTargetException
{
	public static final String MESSAGE_PREFIX = "PHIZ_GATE_INVALID_TARGET_EXCEPTION_START";
	public static final String MESSAGE_SUFFIX = "PHIZ_GATE_INVALID_TARGET_EXCEPTION_END";
	private String message;

	public WSGateInvalidTargerException(Throwable couse)
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
