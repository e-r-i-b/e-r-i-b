package com.rssl.phizic.monitoring.client.jms;

import com.rssl.phizic.events.EventHandler;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.monitoring.UpdateMonitoringConfigEvent;
import com.rssl.phizic.gate.monitoring.UpdateMonitoringGateConfigService;

/**
 * @author akrenev
 * @ created 18.01.2013
 * @ $Author$
 * @ $Revision$
 *
 * ������� ������� ��������� �������� ����������� + �������������� ������� �����
 */

public class UpdateMonitoringConfigEventHandler implements EventHandler<UpdateMonitoringConfigEvent>
{
	/**
	 * �����������, ���������� ConfigFactory, ���������� ��� ������ ������� ��������
	 */
	public UpdateMonitoringConfigEventHandler()
	{
	}

	public void process(UpdateMonitoringConfigEvent event) throws Exception
	{
		UpdateMonitoringGateConfigService service = GateSingleton.getFactory().service(UpdateMonitoringGateConfigService.class);
		service.update(event.getConfig());
	}
}
