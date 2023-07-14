package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.gate.dictionaries.KBKRecord;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Kosyakova
 * @ created 12.02.2007
 * @ $Author$
 * @ $Revision$
 */
public class KBKReplicaDestination extends QueryReplicaDestinationBase<KBKRecord>
{
    public KBKReplicaDestination()
    {
        super("com.rssl.phizic.gate.dictionaries.KBKRecord.getAllBySynchKey");
    }

	public void initialize(GateFactory factory) throws GateException
	{
	}
}