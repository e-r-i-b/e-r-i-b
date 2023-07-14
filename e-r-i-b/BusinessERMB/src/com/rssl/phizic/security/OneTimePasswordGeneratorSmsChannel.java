package com.rssl.phizic.security;

import com.rssl.common.forms.validators.passwords.SmsPasswordConfig;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.RandomHelper;

/**
 * ��������� ����������� ������� ��� ���-������
 * @author Puzikov
 * @ created 08.09.14
 * @ $Author$
 * @ $Revision$
 */

class OneTimePasswordGeneratorSmsChannel
{
	private static final int MAX_TRIES = 10000;

	private final int length = ConfigFactory.getConfig(SmsPasswordConfig.class).getSmsPasswordLength();

	/**
	 * ������������� ��� ������������� �� �������� ���-������ ����:
	 * ��������� ������������������ ���� � �������������
	 * @return ������ ���� �������������
	 */
	String generate()
	{
		for (int i = 0; i < MAX_TRIES; i++)
		{
			String password = RandomHelper.rand(length, RandomHelper.DIGITS);
			if (check(password))
				return password;
		}
		throw new InternalErrorException("�� ������� ��������� ������");
	}

	//1.	��������� ����������� ��������� �������� ���� ������� 10000;
	//2.	��������� ����������� ��������� �������� ���� � ������ �������� �0�.
	private boolean check(String password)
	{
		if (password.startsWith("0"))
			return false;

		if ("10000".equals(password))
			return false;

		return true;
	}
}
