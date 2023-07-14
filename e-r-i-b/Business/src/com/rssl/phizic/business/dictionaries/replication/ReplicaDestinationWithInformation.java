package com.rssl.phizic.business.dictionaries.replication;

import com.rssl.phizic.gate.dictionaries.ReplicaDestination;

/**
 * @author Krenev
 * @ created 07.10.2009
 * @ $Author$
 * @ $Revision$
 */
public interface ReplicaDestinationWithInformation extends ReplicaDestination
{
	/**
	 * @return Количесво добавленных элементов
	 */
	public long getAddedCount();

	/**
	 * @return Количесво обновлекнных элементов
	 */
	public long getUpdatedCount();

	/**
	 * @return Количесво удавленных элементов
	 */
	public long getDeletedCount();
}
