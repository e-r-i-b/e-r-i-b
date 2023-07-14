package com.rssl.phizicgate.rsretailV6r20.dictionaries;

import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.CurrencyOpTypeGateService;
import com.rssl.phizic.gate.dictionaries.CurrencyOperationType;
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
			Query query = RSRetailV6r20Executor.getInstance().getNamedQuery("com.rssl.phizicgate.rsretailV6r20.dictionaries.GetCurrencyOperationTypes");

			//noinspection unchecked
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResults);
			List<CurrencyOperationType> types = query.executeList();
			return (types != null ? types : new ArrayList<CurrencyOperationType>());
		}
		catch (Exception ex)
		{
			throw new GateException(ex);
		}
	}

	public List<CurrencyOperationType> getAll(CurrencyOperationType template, int firstResult, int listLimit) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}
}

