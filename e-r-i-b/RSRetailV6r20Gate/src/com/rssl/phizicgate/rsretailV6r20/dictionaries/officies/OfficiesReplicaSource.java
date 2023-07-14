package com.rssl.phizicgate.rsretailV6r20.dictionaries.officies;

import com.rssl.phizicgate.rsretailV6r20.dictionaries.NamedQueryReplicaSource;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Omeliyanchuk
 * @ created 12.04.2007
 * @ $Author$
 * @ $Revision$
 */

public class OfficiesReplicaSource extends NamedQueryReplicaSource
{
	public OfficiesReplicaSource()
	{
		super("com.rssl.phizic.gate.impl.dictionaries.officies.ExtendedOfficeGateImpl.rsRetailV6r20.GetOfficies");
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}
}
