package com.rssl.auth.csa.back.exceptions;

import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * @author akrenev
 * @ created 24.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ������ �������
 */

public class ClientNotFoundException extends LogicException
{
	/**
	 * ctor
	 * @param message ��������� �� ������
	 */
	public ClientNotFoundException(String message)
	{
		super(message);
	}

	/**
	 * �����������
	 * @param cause ���������� ����������
	 */
	public ClientNotFoundException(Throwable cause)
	{
		super(cause);
	}
}
