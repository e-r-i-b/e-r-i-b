package com.rssl.auth.csa.back.exceptions;

/**
 * ����������, ��������������� � ���, ��� ����� ����������� �� �������� ��������
 * @author niculichev
 * @ created 05.02.14
 * @ $Author$
 * @ $Revision$
 */
public class NotMainCardRegistrationException extends RestrictionException
{
	public NotMainCardRegistrationException(String message)
	{
		super(message);
	}
}
