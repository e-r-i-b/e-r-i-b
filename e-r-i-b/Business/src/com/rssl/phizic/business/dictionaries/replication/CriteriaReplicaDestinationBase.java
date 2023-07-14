package com.rssl.phizic.business.dictionaries.replication;

import com.rssl.phizic.business.dictionaries.ReplicaDestinationBase;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.SynchKeyComparator;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import org.hibernate.criterion.DetachedCriteria;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author khudyakov
 * @ created 11.08.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class CriteriaReplicaDestinationBase <DR extends DictionaryRecord> extends ReplicaDestinationBase
{
	protected CriteriaReplicaDestinationBase(boolean ignoreDublicate, boolean needSort)
	{
		this.ignoreDublicate = ignoreDublicate;
		this.needSort = needSort;
	}

	public Iterator iterator() throws GateException, GateLogicException
	{
		initialize();

		//noinspection unchecked
		List<DR> list = getCriteria().getExecutableCriteria(getSession()).list();

		if(needSort)
			Collections.sort(list, new SynchKeyComparator());

	    return list.iterator();
	}

	/**
	 * @return - получить DetachedCriteria
	 */
	protected abstract DetachedCriteria getCriteria();
}
