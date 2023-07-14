package com.rssl.phizicgate.rsretailV6r4.dictionaries.officies;

import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.dictionaries.officies.OfficeGateService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizicgate.rsretailV6r4.data.RSRetailV6r4Executor;

import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 04.04.2008
 * @ $Author$
 * @ $Revision$
 */

public class OfficeGateServiceImpl extends AbstractService implements OfficeGateService
{
	public OfficeGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public Office getOfficeById(final String id) throws GateException
	{
		Query query = RSRetailV6r4Executor.getInstance().getNamedQuery("com.rssl.phizicgate.rsretailV6r4.dictionaries.officies.OfficeImpl.findById");
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

	public List<Office> getAll(final Office template, int firstResult, final int listLimit) throws GateException
	{
		Query query = RSRetailV6r4Executor.getInstance().getNamedQuery("com.rssl.phizicgate.rsretailV6r4.dictionaries.officies.OfficeImpl.list");
		query.setMaxResults(listLimit).
				setFirstResult(firstResult).
				setParameter("name", template.getName());
		try
		{
			return query.executeList();
		}
		catch (DataAccessException e)
		{
			throw new GateException(e);
		}
	}

	public List<Office> getAll(final int firstResult, final int maxResults) throws GateException
	{
		Query query = RSRetailV6r4Executor.getInstance().getNamedQuery("com.rssl.phizicgate.rsretailV6r4.dictionaries.officies.OfficeImpl.list");
		query.setMaxResults(maxResults).
				setFirstResult(firstResult).
				setParameter("name", null);
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
