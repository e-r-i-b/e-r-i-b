package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.exceptions.OperationNotFoundException;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.Operation;
import com.rssl.auth.csa.back.servises.OperationState;
import com.rssl.auth.csa.back.servises.SyncUtil;
import com.rssl.auth.csa.back.servises.connectors.TerminalConnector;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.config.ConfigFactory;

/**
 * @author krenev
 * @ created 29.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class AuthenticationOperation extends LoginBasedOperationBase
{

	public AuthenticationOperation()
	{
	}

	public AuthenticationOperation(IdentificationContext identificationContext) throws Exception
	{
		super(identificationContext);
	}

	/**
	 * Исполнить операцию аутентиякации
	 * @param password пароль
	 * @return коннектор в случае успшной аутентификации
	 * @throws Exception
	 */
	public Connector execute(final String password) throws Exception
	{
		return execute(new HibernateAction<Connector>()
		{
			public Connector run(org.hibernate.Session session) throws Exception
			{
				Connector connector = getConnector();
				CSAUserInfo userInfo = connector.authenticate(password);
				Config config = ConfigFactory.getConfig(Config.class);
				if (connector instanceof TerminalConnector && config.isPostAuthenticationSyncAllowed())
				{
					//вставляем костилик по поздней синхронизации для iPas-коннекторов
					SyncUtil.synchronize(userInfo);
					session.refresh(AuthenticationOperation.this);
					connector = findAuthenticableConnecorByLogin(getLogin());
					setConnectorUID(connector.getGuid());
				}
				return connector;
			}
		});
	}

	/**
	 * Найти операцию по токену аутентификации.
	 * @param authToken токен аутенитфикации
	 * @return операция или null если не найдена.
	 */
	public static AuthenticationOperation findByAuthToken(String authToken) throws Exception
	{
		return (AuthenticationOperation) findByOUID(authToken);
	}

	/**
	 * Найти исполненную (EXECUTED) операцию по токену аутентификации с учетом времени жизни.
	 * @param authToken токен аутенитфикации
	 * @return операция или null если не найдена.
	 */
	public static AuthenticationOperation findLifeByAuthToken(String authToken) throws Exception
	{
		AuthenticationOperation operation = findLifeByOUID(AuthenticationOperation.class, authToken, getLifeTime());
		if (OperationState.EXECUTED == operation.getState())
			return operation;

		throw new OperationNotFoundException(authToken, AuthenticationOperation.class);
	}

	private static int getLifeTime()
	{
		return ConfigFactory.getConfig(Config.class).getAuthenticationTimeout();
	}

	/**
	 * @return токен аутентификации
	 */
	public String getAuthToken()
	{
		return getOuid();
	}
}
