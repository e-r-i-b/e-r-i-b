package com.rssl.phizicgate.manager.services.persistent.offices;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.officies.BackRefOfficeGateService;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.dictionaries.officies.OfficeGateService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.manager.services.persistent.PersistentServiceBase;

/**
 * Персистентная прослойка для обратного сервиса получения офисов
 * @author niculichev
 * @ created 25.09.13
 * @ $Author$
 * @ $Revision$
 */
public class BackRefOfficeGateServiceImpl extends PersistentServiceBase<BackRefOfficeGateService> implements BackRefOfficeGateService
{
	public BackRefOfficeGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public Office getOfficeById(String id) throws GateException, GateLogicException
	{
		return delegate.getOfficeById(storeRouteInfo(id));
	}

	public Office getOfficeByCode(Code code) throws GateException, GateLogicException
	{
		Office office = delegate.getOfficeByCode(code);
		if(office == null)
			return null;

		return removeRouteInfo(office);
	}
}
