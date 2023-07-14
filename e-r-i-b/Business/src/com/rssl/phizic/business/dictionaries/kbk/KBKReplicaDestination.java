package com.rssl.phizic.business.dictionaries.kbk;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.business.dictionaries.QueryReplicaDestinationBase;

/**
 * @author Kosyakova
 * @ created 12.02.2007
 * @ $Author$
 * @ $Revision$
 */
public class KBKReplicaDestination extends QueryReplicaDestinationBase<KBK>
{
    public KBKReplicaDestination()
    {
        super("com.rssl.phizic.business.dictionaries.kbk.KBK.getAllOrderedByCode");
    }

	public void initialize(GateFactory factory) throws GateException
	{
	}
}