package com.rssl.auth.csa.back.servises.restrictions.security;

import com.rssl.auth.csa.back.exceptions.LoginRestrictionException;
import com.rssl.auth.csa.back.exceptions.RestrictionException;
import com.rssl.auth.csa.back.servises.restrictions.*;
import com.rssl.auth.csa.back.servises.Connector;

/**
 * @author krenev
 * @ created 22.11.2012
 * @ $Author$
 * @ $Revision$
 * ����������� �� �������������� ������ ����������� ������������.
 *
 * ������������� ������ �������� �� ���� �������� ��� ���������� �������� � ����� ��������� �����, ��������: ivan12.
 * ������� ���� (������� ��� ���������) �� ����� ��������. ����� �������������� ������ ���� �� ����� 5-�� ��������.
 * ������������� �� ������ ��������� ����� 3� ���������� �������� ������ � ����� ��������� ��������� �������: �@�, �_�, �-�, �.�
 * ���������� � ������:
 * �	����� �� ����� 5 ��������;
 * �	�� ������ ��������� ����� 3� ���������� �������� ������;
 * �	����� ��������� �������� ���������� �� ������ � � � @ _ -  .�
 * �	�� ����� �������� �� 10 ����.
 * �	�� ����� �������� �� ����� �Z� � 10 ����.
 * �	�� ������ ��������� ����� 3 ��������, ������������� ������ �� ����������, ��������, qwer. TODO �����������
 */
public class LoginSecurityRestriction implements Restriction<String>
{
	private static final Restriction<String> statelessRestriction = new CompositeRestriction<String>(
			new NotEmptyStringRestriction("����� �� ����� ���� ������"),
			new NotIpasLoginRestriction("����� �� ����� �������� �� 10 ����"),
			new NotDisposableLoginRestriction("����� �� ����� �������� �� ����� �Z� � 10 ����."),
			new RegexpRestriction("^[\\d\\-\\.@_a-zA-Z]{5,32}$", "����� ������ �������� �� ���� ���������� �������� � ����� ��������� ��������� �������: �@�, �_�, �-�, �.�. ����� ������ ������ ���� �� ����� 5-�� ��������."),
			new SubsequenceRepeateSymbolsRestriction(3, "����� �� ����� ��������� ����� 3 ���������� �������� ������")
	);
	private Restriction<String> restriction;

	private LoginSecurityRestriction(Restriction<String> restriction)
	{
		this.restriction = restriction;
	}

	/**
	 * @return �������, �� ���������� �� ��������� (��) � ����������� ����������� ������ �� ��������.
	 */
	public static Restriction<String> getInstance()
	{
		return new LoginSecurityRestriction(statelessRestriction);
	}

	/**
	 * ���������� ������, ����������� ����������� � ��������� ����������� ����������.
	 * ����� �������������� �� ��� ��������� �����������. ��������, �������� �� ��������� ������
	 * @param connector ���������, � ��������� �������� ����������� ��������
	 * @return �������
	 */
	public static Restriction<String> getInstance(Connector connector)
	{
		if (connector == null)
		{
			throw new IllegalArgumentException("��������� �� ����� ���� null");
		}
		return getInstance();
	}

	public void check(String object) throws Exception
	{
		try
		{
			restriction.check(object);
		}
		catch (LoginRestrictionException e)
		{
			throw e;
		}
		catch (RestrictionException e)
		{
			throw new LoginRestrictionException(e);
		}
	}
}
