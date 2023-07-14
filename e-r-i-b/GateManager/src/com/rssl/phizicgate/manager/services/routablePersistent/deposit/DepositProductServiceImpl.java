package com.rssl.phizicgate.manager.services.routablePersistent.deposit;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.deposit.DepositProductService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.manager.services.objects.OfficeWithoutRouteInfo;
import com.rssl.phizicgate.manager.services.routablePersistent.RoutablePersistentServiceBase;
import org.w3c.dom.Document;

/**
 * @author bogdanov
 * @ created 29.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class DepositProductServiceImpl extends RoutablePersistentServiceBase<DepositProductService> implements DepositProductService
{
	public DepositProductServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	protected DepositProductService endService(String routeInfo) throws GateLogicException, GateException
	{
		return getDelegateFactory(routeInfo).service(DepositProductService.class);
	}

	public Document getDepositsInfo(Office office) throws GateException, GateLogicException
	{
		OfficeWithoutRouteInfo officeInner = removeRouteInfo(office);
		return endService(officeInner.getRouteInfo()).getDepositsInfo(officeInner);
	}

	public Document getDepositProduct(Document params, Office office) throws GateException, GateLogicException
	{
		OfficeWithoutRouteInfo officeInner = removeRouteInfo(office);
		return endService(officeInner.getRouteInfo()).getDepositProduct(params, officeInner);
	}
}
