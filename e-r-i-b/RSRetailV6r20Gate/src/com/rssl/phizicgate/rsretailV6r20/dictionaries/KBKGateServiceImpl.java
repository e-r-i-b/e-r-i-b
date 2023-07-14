package com.rssl.phizicgate.rsretailV6r20.dictionaries;

import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.KBKGateService;
import com.rssl.phizic.gate.dictionaries.KBKRecord;
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
public class KBKGateServiceImpl extends AbstractService implements KBKGateService
{
	public KBKGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public List<KBKRecord> getAll(final int firstResult, final int maxResults) throws GateException, GateLogicException
	{
		Query query = RSRetailV6r20Executor.getInstance().getNamedQuery("GetKBK");
		//noinspection unchecked
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		List<KBKRecord> kbks = null;
		try
		{
			kbks = query.executeList();
		}
		catch (DataAccessException e)
		{
			throw new GateException(e);
		}
		return (kbks != null ? kbks : new ArrayList<KBKRecord>());
	}

	public List<KBKRecord> getAll(KBKRecord template, int firstResult, int listLimit) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}
}
