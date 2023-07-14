package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.SynchKeyComparator;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import org.hibernate.Query;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Kosyakova
 * @ created 10.11.2006
 * @ $Author$
 * @ $Revision$
 */
public abstract class QueryReplicaDestinationBase<DR extends DictionaryRecord> extends ReplicaDestinationBase<DR>
{
	private String queryName;

	protected QueryReplicaDestinationBase(String hqlQuery)
	{
		this(hqlQuery, false, false);
	}

	protected QueryReplicaDestinationBase(String hqlQuery, boolean ignoreDublicate)
	{
		this(hqlQuery, ignoreDublicate, false);
	}

	protected QueryReplicaDestinationBase(String hqlQuery, boolean ignoreDublicate, boolean needSort)
	{
		this.queryName = hqlQuery;
		this.ignoreDublicate = ignoreDublicate;
		this.needSort = needSort;
	}

	public Iterator<DR> iterator() throws GateException, GateLogicException
	{
		initialize();

		Query query = getQuery();
		//noinspection unchecked
		List<DR> list = query.list();

		if(needSort)
			Collections.sort(list, new SynchKeyComparator());
	    return list.iterator();
	}

	/**
	 * @return - получаем Query
	 */
	protected Query getQuery()
	{
		return getSession().getNamedQuery(queryName);
	}

	protected String getQueryName()
	{
		return queryName;
	}
}
