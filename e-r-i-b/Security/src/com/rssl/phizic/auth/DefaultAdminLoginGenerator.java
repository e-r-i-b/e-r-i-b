package com.rssl.phizic.auth;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 20.06.2014
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ������ ���������� ����������
 */

public class DefaultAdminLoginGenerator extends BankLoginGenerator
{
	/**
	 * �����������
	 * @param userId    �����
	 * @param password  ������
	 */
	public DefaultAdminLoginGenerator(String userId, String password)
	{
		super(userId, password);
	}

	@Override
	protected LoginBase newInstance()
	{
		BankLoginImpl login = (BankLoginImpl) super.newInstance();
		login.setLastSynchronizationDate(Calendar.getInstance());
		return login;
	}
}
