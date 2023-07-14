package com.rssl.phizic.business.configuration.gate;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.test.BusinessTestCaseBase;

/**
 * @author akrenev
 * @ created 29.11.2012
 * @ $Author$
 * @ $Revision$
 *
 * “ест сервиса дл€ настроек мониторинга
 */

public class MonitoringGateConfigBusinessServiceTest extends BusinessTestCaseBase
{
	private static final MonitoringGateConfigBusinessService service = new MonitoringGateConfigBusinessService();

	private static final String REAL_SERVICE_NAME = "phiz-gate-iqwave.CardToCard";
	private static final String ERROR_SERVICE_NAME = "ERROR_SERVICE_NAME";

	public void testService() throws BusinessException, BusinessLogicException
	{
		assertNotNull(service.findConfig(REAL_SERVICE_NAME));
		assertNull(service.findConfig(ERROR_SERVICE_NAME));
	}

}
