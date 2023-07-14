package com.rssl.phizic.business.login.exceptions;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;

/**
 *  Исключение бросаемое при остановке синхронизации клиента
 * (в переменной login хранится логин клиента, над которым совершаются операции синхронизации)
 *
 * @author khudyakov
 * @ created 24.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class StopClientSynchronizationException extends BusinessException
{
	private Login login;                                //логин клиента
	private boolean showMessage;                        //показывать ли сообщение об ошибке клиенту

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
	 * @return логин клиента
	 */
	public Login getLogin()
	{
		return login;
	}

	/**
	 * @return показываем ли сообщение об ошибке клиенту
	 */
	public boolean isShowMessage()
	{
		return showMessage;
	}
}
