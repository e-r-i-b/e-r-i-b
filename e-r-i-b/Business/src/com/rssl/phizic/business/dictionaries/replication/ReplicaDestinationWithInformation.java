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
	 * @return ��������� ����������� ���������
	 */
	public long getAddedCount();

	/**
	 * @return ��������� ������������ ���������
	 */
	public long getUpdatedCount();

	/**
	 * @return ��������� ���������� ���������
	 */
	public long getDeletedCount();
}
