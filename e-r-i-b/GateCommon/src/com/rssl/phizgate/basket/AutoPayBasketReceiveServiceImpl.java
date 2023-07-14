package com.rssl.phizgate.basket;

import com.rssl.phizgate.basket.BasketInvoiceHelper;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.basket.BasketRouteService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.impl.AbstractService;

/**
 * Сервис обрбаотки запросов напрямую от автопэй
 * @author niculichev
 * @ created 04.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AutoPayBasketReceiveServiceImpl extends AbstractService implements BasketRouteService
{
	public AutoPayBasketReceiveServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public void acceptBillBasketExecute(String message) throws GateException
	{
		BasketInvoiceHelper.processAcceptBillBasketExecute(message);
	}

	public void addBillBasketInfo(String request, String messageId) throws GateException
	{
		BasketInvoiceHelper.processAddBillBasketInfo(request, messageId);
	}
}
