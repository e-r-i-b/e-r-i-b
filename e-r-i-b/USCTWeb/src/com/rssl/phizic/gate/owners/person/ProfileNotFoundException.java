package com.rssl.phizic.gate.owners.person;

/**
 * ����������, ��������� � ������ ���������� ������� � �� ����
 *
 * @author khudyakov
 * @ created 05.05.14
 * @ $Author$
 * @ $Revision$
 */
public class ProfileNotFoundException extends Exception
{
	private static final String ERROR_MESSAGE = "������ �� ������ � �� ����";

	public ProfileNotFoundException()
	{
		super(ERROR_MESSAGE);
	}
}
