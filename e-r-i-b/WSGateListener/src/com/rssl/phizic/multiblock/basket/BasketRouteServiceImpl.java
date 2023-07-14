package com.rssl.phizic.multiblock.basket;

import com.rssl.phizgate.basket.BasketInvoiceHelper;
import com.rssl.phizic.BasketPaymentsListenerConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.multiblock.basket.generated.BasketRouteService;

import java.rmi.RemoteException;

/**
 * @author vagin
 * @ created 06.05.14
 * @ $Author$
 * @ $Revision$
 * Сервис обработки сообщений от АС "AutoPay" в рамках корзны платежей.
 */
public class BasketRouteServiceImpl implements BasketRouteService
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Gate);

	public void addBillBasketInfo(String messageText, String messageID) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.basket.BasketRouteService basketRouteService =
					GateSingleton.getFactory().service(com.rssl.phizic.gate.basket.BasketRouteService.class);

			basketRouteService.addBillBasketInfo(messageText, messageID);
		}
		catch (GateException e)
		{
			log.error(e.getMessage(), e);
			throw new RemoteException(e.getMessage());
		}
	}

	public void acceptBillBasketExecute(String messageText) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.basket.BasketRouteService basketRouteService =
					GateSingleton.getFactory().service(com.rssl.phizic.gate.basket.BasketRouteService.class);

			basketRouteService.acceptBillBasketExecute(messageText);
		}
		catch (GateException e)
		{
			log.error(e.getMessage(), e);
			throw new RemoteException(e.getMessage());
		}
	}
}
