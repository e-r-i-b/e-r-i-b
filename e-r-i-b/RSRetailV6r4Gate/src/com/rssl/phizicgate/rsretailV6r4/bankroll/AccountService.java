package com.rssl.phizicgate.rsretailV6r4.bankroll;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizicgate.rsretailV6r4.data.RSRetailV6r4Executor;
import com.rssl.phizicgate.rsretailV6r4.messaging.RetailXMLHelper;
import com.rssl.phizicgate.rsretailV6r4.hibernate.RSRetailCurrencyType;
import com.rssl.phizicgate.rsretailV6r4.money.CurrencyHelper;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.FlushMode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
	        return RSRetailV6r4Executor.getInstance().execute(new HibernateAction<AccountImpl>()
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
