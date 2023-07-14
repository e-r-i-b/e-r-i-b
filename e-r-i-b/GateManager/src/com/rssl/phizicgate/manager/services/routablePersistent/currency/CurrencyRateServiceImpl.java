package com.rssl.phizicgate.manager.services.routablePersistent.currency;

import com.rssl.phizic.common.types.*;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.manager.services.objects.OfficeWithoutRouteInfo;
import com.rssl.phizicgate.manager.services.routablePersistent.RoutablePersistentServiceBase;

/**
 * @author bogdanov
 * @ created 29.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyRateServiceImpl extends RoutablePersistentServiceBase<CurrencyRateService> implements CurrencyRateService
{
	public CurrencyRateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	protected CurrencyRateService endService(String routeInfo) throws GateLogicException, GateException
	{
		return getDelegateFactory(routeInfo).service(CurrencyRateService.class);
	}

	public CurrencyRate getRate(Currency from, Currency to, CurrencyRateType type, Office office,
	                            String tarifPlanCodeType) throws GateException, GateLogicException
	{
		OfficeWithoutRouteInfo officeInner = removeRouteInfo(office);
		return (CurrencyRate) storeRouteInfo(endService(officeInner.getRouteInfo()).getRate(from, to, type,
				officeInner, tarifPlanCodeType), officeInner.getRouteInfo());
	}

	public CurrencyRate convert(Money from, Currency to, Office office, String tarifPlanCodeType) throws GateException, GateLogicException
	{
		OfficeWithoutRouteInfo officeInner = removeRouteInfo(office);

		return (CurrencyRate) storeRouteInfo(endService(officeInner.getRouteInfo()).convert(from, to, officeInner,
				tarifPlanCodeType), officeInner.getRouteInfo());
	}

	public CurrencyRate convert(Currency from, Money to, Office office, String tarifPlanCodeType) throws GateException, GateLogicException
	{
		OfficeWithoutRouteInfo officeInner = removeRouteInfo(office);

		return (CurrencyRate) storeRouteInfo(endService(officeInner.getRouteInfo()).convert(from, to, officeInner,
				tarifPlanCodeType), officeInner.getRouteInfo());
	}
}
