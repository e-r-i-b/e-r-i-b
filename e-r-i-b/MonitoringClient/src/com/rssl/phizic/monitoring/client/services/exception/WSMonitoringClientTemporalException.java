package com.rssl.phizic.monitoring.client.services.exception;

import com.rssl.phizic.gate.exceptions.TemporalGateException;

/**
 * @author akrenev
 * @ created 19.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class WSMonitoringClientTemporalException extends TemporalGateException
{
	public static final String MESSAGE_PREFIX = "PHIZ_TEMPORAL_GATE_EXCEPTION_START";
	public static final String MESSAGE_SUFFIX = "PHIZ_TEMPORAL_GATE_EXCEPTION_END";
	private String message;

	public WSMonitoringClientTemporalException(Throwable couse)
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
