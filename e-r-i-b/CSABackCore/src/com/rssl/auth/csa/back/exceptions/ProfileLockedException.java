package com.rssl.auth.csa.back.exceptions;

/**
 * @author krenev
 * @ created 15.08.2013
 * @ $Author$
 * @ $Revision$
 * ����������, ��������������� � ���������������� �������.
 */

public class ProfileLockedException extends RestrictionException
{
	public ProfileLockedException(String message)
	{
		super(message);
	}
}
