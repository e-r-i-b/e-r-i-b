package com.rssl.auth.csa.back.servises.restrictions.security;

import com.rssl.auth.csa.back.exceptions.PasswordRestrictionException;
import com.rssl.auth.csa.back.exceptions.RestrictionException;
import com.rssl.auth.csa.back.servises.restrictions.*;

/**
 * @author krenev
 * @ created 22.11.2012
 * @ $Author$
 * @ $Revision$
 * ����������� �� �������������� ������ ��� ������� � ���� ����������� ������������.
 *
 * ���������� � ������� ������ �� ���� � ����������:
 * �	����������� ����� � 5 ����;
 * �	������������� ������� ������������������ ������ ������ ���� ��� � ������, ��� � �������� �������, �.�. ���� 12345, 23456, 765432;
 * �	�� ����� 3� ���������� ��������, ������ ������.
 */

public class MAPIPasswordSecurityRestriction extends CompositeRestriction<String>
{
	private static final MAPIPasswordSecurityRestriction instance = new MAPIPasswordSecurityRestriction();

	private MAPIPasswordSecurityRestriction()
	{

		super(
				new NotEmptyStringRestriction("������ �� ����� ���� ������"),
				new RegexpRestriction("^.{5,}$", "������ ������ ��������� �� ����� 5 ��������"),
				new NotSubstringRestriction("01234567890", "������ �� ����� �������� �� ������ ��������������� ����"),
				new NotSubstringRestriction("09876543210", "������ �� ����� �������� �� ������ ��������������� ����"),
				new SubsequenceRepeateSymbolsRestriction(3, "������ �� ������ ��������� ����� 3 ������������� �������� ������")
		);
	}

	public static MAPIPasswordSecurityRestriction getInstance()
	{
		return instance;
	}

	public void check(String object) throws Exception
	{
		try
		{
			super.check(object);
		}
		catch (PasswordRestrictionException e)
		{
			throw e;
		}
		catch (RestrictionException e)
		{
			throw new PasswordRestrictionException(e);
		}
	}
}