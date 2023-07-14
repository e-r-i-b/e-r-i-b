package com.rssl.phizic.gate.basket;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author niculichev
 * @ created 04.06.15
 * @ $Author$
 * @ $Revision$
 */
public interface BasketRouteService extends Service
{
	void acceptBillBasketExecute(String message) throws GateException;

	void addBillBasketInfo(String request, String messageId) throws GateException;
}
