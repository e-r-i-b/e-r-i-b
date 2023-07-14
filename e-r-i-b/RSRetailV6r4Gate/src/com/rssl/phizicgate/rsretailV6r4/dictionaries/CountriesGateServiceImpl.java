package com.rssl.phizicgate.rsretailV6r4.dictionaries;

import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.dictionaries.CountriesGateService;
import com.rssl.phizic.gate.dictionaries.Country;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizicgate.rsretailV6r4.data.RSRetailV6r4Executor;
import org.hibernate.Session;
import org.hibernate.Query;

import java.util.List;
import java.util.ArrayList;
import javax.naming.NameNotFoundException;

/**
 * @author: Pakhomova
 * @created: 07.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class CountriesGateServiceImpl extends AbstractService implements CountriesGateService
{
	protected Session session;

	public CountriesGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public List<Country> getAll(final int firstResult, final int maxResults) throws GateException, GateLogicException
	{

		try
		{

			return RSRetailV6r4Executor.getInstance().execute(new HibernateAction<List<Country>>()
			   {
					public List<Country> run(Session session) throws Exception
					{
						 //noinspection unchecked
						 Query query = session
								   .getNamedQuery("GetCountries");
						//noinspection unchecked
						query.setFirstResult(firstResult);
						query.setMaxResults(maxResults);
						 List<Country> countries = query.list();
						return (countries != null  ?  countries  :  new ArrayList<Country>());

					}
			});
		}
		catch (Exception ex)
		{
			throw new GateException(ex);
		}
	}

	public	List<Country> getAll(Country template, int firstResult, int listLimit) throws GateException
	{
	   throw new UnsupportedOperationException();
	}
       
}
