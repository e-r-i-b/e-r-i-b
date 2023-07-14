package com.rssl.ikfl.crediting;

import com.rssl.phizic.auth.modes.AthenticationCompleteAction;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.security.SecurityLogicException;

/**
 * @author Erkin
 * @ created 26.12.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Экшен входа по задаче "Кредитование УКО"
 * Задачи:
 * + Отправка запроса за предодобренными предложениями по кредитам / кредитным картам [в CRM].
 * Важно! Экшен использует PersonContext, поэтому он должен быть вызван позднее SetupPersonContextFieldsAction
 */
public class ClientAthenticationCompleteAction implements AthenticationCompleteAction
{

	public void execute(AuthenticationContext context) throws SecurityException, SecurityLogicException
	{
		GetPersonOffers getPersonOffers = new GetPersonOffers();
		getPersonOffers.execute();
	}
}
