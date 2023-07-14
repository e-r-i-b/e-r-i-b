package com.rssl.phizicgate.rsretailV6r20.dictionaries;

import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.CountriesGateService;
import com.rssl.phizic.gate.dictionaries.Country;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizicgate.rsretailV6r20.data.RSRetailV6r20Executor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Pakhomova
 * @created: 07.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class CountriesGateServiceImpl extends AbstractService implements CountriesGateService
{
	public CountriesGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public List<Country> getAll(int firstResult, int maxResults) throws GateException, GateLogicException
	{

		Query query = RSRetailV6r20Executor.getInstance().getNamedQuery("GetCountries");
		//noinspection unchecked
		List<Country> countries = null;
		try
		{
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResults);
			countries = query.executeList();
		}
		catch (DataAccessException e)
		{
			throw new GateException(e);
		}
		return (countries != null ? countries : new ArrayList<Country>());
	}

	public List<Country> getAll(Country template, int firstResult, int listLimit) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}
}
