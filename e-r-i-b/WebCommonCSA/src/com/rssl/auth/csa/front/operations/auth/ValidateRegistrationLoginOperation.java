package com.rssl.auth.csa.front.operations.auth;

import com.rssl.auth.csa.front.security.NewRegistrationSecurityManager;
import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.LoginAlreadyRegisteredException;
import org.w3c.dom.Document;
import com.rssl.auth.security.SecurityManager;

/**
 * Операция валидации логина
 * @author niculichev
 * @ created 11.02.14
 * @ $Author$
 * @ $Revision$
 */
public class ValidateRegistrationLoginOperation extends InterchangeCSABackOperationBase
{
	private RegistrationOperationInfo info;
	private String login;

	public void initialize(RegistrationOperationInfo operationInfo, String login)
	{
		this.info = operationInfo;
		this.login = login;
	}

	@Override
	protected Document doRequest() throws BackLogicException, BackException
	{
		SecurityManager manager = NewRegistrationSecurityManager.getIt();

		try
		{
			CSABackRequestHelper.sendPrepareCheckLoginByOperationRq(info.getOUID(), login);
			// ввели всё успешно, все сбрасываем
			manager.reset(info.getKeyByUserInfo());
			return null;
		}
		catch (LoginAlreadyRegisteredException e)
		{
			// дополнительная обработка ошибки
			manager.processUserAction(info.getKeyByUserInfo());
			throw e;
		}
	}
}
