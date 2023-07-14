package com.rssl.phizic.business.login.exceptions;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;

/**
 *  ���������� ��������� ��� ��������� ������������� �������
 * (� ���������� login �������� ����� �������, ��� ������� ����������� �������� �������������)
 *
 * @author khudyakov
 * @ created 24.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class StopClientSynchronizationException extends BusinessException
{
	private Login login;                                //����� �������
	private boolean showMessage;                        //���������� �� ��������� �� ������ �������

	public StopClientSynchronizationException(String message)
	{
		super(message);
	}

	public StopClientSynchronizationException(Throwable cause)
	{
		super(cause);
	}

	public StopClientSynchronizationException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public StopClientSynchronizationException(ActivePerson person, String message)
	{
		super(message);

		if (person != null)
		{
			login = person.getLogin();
		}
	}

	public StopClientSynchronizationException(ActivePerson person, Throwable cause)
	{
		this(person, cause.getMessage());
		showMessage = cause instanceof InactiveExternalSystemException;
	}

	/**
	 * @return ����� �������
	 */
	public Login getLogin()
	{
		return login;
	}

	/**
	 * @return ���������� �� ��������� �� ������ �������
	 */
	public boolean isShowMessage()
	{
		return showMessage;
	}
}
