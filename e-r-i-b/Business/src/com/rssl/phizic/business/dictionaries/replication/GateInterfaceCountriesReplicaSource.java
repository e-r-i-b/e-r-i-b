package com.rssl.phizic.business.dictionaries.replication;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.CountriesGateService;
import com.rssl.phizic.gate.dictionaries.ReplicaSource;
import com.rssl.phizic.gate.dictionaries.Country;
import com.rssl.phizic.gate.exceptions.GateException;

import java.util.Iterator;

/**
 * @author Kosyakova
 * @ created 07.12.2006
 * @ $Author$
 * @ $Revision$
 */
public class GateInterfaceCountriesReplicaSource implements ReplicaSource<Country>
{
	private CountriesGateService service;

	public void initialize(GateFactory factory) throws GateException
	{
		service = factory.service(CountriesGateService.class);
	}

	public Iterator<Country> iterator() throws GateException
	{
		return new ReplicationServiceIterator<Country>(service);
	}

	public void close() {}
}