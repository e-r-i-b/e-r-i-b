package com.rssl.phizicgate.manager.services.persistent.offices;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.dictionaries.officies.OfficeGateService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.manager.services.persistent.PersistentServiceBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Krenev
 * @ created 05.06.2009
 * @ $Author$
 * @ $Revision$
 * Врапер для обеспечения уникальности идентификаторов офисов
 */
public class OfficeGateServiceImpl extends PersistentServiceBase<OfficeGateService> implements OfficeGateService
{
	public OfficeGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public Office getOfficeById(String id) throws GateException, GateLogicException
	{
		Office office = delegate.getOfficeById(removeRouteInfo(id));
		return storeRouteInfo(office);
	}

	public List<Office> getAll(Office template, int firstResult, int listLimit) throws GateException, GateLogicException
	{
		List<Office> offices = delegate.getAll(template, firstResult, listLimit);
		List<Office> rezult = new ArrayList<Office>();
		for (Office office : offices)
		{
			rezult.add(storeRouteInfo(office));
		}
		return rezult;
	}

	public List<Office> getAll(int firstResult, int maxResults) throws GateException, GateLogicException
	{
		List<Office> offices = delegate.getAll(firstResult, maxResults);
		List<Office> rezult = new ArrayList<Office>();
		for (Office office : offices)
		{
			rezult.add(storeRouteInfo(office));
		}
		return rezult;
	}
}
