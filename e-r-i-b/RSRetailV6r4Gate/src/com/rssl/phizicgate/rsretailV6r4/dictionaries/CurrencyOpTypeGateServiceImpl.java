package com.rssl.phizicgate.rsretailV6r4.dictionaries;

import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.dictionaries.CurrencyOpTypeGateService;
import com.rssl.phizic.gate.dictionaries.CurrencyOperationType;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;

import com.rssl.phizicgate.rsretailV6r4.data.RSRetailV6r4Executor;
import org.hibernate.Session;
import org.hibernate.Query;

import java.util.List;
import java.util.ArrayList;

/**
 * @author: Pakhomova
 * @created: 07.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyOpTypeGateServiceImpl extends AbstractService implements CurrencyOpTypeGateService
{
	public CurrencyOpTypeGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public List<CurrencyOperationType> getAll(final int firstResult, final int maxResults) throws GateException, GateLogicException
	{

		try
		{

			return RSRetailV6r4Executor.getInstance().execute(new HibernateAction<List<CurrencyOperationType>>()
			   {
					public List<CurrencyOperationType> run(Session session) throws Exception
					{
						 //noinspection unchecked
						 Query query = session
								   .getNamedQuery("com.rssl.phizicgate.rsretailV6r4.dictionaries.GetCurrencyOperationTypes");
						//noinspection unchecked
						query.setFirstResult(firstResult);
						query.setMaxResults(maxResults);
						 List<CurrencyOperationType> types = query.list();
						return (types != null  ?  types  :  new ArrayList<CurrencyOperationType>());

					}
			});
		}
		catch (Exception ex)
		{
			throw new GateException(ex);
		}
	}

	public	List<CurrencyOperationType> getAll(CurrencyOperationType template, int firstResult, int listLimit) throws GateException
	{
	   throw new UnsupportedOperationException();
	}

}

