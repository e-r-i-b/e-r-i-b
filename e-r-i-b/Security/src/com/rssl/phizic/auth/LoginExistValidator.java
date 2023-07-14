package com.rssl.phizic.auth;

import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.password.BlockedException;
import com.rssl.phizic.security.password.NamePasswordValidator;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * �������� �� ������������� ������ ������������
 * @author eMakarov
 * @ created 04.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class LoginExistValidator implements NamePasswordValidator
{
	private static final SecurityService securityService = new SecurityService();

	/**
	 * ��������� ���������� ��� ������������ (� ������ ����� �� ����� ��������� ������)
	 * @param userId userId ������������
	 * @param password ������
	 * @return login ��������� �� ���� id
	 * @exception com.rssl.phizic.security.SecurityLogicException �������� login\password,
	 * ��������� ������� ������ (�������������) etc
	 * @exception SecurityException ������ ������ ��� ���������
	 */
	public CommonLogin validateLoginInfo(String userId, char[] password) throws SecurityLogicException, SecurityException
	{
		Login login;
		try
		{
			login = securityService.getClientLogin(userId);
		}
		catch (SecurityDbException e)
		{
			throw new SecurityException(e);
		}
		if (login == null)
		{
			// ���� ��� ������, �� ��� ����� �����
			throw new SecurityLogicException("������� ������ �����.");
		}

		return validateLoginInfo(login);
	}

	/**
	 * ��������� ���������� ������
	 * @param login �����
	 * @return �����
	 */
	public CommonLogin validateLoginInfo(Login login) throws BlockedException
	{
		Calendar blockUntil = new GregorianCalendar();
		List<LoginBlock> blocks = securityService.getBlocksForLogin(login, blockUntil.getTime(), null);
		if (blocks.size() != 0)
		{
			throw new BlockedException("������ �����������. ������ � ������� ��������.");
		}
		return login;
	}
}
