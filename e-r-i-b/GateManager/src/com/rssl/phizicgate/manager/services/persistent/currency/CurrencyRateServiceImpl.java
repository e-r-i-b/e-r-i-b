package com.rssl.phizicgate.manager.services.persistent.currency;

import com.rssl.phizic.common.types.*;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.manager.services.persistent.PersistentServiceBase;

/**
 * @author Krenev
 * @ created 30.04.2009
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyRateServiceImpl extends PersistentServiceBase<CurrencyRateService> implements CurrencyRateService
{
	public CurrencyRateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public CurrencyRate getRate(Currency from, Currency to, CurrencyRateType type, Office office,
	                            String tarifPlanCodeType) throws GateException, GateLogicException
	{
		return delegate.getRate(from, to, type, removeRouteInfo(office), tarifPlanCodeType);
	}

	public CurrencyRate convert(Money from, Currency to, Office office, String tarifPlanCodeType) throws GateException, GateLogicException
	{
		return delegate.convert(from, to, removeRouteInfo(office), tarifPlanCodeType);
	}

	public CurrencyRate convert(Currency from, Money to, Office office, String tarifPlanCodeType) throws GateException, GateLogicException
	{
		return delegate.convert(from, to, removeRouteInfo(office), tarifPlanCodeType);
	}
}
