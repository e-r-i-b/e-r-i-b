package com.rssl.phizic.business.ant.configs.gate.monitoring;

import com.rssl.phizic.gate.dictionaries.OneWayReplicator;
import com.rssl.phizic.gate.monitoring.MonitoringServiceGateConfig;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.test.SafeTaskBase;

import java.io.File;
import java.util.Comparator;

/**
 * @author akrenev
 * @ created 19.02.2013
 * @ $Author$
 * @ $Revision$
 *
 *  ���� ���������� �������� �����������
 */

public class UpdateMonitoringGateConfigTask extends SafeTaskBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private String file;

	/**
	 * @return ��� �����
	 */
	public String getFile()
	{
		return file;
	}

	/**
	 * ������ ��� �����
	 * @param file ��� �����
	 */
	public void setFile(String file)
	{
		this.file = file;
	}

	public void safeExecute() throws Exception
	{
		log.info("������ ���������� �������� ����������� �����.");
		MonitoringGateConfigReplicaSource replicaSource = new MonitoringGateConfigReplicaSource();
		replicaSource.initialize(new File(getFile()));
		MonitoringGateConfigReplicaDestination replicaDestination = new MonitoringGateConfigReplicaDestination();
		Comparator comparator = new MonitoringGateConfigComparator();
		OneWayReplicator replicator = new OneWayReplicator(replicaSource, replicaDestination, comparator);
		replicator.replicate();
		log.info("����� ���������� �������� ����������� �����.");
	}

	/**
	 * ���������� �������� ����������� (�������� ������ ��� ����������)
	 * 1. ���������� �� ����� �������
	 * 2. ���������� �� ����������� �������� �������
	 */
	private class MonitoringGateConfigComparator implements Comparator<MonitoringServiceGateConfig>
	{
		private int compareBoolean(boolean first, boolean second)
		{
			if (first == second)
				return 0;

			if (first)
				return 1;

			return -1;
		}

		public int compare(MonitoringServiceGateConfig firstConfig, MonitoringServiceGateConfig secondConfig)
		{
			int compareResult = firstConfig.getServiceName().compareTo(secondConfig.getServiceName());
			if (compareResult != 0)
				return compareResult;

			//noinspection ReuseOfLocalVariable
			compareResult = compareBoolean(firstConfig.getDegradationConfig().isAvailable(), secondConfig.getDegradationConfig().isAvailable());
			if (compareResult != 0)
				return compareResult;

			return compareBoolean(firstConfig.getInaccessibleConfig().isAvailable(), secondConfig.getInaccessibleConfig().isAvailable());
		}
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
}
