package com.rssl.phizic.business.dictionaries.city;

import com.rssl.phizic.business.dictionaries.QueryReplicaDestinationBase;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author lepihina
 * @ created 27.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class CityReplicaDestinations extends QueryReplicaDestinationBase<City>
{
	public CityReplicaDestinations()
	{
		super("com.rssl.phizic.business.dictionaries.city.City.getAll");
	}

	public void initialize(GateFactory factory) throws GateException {}
}
