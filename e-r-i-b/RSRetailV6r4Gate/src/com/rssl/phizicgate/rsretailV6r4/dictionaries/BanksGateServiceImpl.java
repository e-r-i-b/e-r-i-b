package com.rssl.phizicgate.rsretailV6r4.dictionaries;

import com.rssl.phizic.gate.dictionaries.BanksGateService;
import com.rssl.phizic.gate.dictionaries.Bank;
import com.rssl.phizic.gate.dictionaries.KBKRecord;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.TransactionBase;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizicgate.rsretailV6r4.data.RSRetailV6r4Executor;

import java.util.List;
import java.util.ArrayList;
import javax.naming.NameNotFoundException;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author: Pakhomova
 * @created: 07.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class BanksGateServiceImpl extends AbstractService implements BanksGateService
{

	public BanksGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}
	public List<Bank> getAll(final int firstResult, final int maxResults) throws GateException, GateLogicException
	{
		try
		{

			return RSRetailV6r4Executor.getInstance().execute(new HibernateAction<List<Bank>>()
			   {
					public List<Bank> run(Session session) throws Exception
					{
						 //noinspection unchecked
						 Query query = session.getNamedQuery("GetResidentBanks");
						//noinspection unchecked
						query.setFirstResult(firstResult);
						query.setMaxResults(maxResults);
						 List<Bank> banks = query.list();
						return (banks != null  ?  banks  :  new ArrayList<Bank>());

					}
			});
		}
		catch (Exception ex)
		{
			throw new GateException(ex);
		}
	}

	public	List<Bank> getAll(Bank template, int firstResult, int listLimit) throws GateException
	{
	   throw new UnsupportedOperationException();
	}
}
