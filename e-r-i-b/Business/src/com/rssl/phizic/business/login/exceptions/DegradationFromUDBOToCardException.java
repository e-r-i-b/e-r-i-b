package com.rssl.phizic.business.login.exceptions;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * ���������� ��� ���������� ������� � "UDBO" �� "CARD". ������� ����������� �������� �������� �� ����.
 *
 * @ author: Gololobov
 * @ created: 30.08.13
 * @ $Author$
 * @ $Revision$
 */
public class DegradationFromUDBOToCardException extends BusinessLogicException
{
	private static final String MESSAGE = "��������� ������!\n" +
										  "� ����� � ������������ �������� �������������� ����������� ������������ �� %s "+
										  "��� �������� ���������� � �������� ������ �� ����� ������. ��� ����, ����� ������������ "+
										  "������������������� ����� ���������� ��������� ������� �������������� ����������� ������������.";

	private Login login;//����� �������

	public DegradationFromUDBOToCardException(Login personLogin, Calendar cancellationDate)
	{
		super(String.format(MESSAGE, String.valueOf(DateHelper.formatDateToStringWithPoint(cancellationDate))));
		if (personLogin != null)
		{
			this.login= personLogin;
		}
	}

	public Login getLogin()
	{
		return login;
	}

	public void setLogin(Login login)
	{
		this.login = login;
	}
}
