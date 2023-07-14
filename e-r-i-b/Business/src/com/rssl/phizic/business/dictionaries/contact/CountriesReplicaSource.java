package com.rssl.phizic.business.dictionaries.contact;

import com.linuxense.javadbf.DBFException;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.SynchKeyComparator;
import com.rssl.phizic.gate.dictionaries.CountriesGateService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.GateFactory;

import java.util.Map;

/**
 * @author Kosyakova
 * @ created 09.11.2006
 * @ $Author$
 * @ $Revision$
 */

public class CountriesReplicaSource extends ContactFileReplicaSource
{
	public CountriesReplicaSource()
	{
		super("country.dbf", new SynchKeyComparator());
	}

	protected void validate(DictionaryRecord dr) throws GateException
	{
	}

	protected DictionaryRecord map(Map row) throws DBFException, GateException
	{
     	ContactCountry contactCountry = new ContactCountry();
		contactCountry.setId(convertDoubleToLongValue(row.get("ID")));
		contactCountry.setName(((String) row.get("NAME")).trim());
		contactCountry.setNameLat(((String) row.get("NAME_LAT")).trim());
		contactCountry.setCode(((String) row.get("CODE")).trim());
		contactCountry.setRUR(convertDoubleToLongValue(row.get("IS_RUR")) == 1);
		contactCountry.setUSD(convertDoubleToLongValue(row.get("IS_USD")) == 1);
		contactCountry.setEUR(convertDoubleToLongValue(row.get("IS_EUR")) == 1);

		return contactCountry;
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}
}
