package com.rssl.phizic.operations.dictionaries.regions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.regions.replication.RegionSynchCSADestinitions;
import com.rssl.phizic.business.dictionaries.regions.replication.RegionsComparator;
import com.rssl.phizic.business.dictionaries.regions.replication.RegionsReplicaDestinitions;
import com.rssl.phizic.gate.dictionaries.OneWayReplicator;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.OperationBase;


/**
 * @author komarov
 * @ created 28.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * �������� ������������� ����������� �������� � �� ���
 */
public class SynchronizeRegionOperation extends OperationBase
{
	/**
	 * �������������� ���������� ��������
	 */
	public void synchronize() throws BusinessException, BusinessLogicException
	{
		RegionSynchCSADestinitions replicaDestination = new RegionSynchCSADestinitions();
		RegionsReplicaDestinitions replicaSource = new RegionsReplicaDestinitions();
		OneWayReplicator replicator = new OneWayReplicator(replicaSource, replicaDestination, new RegionsComparator());
		try
		{
			replicator.replicate();
		}
		catch (GateLogicException gle)
		{
			throw new BusinessLogicException("�� ������� ���������������� ���������� ��������", gle);
		}
		catch (GateException ge)
		{
			throw new BusinessException("�� ������� ���������������� ���������� ��������", ge);
		}

	}
}
