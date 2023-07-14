package com.rssl.phizicgate.manager.services.persistent.monitoring;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.monitoring.IncrementMode;
import com.rssl.phizic.gate.monitoring.IncrementMonitoringCounterService;
import com.rssl.phizicgate.manager.services.persistent.PersistentServiceBase;

/**
 * @author akrenev
 * @ created 18.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class IncrementMonitoringCounterServiceImpl extends PersistentServiceBase<IncrementMonitoringCounterService> implements IncrementMonitoringCounterService
{
	public IncrementMonitoringCounterServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public void increment(String service, IncrementMode incrementMode) throws GateLogicException, GateException
	{
		delegate.increment(service, incrementMode);
	}

	public void incrementPercent(String service, IncrementMode incrementMode, boolean isFail) throws GateLogicException, GateException
	{
		delegate.incrementPercent(service, incrementMode, isFail);
	}
}
