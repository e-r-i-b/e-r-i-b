package com.rssl.auth.csa.back.exceptions;

import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * ����������, ��������� � ���, ��� ����� ��� ��������������� � �������
 * @author niculichev
 * @ created 31.01.15
 * @ $Author$
 * @ $Revision$
 */
public class GuestAlreadyRegistrationException extends LogicException
{
	public GuestAlreadyRegistrationException(String message)
	{
		super(message);
	}
}
