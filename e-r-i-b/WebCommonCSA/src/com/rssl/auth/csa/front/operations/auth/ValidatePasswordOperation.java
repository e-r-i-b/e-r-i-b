package com.rssl.auth.csa.front.operations.auth;

import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.auth.csa.front.security.NewRegistrationSecurityManager;
import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.LoginAlreadyRegisteredException;
import org.w3c.dom.Document;

/**
 * Операция валидации логина
 * @author niculichev
 * @ created 12.02.14
 * @ $Author$
 * @ $Revision$
 */
public class ValidatePasswordOperation extends InterchangeCSABackOperationBase
{
	private OperationInfo info;
	private String login;

	public void initialize(OperationInfo operationInfo, String login)
	{
		this.info = operationInfo;
		this.login = login;
	}

	@Override
	protected Document doRequest() throws BackLogicException, BackException
	{
		CSABackRequestHelper.sendPrepareCheckPasswordByOperationRq(info.getOUID(), login);
		return null;
	}
}
