package com.rssl.auth.csa.back.exceptions;

import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * ����������, ��������� � ���, ��� ��������� ������� �� ������.
 * @author niculichev
 * @ created 31.01.15
 * @ $Author$
 * @ $Revision$
 */
public class GuestProfileNotFoundException extends LogicException
{
	public GuestProfileNotFoundException(String message)
	{
		super(message);
	}
}
