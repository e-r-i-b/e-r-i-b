package com.rssl.phizicgate.rsretailV6r20.dictionaries;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Kosyakova
 * @ created 07.12.2006
 * @ $Author$
 * @ $Revision$
 */
public class CountriesReplicaSource extends com.rssl.phizicgate.rsretailV6r20.dictionaries.NamedQueryReplicaSource
{
	public CountriesReplicaSource()
	{
		super("GetCountries");
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}
}
