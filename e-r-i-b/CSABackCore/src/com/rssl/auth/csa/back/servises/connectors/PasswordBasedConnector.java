package com.rssl.auth.csa.back.servises.connectors;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.Password;
import com.rssl.auth.csa.back.servises.restrictions.Restriction;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 05.10.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class PasswordBasedConnector extends Connector
{
	public final CSAUserInfo checkPassword(String password) throws Exception
	{
		if (getPassword().check(password))
		{
			return asUserInfo();
		}
		return null;
	}

	/**
	 * ��������/���������� ������ ��� ���������
	 * @param password ������
	 * @return ������� �� ������.
	 * @throws Exception
	 */
	public final boolean changePassword(final String password) throws Exception
	{
		getPasswordRestriction().check(password);
		return setPassword(password);
	}
	/**
	 * ���������� ������ ��� ����������.
	 * @param password �������� ������
	 * @return ������� �� ������.
	 * @throws Exception
	 */
	protected abstract boolean setPassword(final String password) throws Exception;

	protected abstract Password getPassword() throws Exception;

	public final Calendar getPasswordCreationDate() throws Exception
	{
		Password password = getPassword();
		return password == null ? null : password.getCreationDate();
	}

	/**
	 * @return ����������� �� ������
	 */
	public abstract Restriction<String> getPasswordRestriction() throws Exception;
}
