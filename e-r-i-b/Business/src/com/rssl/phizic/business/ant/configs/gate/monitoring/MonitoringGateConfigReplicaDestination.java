package com.rssl.phizic.business.ant.configs.gate.monitoring;

import com.rssl.phizic.business.dictionaries.QueryReplicaDestinationBase;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.monitoring.MonitoringServiceGateConfig;

/**
 * @author akrenev
 * @ created 19.02.2013
 * @ $Author$
 * @ $Revision$
 *
 * класс взаимодействия с хранилищем данных
 */

public class MonitoringGateConfigReplicaDestination extends QueryReplicaDestinationBase<MonitoringServiceGateConfig>
{
	/**
	 * Конструктор
	 * по квере,
	 * дублирующиеся записи игнориться не будут,
	 * сортировать результаты выполнения квери не нужно  
	 */
	public MonitoringGateConfigReplicaDestination()
	{
		super("com.rssl.phizic.operations.config.gate.ListMonitoringGateConfigOperation.list");
	}

	public void initialize(GateFactory factory){}
}
