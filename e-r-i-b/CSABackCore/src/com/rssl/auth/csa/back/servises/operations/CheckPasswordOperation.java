package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;

/**
 * @author krenev
 * @ created 12.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class CheckPasswordOperation extends SessionContextOperation
{
	public CheckPasswordOperation() {}

	public CheckPasswordOperation(IdentificationContext identificationContext)
	{
		super(identificationContext);
	}

	public Connector execute(final String password) throws Exception
	{
		return execute(new HibernateAction<Connector>()
		{
			public Connector run(org.hibernate.Session session) throws Exception
			{
				Connector connector = getSessionConnector();
				connector.authenticate(password);
				return connector;
			}
		});
	}
}
