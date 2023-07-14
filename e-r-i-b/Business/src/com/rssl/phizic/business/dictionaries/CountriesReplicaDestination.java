package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.gate.dictionaries.Country;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.ReplicaDestination;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Kosyakova
 * @ created 07.12.2006
 * @ $Author$
 * @ $Revision$
 */
public class CountriesReplicaDestination extends QueryReplicaDestinationBase  implements ReplicaDestination
{
    public CountriesReplicaDestination()
    {
        super("com.rssl.phizic.gate.dictionaries.Country.getAllBySynchKey");
    }

	public void initialize(GateFactory factory) throws GateException
	{
	}

	public void add(DictionaryRecord newValue) throws GateException
	{
		Country country = new Country();
		country.updateFrom(newValue);
		if (!country.getCode().equals("") && !country.getIntCode().equals(""))
		{
			super.add(country);
		}
		else
		{
			addError("не удалось добавить страну с кодом "+country.getIntCode());
		}
	}

	public void update(DictionaryRecord oldValue, DictionaryRecord newValue) throws GateException
	{
		Country country = new Country();
		country.updateFrom(newValue);
		if (!country.getCode().equals("") && !country.getIntCode().equals(""))
		{
			super.update(oldValue, country);
		}
		else
		{
			super.remove(oldValue);
			addError("не удалось обновить страну с кодом "+country.getIntCode());
		}
	}
}