package com.rssl.phizicgate.wsgate;

import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author Krenev
 * @ created 23.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class WSGateLogicException extends GateLogicException
{
	public static final String MESSAGE_PREFIX = "PHIZ_GATE_LOGIC_EXCEPTION_START";
	public static final String MESSAGE_SUFFIX = "PHIZ_GATE_LOGIC_EXCEPTION_END";
	public static final String ERROR_CODE_PREFIX = "PHIZ_GATE_LOGIC_EXCEPTION_ERROR_CODE_PREFIX";
	public static final String ERROR_CODE_SUFFIX = "PHIZ_GATE_LOGIC_EXCEPTION_ERROR_CODE_SUFFIX";
	private String message;
	private String errCode;

	public WSGateLogicException(Throwable couse)
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

		int errCodeStart = message.indexOf(ERROR_CODE_PREFIX);
		if (errCodeStart < 0)
		{
			return;
		}

		int errCodeEnd = message.indexOf(ERROR_CODE_SUFFIX);
		if (end < 0)
		{
			return;
		}

		errCodeStart += ERROR_CODE_PREFIX.length();
		setErrCode(message.substring(errCodeStart, errCodeEnd));
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getMessage()
	{
		return message;
	}

	public void setErrCode(String errCode)
	{
		this.errCode = errCode;
	}

	public String getErrCode()
	{
		return errCode;
	}
}
