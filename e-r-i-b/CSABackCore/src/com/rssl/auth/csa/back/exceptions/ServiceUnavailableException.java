package com.rssl.auth.csa.back.exceptions;

/**
 * @author krenev
 * @ created 24.01.2013
 * @ $Author$
 * @ $Revision$
 * ����������, �������������� � ������������� ������-���� �������
 */
public class ServiceUnavailableException extends Exception
{
	public ServiceUnavailableException(Exception e)
	{
		super(e);
	}

	public ServiceUnavailableException(String message)
	{
		super(message);
	}
}
