package com.rssl.phizicgate.rsV55.dictionaries;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Kosyakova
 * @ created 12.02.2007
 * @ $Author$
 * @ $Revision$
 */
public class KBKReplicaSource extends NamedQueryReplicaSource
{
    public KBKReplicaSource()
    {
        super("GetKBK", false);
    }

	public void initialize(GateFactory factory) throws GateException
	{
	}
}
