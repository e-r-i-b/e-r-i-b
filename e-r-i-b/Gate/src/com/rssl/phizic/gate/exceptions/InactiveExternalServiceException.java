package com.rssl.phizic.gate.exceptions;

/**
 * @author mihaylov
 * @ created 21.01.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� � ������������� ������� �� ������� ��������.
 * ����������, ��� ������ � ������� ������ ���� �������.
 */
public class InactiveExternalServiceException extends GateLogicException
{
	public static final String MESSAGE_PREFIX = "PHIZ_GATE_INACTIVE_SERVICE_EXCEPTION_START";
	public static final String MESSAGE_SUFFIX = "PHIZ_GATE_INACTIVE_SERVICE_EXCEPTION_END";
	private String message;

	public InactiveExternalServiceException(String message)
	{
		super(message);
		setMessage(message);
	}

	public InactiveExternalServiceException(Throwable cause)
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
