package com.rssl.phizicgate.manager.services.routable.currency;

import com.rssl.phizic.common.types.*;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.config.GateConnectionConfig;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;
import com.rssl.phizicgate.manager.services.routable.RoutableServiceBase;

/**
 * @author hudyakov
 * @ created 11.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class CurrencyRateServiceImpl extends RoutableServiceBase implements CurrencyRateService
{
	private static final String ESB_KEY = CurrencyRateService.class.getName()+".esb";
	private CurrencyRateService esb;

	public CurrencyRateServiceImpl(GateFactory factory)
	{
		super(factory);
		try
		{
			String esbClassName = ConfigFactory.getConfig(GateConnectionConfig.class).getProperty(ESB_KEY);
			Class esbService = ClassHelper.loadClass(esbClassName);
			esb = (CurrencyRateService) esbService.getConstructor(GateFactory.class).newInstance(factory);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public CurrencyRate getRate(Currency from, Currency to, CurrencyRateType type, Office office,
	                            String tarifPlanCodeType) throws GateException, GateLogicException {
		if(!ESBHelper.isESBSupported(office))
		{
			GateInfoService service = getFactory().service(GateInfoService.class);
			if(!service.isCurrencyRateAvailable(office))
				return null;
			CurrencyRateService delegate = getDelegateFactory(office).service(CurrencyRateService.class);

			return delegate.getRate(from, to, type, office, StringHelper.getZeroIfEmptyOrNull(tarifPlanCodeType));
		}
		else
			return esb.getRate(from, to, type, office, tarifPlanCodeType);

	}

	public CurrencyRate convert(Money from, Currency to, Office office, String tarifPlanCodeType) throws GateException, GateLogicException
	{
		if (!ESBHelper.isESBSupported(office))
		{
			GateInfoService service = getFactory().service(GateInfoService.class);
			if(!service.isCurrencyRateAvailable(office))
				return null;
			CurrencyRateService delegate = getDelegateFactory(office).service(CurrencyRateService.class);
			return delegate.convert(from, to, office, StringHelper.getZeroIfEmptyOrNull(tarifPlanCodeType));
		}
		else
			return esb.convert(from, to, office, tarifPlanCodeType);

	}

	public CurrencyRate convert(Currency from, Money to, Office office, String tarifPlanCodeType) throws GateException, GateLogicException
	{
		if(!ESBHelper.isESBSupported(office))
		{
			GateInfoService service = getFactory().service(GateInfoService.class);
			if(!service.isCurrencyRateAvailable(office))
				return null;
			CurrencyRateService delegate = getDelegateFactory(office).service(CurrencyRateService.class);
			return delegate.convert(from, to, office, tarifPlanCodeType);
		}
		else
			return esb.convert(from, to, office, tarifPlanCodeType);
	}
}
