package com.rssl.phizicgate.manager.services.persistent.offices;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.dictionaries.officies.OfficeGateService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;

import java.util.List;

/**
 * @author Krenev
 * @ created 30.04.2009
 * @ $Author$
 * @ $Revision$
 */
public class OfficeGateServiceTerminator extends AbstractService implements OfficeGateService
{
	public OfficeGateServiceTerminator(GateFactory factory)
	{
		super(factory);
	}

	public Office getOfficeById(String id) throws GateException
	{
		throw new UnsupportedOperationException("использовать GateManager.getInstance().getService(externalSystem, OfficeGateService.class)");
	}

	public List<Office> getAll(Office template, int firstResult, int listLimit) throws GateException
	{
		throw new UnsupportedOperationException("использовать GateManager.getInstance().getService(externalSystem, OfficeGateService.class)");
	}

	public List<Office> getAll(int firstResult, int maxResult) throws GateException
	{
		throw new UnsupportedOperationException("использовать GateManager.getInstance().getService(externalSystem, OfficeGateService.class)");
	}
}
