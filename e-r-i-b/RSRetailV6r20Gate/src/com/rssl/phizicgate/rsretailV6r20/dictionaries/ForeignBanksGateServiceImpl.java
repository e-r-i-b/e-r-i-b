package com.rssl.phizicgate.rsretailV6r20.dictionaries;

import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.ForeignBank;
import com.rssl.phizic.gate.dictionaries.ForeignBanksGateService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizicgate.rsretailV6r20.data.RSRetailV6r20Executor;

import java.util.ArrayList;
import java.util.List;

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
		Query query = RSRetailV6r20Executor.getInstance().getNamedQuery("GetForeignBanks");
		//noinspection unchecked
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		List<ForeignBank> banks = null;
		try
		{
			banks = query.executeList();
		}
		catch (DataAccessException e)
		{
			throw new GateException(e);
		}
		return (banks != null ? banks : new ArrayList<ForeignBank>());
	}

	public List<ForeignBank> getAll(ForeignBank template, int firstResult, int listLimit) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}
}
