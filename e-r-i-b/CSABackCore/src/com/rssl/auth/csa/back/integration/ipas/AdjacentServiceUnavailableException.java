package com.rssl.auth.csa.back.integration.ipas;

/**
 * @author krenev
 * @ created 13.09.2013
 * @ $Author$
 * @ $Revision$
 * ����������, ��������������� � ������������� ������� c ipas ��������(� ��������� way4).
 * ���������� �������� ��� ������ �� iPas, ����������� ������������� ������� � ��� ������� ������.
 *
 */

public class AdjacentServiceUnavailableException extends ServiceUnavailableException
{
	private final String errorCode;

	/**
	 * �����������
	 * @param errorCode ��� ������
	 */
	public AdjacentServiceUnavailableException(String errorCode)
	{
		super("������� ����� � ������������� ������� �������� �� iPas: " + errorCode);
		this.errorCode = errorCode;
	}

	/**
	 * @return ��� ������ �� iPas
	 */
	public String getErrorCode()
	{
		return errorCode;
	}
}
