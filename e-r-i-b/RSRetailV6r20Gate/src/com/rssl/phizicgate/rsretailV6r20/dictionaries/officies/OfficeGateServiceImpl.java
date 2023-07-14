package com.rssl.phizicgate.rsretailV6r20.dictionaries.officies;

import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.dictionaries.officies.OfficeGateService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizicgate.rsretailV6r20.data.RSRetailV6r20Executor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 04.04.2008
 * @ $Author$
 * @ $Revision$
 */

//todo отладить
public class OfficeGateServiceImpl extends AbstractService implements OfficeGateService
{
	public OfficeGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public Office getOfficeById(final String id) throws GateException
	{
		Query query = RSRetailV6r20Executor.getInstance().getNamedQuery("com.rssl.phizicgate.rsretailV6r20.dictionaries.offices.OfficeImpl.findById");
		query.setParameter("fnCash", id);
		try
		{
			return (Office) query.executeUnique();
		}
		catch (DataAccessException e)
		{
			throw new GateException(e);
		}
	}

	public List<Office> getAll(int firstResult, int maxResults) throws GateException
	{
		Query query = RSRetailV6r20Executor.getInstance().getNamedQuery("com.rssl.phizicgate.rsretailV6r20.dictionaries.offices.OfficeImpl.list");

		query.setParameter("name", null);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		try
		{
			return query.executeList();
		}
		catch (DataAccessException e)
		{
			throw new GateException(e);
		}
	}

	public List<Office> getAll(Office template, int firstResult, int listLimit) throws GateException, GateLogicException
	{
		Query query = RSRetailV6r20Executor.getInstance().getNamedQuery("com.rssl.phizicgate.rsretailV6r20.dictionaries.offices.OfficeImpl.list");

		query.setParameter("name", template.getName());
		query.setFirstResult(firstResult);
		query.setMaxResults(listLimit);
		try
		{
			return query.executeList();
		}
		catch (DataAccessException e)
		{
			throw new GateException(e);
		}
	}
}
