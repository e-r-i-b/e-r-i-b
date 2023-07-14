package com.rssl.auth.csa.back.integration.dasreda;

/**
 * @author akrenev
 * @ created 26.03.2013
 * @ $Author$
 * @ $Revision$
 *
 * ���������� �������������� � ������� ������
 */

public class BusinessEnvironmentException extends Exception
{
	/**
	 * �����������
	 * @param cause ����������-���������
	 */
	public BusinessEnvironmentException(Exception cause)
	{
		super(cause);
	}

	/**
	 * �����������
	 * @param message ���������
	 */
	public BusinessEnvironmentException(String message)
	{
		super(message);
	}
}
