package com.rssl.phizic.web.wsclient.webApi.exceptions;

import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * ���������� ��� ������������� ������ � ������ WebAPI.
 * @author Jatsky
 * @ created 22.04.14
 * @ $Author$
 * @ $Revision$
 */

public class WebAPILogicException extends LogicException
{
	public static final String DEFAULT_ERROR_MESSAGE = "�������� �������� ����������, ��������� ������� �����.";

	public WebAPILogicException()
	{
		super(DEFAULT_ERROR_MESSAGE);
	}

	public WebAPILogicException(String message)
	{
		super(message);
	}

	public WebAPILogicException(Throwable cause)
	{
		super(cause);
	}

	public WebAPILogicException(String message, Throwable cause)
	{
		super(message, cause);
	}
}

