package com.rssl.phizicgate.rsV51.dictionaries;

import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.dictionaries.BanksGateService;
import com.rssl.phizic.gate.dictionaries.Bank;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizicgate.rsV51.data.GateRSV51Executor;

import java.util.List;
import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.Query;

/**
 * @author: Pakhomova
 * @created: 07.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class BanksGateServiceImpl extends AbstractService implements BanksGateService
{
	/**
	 * 
	 * @param factory  gatefactory
	 */
	public BanksGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}
	public List<Bank> getAll(final int firstResult, final int maxResults) throws GateException, GateLogicException
	{
		try
		{

			return GateRSV51Executor.getInstance().execute(new HibernateAction<List<Bank>>()
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
		}	}

	public	List<Bank> getAll(Bank template, final int firstResult, final int listLimit) throws GateException
	{
		try
		{
			final String name = template == null ? null : template.getName();
			final String bic = template == null ? null : (String)template.getSynchKey();
			final String place = template == null ? null : template.getPlace();
			final String account = template == null ? null : template.getAccount();

			return GateRSV51Executor.getInstance().execute(new HibernateAction<List<Bank>>()
			   {
					public List<Bank> run(Session session) throws Exception
					{
						 //noinspection unchecked
						 Query query = session.getNamedQuery("GetResidentBanksByTemplate");
						//noinspection unchecked
						query.setFirstResult(firstResult);
						query.setMaxResults(listLimit);
						query.setParameter("name", name);
						query.setParameter("bic", bic);
						query.setParameter("place", place);
						query.setParameter("account", account);
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

}
