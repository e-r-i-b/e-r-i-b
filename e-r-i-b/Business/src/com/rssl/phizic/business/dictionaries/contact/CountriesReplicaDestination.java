package com.rssl.phizic.business.dictionaries.contact;

import com.rssl.phizic.business.dictionaries.QueryReplicaDestinationBase;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Kosyakova
 * @ created 14.11.2006
 * @ $Author$
 * @ $Revision$
 */
public class CountriesReplicaDestination extends QueryReplicaDestinationBase<ContactCountry>
{
	public CountriesReplicaDestination()
	{
		super("com.rssl.phizic.business.dictionaries.contact.ContactCountry.getAllBySynchKey");
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}
}
