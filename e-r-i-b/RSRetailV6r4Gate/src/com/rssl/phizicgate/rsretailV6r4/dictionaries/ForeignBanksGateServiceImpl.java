package com.rssl.phizicgate.rsretailV6r4.dictionaries;

import com.rssl.phizic.gate.dictionaries.ForeignBanksGateService;
import com.rssl.phizic.gate.dictionaries.ForeignBank;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizicgate.rsretailV6r4.data.RSRetailV6r4Executor;

import java.util.List;
import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author: Pakhomova
 * @created: 11.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class ForeignBanksGateServiceImpl extends AbstractService implements ForeignBanksGateService
{

	public ForeignBanksGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}
	public List<ForeignBank> getAll(final int firstResult, final int maxResults) throws GateException, GateLogicException
	{
		try
		{

			return RSRetailV6r4Executor.getInstance().execute(new HibernateAction<List<ForeignBank>>()
			   {
					public List<ForeignBank> run(Session session) throws Exception
					{
						 //noinspection unchecked
						 Query query = session.getNamedQuery("GetForeignBanks");
						//noinspection unchecked
						query.setFirstResult(firstResult);
						query.setMaxResults(maxResults);
						 List<ForeignBank> banks = query.list();
						return (banks != null  ?  banks  :  new ArrayList<ForeignBank>());

					}
			});
		}
		catch (Exception ex)
		{
			throw new GateException(ex);
		}
	}

	public	List<ForeignBank> getAll(ForeignBank template, int firstResult, int listLimit) throws GateException
	{
	   throw new UnsupportedOperationException();
	}

}
