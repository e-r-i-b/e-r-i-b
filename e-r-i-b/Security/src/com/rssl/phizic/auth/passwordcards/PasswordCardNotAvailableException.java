package com.rssl.phizic.auth.passwordcards;

import com.rssl.phizic.security.SecurityLogicException;

/**
 * �� �����-�� ������� ���������� ����������� �������� ������� � ����
 *
 * @author lepihina
 * @ created 13.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class PasswordCardNotAvailableException extends SecurityLogicException
{
	/**
	 * �����������
	 * @param message ���������
	 */
	public PasswordCardNotAvailableException(String message)
	{
		super(message);
	}
}
