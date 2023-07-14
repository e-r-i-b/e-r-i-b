package com.rssl.phizicgate.rsbankV50.dictionaries;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Kosyakova
 * @ created 07.12.2006
 * @ $Author$
 * @ $Revision$
 */
public class CountriesReplicaSource extends NamedQueryReplicaSource
{
    public CountriesReplicaSource()
    {
        super("GetCountries", false);
    }

	public void initialize(GateFactory factory) throws GateException
	{
	}
}
