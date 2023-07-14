package com.rssl.phizicgate.rsV51.bankroll;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.dictionaries.officies.OfficeGateService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizicgate.rsV51.data.GateRSV51Executor;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Krenev
 * @ created 06.08.2007
 * @ $Author$
 * @ $Revision$
 */
//TODO Перенести всу "низкоуовневую" работу со счетами из BankrollServiceImpl 
public class AccountService
{
	/**
	 * @param id - идентификатор счета во внешней системе
	 * @return счет
	 * @throws GateException
	 */
	public AccountImpl getAccount(final Long id) throws GateException
	{
	    try
	    {
	        AccountImpl account = GateRSV51Executor.getInstance().execute(new HibernateAction<AccountImpl>()
	        {
	            public AccountImpl run(Session session) throws Exception
	            {
	                Query query = createReadonlyQuery(session, "GetAccount")
	                        .setParameter("accountId", id);

	                return (AccountImpl) query.uniqueResult();
	            }
	        });
		    //Перенесено из BankrollServiceImpl
		    OfficeGateService officeGateService = GateSingleton.getFactory().service(OfficeGateService.class);
			account.setOffice(officeGateService.getOfficeById(String.valueOf(account.getBranch())));
		    return account;
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

	public AccountImpl getAccountByNumber(final String accountNumber) throws GateException
	{
		try
		{
			return GateRSV51Executor.getInstance().execute(new HibernateAction<AccountImpl>()
			{
				public AccountImpl run(Session session) throws Exception
	            {
					Query query = createReadonlyQuery(session, "GetAccountByNumber")
							.setParameter("accountNumber", accountNumber);

					return (AccountImpl) query.uniqueResult();
	            }
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
