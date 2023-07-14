package com.rssl.phizic.business.dictionaries.replication;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.Bank;
import com.rssl.phizic.gate.dictionaries.BanksGateService;
import com.rssl.phizic.gate.dictionaries.ReplicaSource;
import com.rssl.phizic.gate.exceptions.GateException;

import java.util.Iterator;

/**
 * @author Krenev
 * @ created 27.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class GateInterfaceResidentBanksReplicaSource implements ReplicaSource<Bank>
{
	private BanksGateService service;

	public void initialize(GateFactory factory) throws GateException
	{
		service = factory.service(BanksGateService.class);
	}

	public Iterator<Bank> iterator() throws GateException
	{
		return new ReplicationServiceIterator<Bank>(service);
	}

	public void close() {}
}
