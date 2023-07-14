package com.rssl.phizicgate.rsV55.dictionaries.officies;

import com.rssl.phizicgate.rsV55.dictionaries.NamedQueryReplicaSource;
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
		super("com.rssl.phizicgate.rsV55.dictionaries.officies.GetOfficies", false);
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}
}
