package com.rssl.auth.csa.back.integration.ipas;

/**
 * @author krenev
 * @ created 24.01.2013
 * @ $Author$
 * @ $Revision$
 * ����������, ������������� � ������������ ������� iPas.
 * ����������� ���������� ������������ � ������������ ������ �� ipas
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
