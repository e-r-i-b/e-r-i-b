package com.rssl.phizic.business.dictionaries.replication;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.CurrencyOpTypeGateService;
import com.rssl.phizic.gate.dictionaries.ReplicaSource;
import com.rssl.phizic.gate.dictionaries.CurrencyOperationType;
import com.rssl.phizic.gate.exceptions.GateException;

import java.util.Iterator;

/**
 * @author Krenev
 * @ created 27.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class GateInterfaceCurrencyOperationTypesReplicaSource implements ReplicaSource<CurrencyOperationType>
{
	private static CurrencyOpTypeGateService service;

	public void initialize(GateFactory factory) throws GateException
	{
		service = factory.service(CurrencyOpTypeGateService.class);
	}

	public Iterator<CurrencyOperationType> iterator() throws GateException
	{
		return new ReplicationServiceIterator<CurrencyOperationType>(service);
	}

	public void close() {}
}
