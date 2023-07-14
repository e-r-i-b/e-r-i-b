package com.rssl.phizicgate.rsretailV6r20.bankroll;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizicgate.rsretailV6r20.data.RSRetailV6r20Executor;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.FlushMode;

/**
 * @author Danilov
 * @ created 03.10.2007
 * @ $Author$
 * @ $Revision$
 */
public class AccountService
{
	/**
	 *
	 * @param id - идентификатор счета во внешней системе
	 * @return счет
	 * @throws GateException
	 */
	public AccountImpl getAccount(final Long id) throws GateException
	{
	    try
	    {
	        return RSRetailV6r20Executor.getInstance().execute(new HibernateAction<AccountImpl>()
	        {
	            public AccountImpl run(Session session) throws Exception
	            {
	                Query query = createReadonlyQuery(session, "GetAccount")
	                        .setParameter("accountId", id);

	                return (AccountImpl) query.uniqueResult();
	            }
	        });
	    }
	    catch (Exception e)
	    {
	       throw new GateException(e);
	    }
	}

	private Query createReadonlyQuery(Session session, String queryName)
	{
	    Query query = session.getNamedQuery(queryName);
	    query.setReadOnly(true).setFlushMode(FlushMode.NEVER);

	    return query;
	}
}
