package com.rssl.phizic.business.dictionaries.replication;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.KBKGateService;
import com.rssl.phizic.gate.dictionaries.KBKRecord;
import com.rssl.phizic.gate.dictionaries.ReplicaSource;
import com.rssl.phizic.gate.exceptions.GateException;

import java.util.Iterator;

/**
 * @author Krenev
 * @ created 27.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class GateInterfaceKBKReplicaSource implements ReplicaSource<KBKRecord>
{
	private KBKGateService service;

	public void initialize(GateFactory factory)
	{
		service = factory.service(KBKGateService.class);
	}

	public Iterator<KBKRecord> iterator() throws GateException
	{
		return new ReplicationServiceIterator<KBKRecord>(service);
	}

	public void close() {}
}
