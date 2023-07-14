package com.rssl.phizicgate.manager.services.routablePersistent.systems.gorod;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.gorod.GorodCard;
import com.rssl.phizic.gate.payments.systems.gorod.GorodService;
import com.rssl.phizicgate.manager.services.objects.OfficeWithoutRouteInfo;
import com.rssl.phizicgate.manager.services.routablePersistent.RoutablePersistentServiceBase;

/**
 * @author bogdanov
 * @ created 29.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class GorodServiceImpl extends RoutablePersistentServiceBase<GorodService> implements GorodService
{
	public GorodServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	protected GorodService endService(String routeInfo) throws GateLogicException, GateException
	{
		return getDelegateFactory(routeInfo).service(GorodService.class);
	}

	public GorodCard getCard(String cardId, Office office) throws GateException, GateLogicException
	{
		OfficeWithoutRouteInfo officeInner = removeRouteInfo(office);
		return (GorodCard) storeRouteInfo(endService(officeInner.getRouteInfo()).getCard(cardId, officeInner));
	}
}
