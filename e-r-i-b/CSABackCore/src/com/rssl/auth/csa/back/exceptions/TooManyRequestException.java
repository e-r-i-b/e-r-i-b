package com.rssl.auth.csa.back.exceptions;

/**
 * @author krenev
 * @ created 19.09.2012
 * @ $Author$
 * @ $Revision$
 * ����������, ��������������� � ���, ��� ������� ������� ����� ���������������� ��������
 */
public class TooManyRequestException extends RestrictionException
{
	public TooManyRequestException(Long profileId)
	{
		super("��� ������� " + profileId + " ��������� ���������� ���������������� �������� ");
	}

	public TooManyRequestException(String message)
	{
		super(message);
	}
}
