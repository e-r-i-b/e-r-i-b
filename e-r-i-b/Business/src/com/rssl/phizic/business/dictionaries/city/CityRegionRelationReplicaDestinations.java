package com.rssl.phizic.business.dictionaries.city;

import com.rssl.phizic.business.dictionaries.QueryReplicaDestinationBase;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.exceptions.GateException;
import org.hibernate.HibernateException;

/**
 * @author lepihina
 * @ created 28.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class CityRegionRelationReplicaDestinations extends QueryReplicaDestinationBase
{
	public CityRegionRelationReplicaDestinations()
	{
		super("com.rssl.phizic.business.dictionaries.city.City.getAll");
	}

	public void initialize(GateFactory factory) throws GateException {}

	public void add(DictionaryRecord newValue) throws GateException
	{
		CityRegionRelation relation = (CityRegionRelation) newValue;
		addError("В БД нет записи с кодом " + relation.getCity());
	}

	public void remove(DictionaryRecord oldValue) throws GateException
	{
		// ничего не удаляем
	}

	public void update(DictionaryRecord oldValue, DictionaryRecord newValue) throws GateException
	{
		City city = (City) oldValue;
		CityRegionRelation relation = (CityRegionRelation) newValue;
		city.setRegionCode(relation.getRegion());

		try
		{
			getSession().update(city);
		}
		catch (HibernateException e)
		{
			throw new GateException(e);
		}
	}
}
