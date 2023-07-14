package com.rssl.phizic.business.login.exceptions;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * Исключение при деградации клиента с "UDBO" до "CARD". Признак расторжения договора приходит из шины.
 *
 * @ author: Gololobov
 * @ created: 30.08.13
 * @ $Author$
 * @ $Revision$
 */
public class DegradationFromUDBOToCardException extends BusinessLogicException
{
	private static final String MESSAGE = "Уважаемый клиент!\n" +
										  "В связи с расторжением договора универсального банковского обслуживания от %s "+
										  "Вам доступна информация и операции только по Вашим картам. Для того, чтобы восстановить "+
										  "полнофункциональный режим необходимо заключить договор универсального банковского обслуживания.";

	private Login login;//логин клиента

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
