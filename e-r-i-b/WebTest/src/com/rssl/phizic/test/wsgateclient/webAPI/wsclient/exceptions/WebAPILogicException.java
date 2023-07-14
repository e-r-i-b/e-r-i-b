package com.rssl.phizic.test.wsgateclient.webAPI.wsclient.exceptions;

import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * ���������� ��� ������������� ������ � ������ WebAPI.
 * @author Jatsky
 * @ created 13.11.13
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

