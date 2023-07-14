package com.rssl.phizic.csaadmin.operation;

import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.phizic.csaadmin.business.authtoken.AuthenticationToken;
import com.rssl.phizic.csaadmin.business.authtoken.AuthenticationTokenService;
import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.business.login.Login;
import com.rssl.phizic.csaadmin.business.login.LoginService;
import com.rssl.phizic.csaadmin.business.session.Session;
import com.rssl.phizic.csaadmin.business.session.SessionService;

import java.util.Map;

/**
 * @author mihaylov
 * @ created 27.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Базовая операция входа в блок
 */
public abstract class AuthenticationOperationBase
{
	protected static final SessionService sessionService = new SessionService();
	protected static final LoginService loginService = new LoginService();

	private static final AuthenticationTokenService authTokenService = new AuthenticationTokenService();

	protected Session session;
	protected NodeInfo currentNode;
	protected String action;
	protected Map<String,String> parameters;
	protected Login login;

	/**
	 * @return возвращает токен аутентификации
	 */
	public AuthenticationToken createToken() throws AdminException
	{
		AuthenticationToken authToken = new AuthenticationToken(session,action,parameters);
		authTokenService.save(authToken);
		return authToken;
	}

	/**
	 * @return текущий блок сотрудника
	 */
	public NodeInfo getCurrentNode()
	{
		return currentNode;
	}
}
