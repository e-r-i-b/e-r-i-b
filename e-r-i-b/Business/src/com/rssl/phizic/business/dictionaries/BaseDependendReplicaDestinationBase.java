package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.business.hibernate.DataBaseTypeQueryHelper;
import org.hibernate.MappingException;
import org.hibernate.Query;

/**
 * @author Omeliyanchuk
 * @ created 04.10.2007
 * @ $Author$
 * @ $Revision$
 */

public class BaseDependendReplicaDestinationBase<DR extends DictionaryRecord> extends QueryReplicaDestinationBase<DR>
{
	public BaseDependendReplicaDestinationBase(String queryName)
	{
		super(queryName);
	}

	public BaseDependendReplicaDestinationBase(String hqlQuery, boolean ignoreDublicate)
	{
		super(hqlQuery, ignoreDublicate);
	}

	public BaseDependendReplicaDestinationBase(String hqlQuery, boolean ignoreDublicate, boolean needSort)
	{
		super(hqlQuery, ignoreDublicate, needSort);
	}

	protected Query getQuery()
	{
		try
		{
			return getSession().getNamedQuery(getQueryName() + '.' + DataBaseTypeQueryHelper.getDBType());
		}
		catch (MappingException ex)
		{
			return super.getQuery();
		}
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}
}
