package com.rssl.auth.csa.back.servises.restrictions.security;

import com.rssl.auth.csa.back.servises.GuestProfile;
import com.rssl.auth.csa.back.servises.Login;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.restrictions.*;
import com.rssl.auth.csa.back.servises.connectors.CSAConnector;
import com.rssl.auth.csa.back.exceptions.PasswordRestrictionException;
import com.rssl.auth.csa.back.exceptions.RestrictionException;

/**
 * @author krenev
 * @ created 22.11.2012
 * @ $Author$
 * @ $Revision$
 * ����������� �� �������������� ������ ����������� ������������.
 *
 *	����� �� ����� 8 ��������;
 *	������ ��������� ������� 1 �����;
 *	�� ������ ��������� ����� 3� ���������� �������� ������;
 *	����� ��������� �������� ���������� �� ������ � � � ! @ # $ % ^ & * ( ) _ - + : ; , .�
 */

public class CSAPasswordSecurityRestriction implements Restriction<String>
{
	private static final Restriction<String> statelessRestriction = new CompositeRestriction<String>(
			new NotEmptyStringRestriction("������ �� ����� ���� ������."),
			new RegexpRestriction("^[\\d\\-\\.\\+\\(\\):;,!#$@%^&*_a-zA-Z]{8,30}$", "������ �������� ������������ �������."),
			new SubsequenceRepeateSymbolsRestriction(3, "������ �� ������ ��������� ����� 3 ������������� �������� ������.")
	);
	private Restriction<String> restriction;

	private CSAPasswordSecurityRestriction(Restriction<String> restriction)
	{
		this.restriction = restriction;
	}

	/**
	 * @return �������, �� ���������� �� ��������� (��) � ����������� ����������� ������ �� ��������.
	 */
	public static CSAPasswordSecurityRestriction getInstance()
	{
		return new CSAPasswordSecurityRestriction(statelessRestriction);
	}

	/**
	 * ���������� �������, ����������� ����������� � ��������� ����������� ��������� �������.
	 * ����� �������������� �� ��� ��������� �����������. ��������, �������� �� ������������� ������ �� ��������
	 * @param profile �������� �������, � ��������� �������� ����������� ��������
	 * @return �������
	 */
	public static Restriction<String> getInstance(GuestProfile profile) throws Exception
	{
		if (profile == null)
		{
			throw new IllegalArgumentException("��������� �� ���� ����� null");
		}
		return new CSAPasswordSecurityRestriction(new CompositeRestriction<String>
				(
						new NotEquilsRestriction<String>(profile.getLogin().toUpperCase(), "������ �� ����� ��������� � �������"),
						statelessRestriction,
						new GuestPasswordHistoryRestriction(profile, 3)
				));
	}

	/**
	 * ���������� ������, ����������� ����������� � ��������� ����������� ����������.
	 * ����� �������������� �� ��� ��������� �����������. ��������, �������� �� ������������� ������ �� ��������
	 * @param connector ���������, � ��������� �������� ����������� ��������
	 * @return �������
	 */
	public static Restriction<String> getInstance(CSAConnector connector) throws Exception
	{
		if (connector == null)
		{
			throw new IllegalArgumentException("��������� �� ���� ����� null");
		}
		return new CSAPasswordSecurityRestriction(new CompositeRestriction<String>
				(
						new NotEquilsRestriction<String>(connector.getLogin().toUpperCase(), "������ �� ����� ��������� � �������"),
						statelessRestriction,
						new PasswordHistoryRestriction(connector.getProfile(), 3)
				));
	}

	/**
	 * ���������� �������, ����������� ����������� � ��������� ����������� �������.
	 * ����� �������������� �� ��� ��������� �����������. ��������, �������� �� ������������� ������ �� ��������
	 * @param profile �������, � ��������� �������� ����������� ��������
	 * @return �������
	 */
	public static Restriction<String> getInstance(Profile profile)
	{
		return new CSAPasswordSecurityRestriction(new CompositeRestriction<String>
				(
						statelessRestriction,
						new PasswordHistoryRestriction(profile, 3)
				));
	}

	public void check(String object) throws Exception
	{
		try
		{
			restriction.check(object.toUpperCase());
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