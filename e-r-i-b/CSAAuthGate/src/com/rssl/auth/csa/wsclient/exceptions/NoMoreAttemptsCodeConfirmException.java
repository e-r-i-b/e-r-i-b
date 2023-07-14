package com.rssl.auth.csa.wsclient.exceptions;

/**
 * ���������� ���������� ������� ������������� ��������
 *
 * @author akrenev
 * @ created 03.04.2013
 * @ $Author$
 * @ $Revision$
 */

public class NoMoreAttemptsCodeConfirmException extends InvalidCodeConfirmException
{
	/**
	 * �����������
	 * @param cause ���������� ���������� ������
	 */
	public NoMoreAttemptsCodeConfirmException(InvalidCodeConfirmException cause)
	{
		super(cause);
	}
}
