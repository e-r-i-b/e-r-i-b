package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.connectors.PasswordBasedConnector;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;

/**
 * @author krenev
 * @ created 12.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class ChangePasswordOperation extends SessionContextOperation
{
	public ChangePasswordOperation() {}

	public ChangePasswordOperation(IdentificationContext identificationContext)
	{
		super(identificationContext);
	}

	public Connector execute(final String password) throws Exception
	{
		return execute(new HibernateAction<Connector>()
		{
			public Connector run(org.hibernate.Session hibernateSession) throws Exception
			{
				Connector connector = getSessionConnector();
				if (!(connector instanceof PasswordBasedConnector))
				{
					throw new UnsupportedOperationException("Коннектор" + connector.getGuid() + " не поддерживает установку произвольного пароля.");
				}

				PasswordBasedConnector passwordBasedConnector = (PasswordBasedConnector) connector;
				passwordBasedConnector.changePassword(password);
				return connector;
			}
		});
	}
}
