package com.rssl.phizic.locale.replicator;

import java.util.Set;

/**
 * @author komarov
 * @ created 15.09.2014
 * @ $Author$
 * @ $Revision$
 */
public interface ReplicaSource<T>
{
	public Set<T> iterator();
}
