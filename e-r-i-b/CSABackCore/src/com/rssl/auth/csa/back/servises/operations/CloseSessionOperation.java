package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.servises.Session;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;

/**
 * @author krenev
 * @ created 28.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class CloseSessionOperation extends SessionContextOperation
{
	public CloseSessionOperation() {}

	public CloseSessionOperation(IdentificationContext identificationContext)
	{
		super(identificationContext);
	}

	/**
	 * Закрыть сесиию
	 * @return закрытая сессия
	 */
	public Session execute() throws Exception
	{
		return execute(new HibernateAction<Session>()
		{
			public Session run(org.hibernate.Session hibernateSession) throws Exception
			{
				Session session = getSession();
				session.close();
				return session;
			}
		});
	}
}
