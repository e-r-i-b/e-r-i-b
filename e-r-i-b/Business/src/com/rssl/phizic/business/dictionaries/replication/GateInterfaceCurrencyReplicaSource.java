package com.rssl.phizic.business.dictionaries.replication;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.CurrenciesGateService;
import com.rssl.phizic.gate.dictionaries.ReplicaSource;
import com.rssl.phizic.gate.exceptions.GateException;

import java.util.Iterator;

/**
 * @author Krenev
 * @ created 27.05.2009
 * @ $Author$
 * @ $Revision$
 */

public class GateInterfaceCurrencyReplicaSource implements ReplicaSource
{
	private CurrenciesGateService service;

	public void initialize(GateFactory factory)
	{
		service = factory.service(CurrenciesGateService.class);
	}

	public Iterator iterator() throws GateException
	{
		return new ReplicationServiceIterator(service);
	}

	public void close() {}
}
