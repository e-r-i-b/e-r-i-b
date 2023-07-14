package com.rssl.auth.csa.wsclient.exceptions;

/**
 * @author tisov
 * @ created 22.04.14
 * @ $Author$
 * @ $Revision$
 */

public class UserProfileBlockedException extends BackLogicException
{

	private static final String DEFAULT_MESSAGE = "��������� ������, ���� ������� ������ �������������. ��� ���� ����� ����������� ������������, ��������� � ���������� ����� �� ������ 8-800-555-5550 ��� ���������� � ��������� ��������� ��������� ������.";

	public UserProfileBlockedException()
	{
		super(DEFAULT_MESSAGE);
	}
}
