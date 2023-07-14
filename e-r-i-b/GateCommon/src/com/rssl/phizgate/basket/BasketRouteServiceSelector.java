package com.rssl.phizgate.basket;

import com.rssl.phizic.BasketPaymentsListenerConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.basket.BasketRouteService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.impl.AbstractService;

/**
 * @author niculichev
 * @ created 04.06.15
 * @ $Author$
 * @ $Revision$
 */
public class BasketRouteServiceSelector extends AbstractService implements com.rssl.phizic.gate.basket.BasketRouteService
{
	private BasketRouteService autoPayDelegate;
	private BasketRouteService esbDelegate;

	public BasketRouteServiceSelector(GateFactory factory)
	{
		super(factory);
		autoPayDelegate = (BasketRouteService) getDelegate(BasketRouteService.class.getName() + ".autopay");
		esbDelegate = (BasketRouteService) getDelegate(BasketRouteService.class.getName() + ".esb");
	}

	public void acceptBillBasketExecute(String message) throws GateException
	{
		getBusinessDelegate().acceptBillBasketExecute(message);
	}

	public void addBillBasketInfo(String request, String messageId) throws GateException
	{
		getBusinessDelegate().addBillBasketInfo(request, messageId);
	}

	private BasketRouteService getBusinessDelegate()
	{
		BasketPaymentsListenerConfig config = ConfigFactory.getConfig(BasketPaymentsListenerConfig.class);
		switch (config.getWorkingMode())
		{
			case esb:
				return esbDelegate;
			case autopay:
				return autoPayDelegate;
			default:
				throw new IllegalStateException("Не известный режим обработки сообщений корзины: " + config.getWorkingMode());
		}
	}
}
