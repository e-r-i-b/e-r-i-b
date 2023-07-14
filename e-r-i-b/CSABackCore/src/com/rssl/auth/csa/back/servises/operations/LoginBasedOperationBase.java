package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.exceptions.IMSICheckException;
import com.rssl.auth.csa.back.exceptions.RestrictionException;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.Operation;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;

/**
 * @author krenev
 * @ created 20.12.2012
 * @ $Author$
 * @ $Revision$
 * Базовая операция, работающая и инициализирующаяся в контексте логина
 */
public class LoginBasedOperationBase extends Operation
{
	private static final String GUID_PARAM = "connector";
	private static final String LOGIN_PARAM = "login";

	public LoginBasedOperationBase()
	{
	}

	public LoginBasedOperationBase(IdentificationContext identificationContext)
	{
		super(identificationContext);
	}

	protected void setConnectorUID(String guid)
	{
		addParameter(GUID_PARAM, guid);
	}

	protected String getConnectorUID()
	{
		return getParameter(GUID_PARAM);
	}

	private void setLogin(String guid)
	{
		addParameter(LOGIN_PARAM, guid);
	}

	protected String getLogin()
	{
		return getParameter(LOGIN_PARAM);
	}

	/**
	 * Провести инициализацию по логину.
	 * @param login логин
	 * @throws Exception внутренние ошибки
	 */
	public void initialize(final String login) throws Exception
	{
		final Connector connector = findAuthenticableConnecorByLogin(login);
		checkBeforeInitialize(connector);

		initialize(new HibernateAction<Void>()
		{
			public Void run(org.hibernate.Session session) throws Exception
			{
				setConnectorUID(connector.getGuid());
				setLogin(login);
				return null;
			}
		});
	}

	protected void checkBeforeInitialize(Connector connector) throws Exception
	{
		// проверка возможности инициализации операции
	}

	protected Connector getConnector() throws Exception
	{
		return findConnectorByGuid(getConnectorUID(), null);
	}
}
