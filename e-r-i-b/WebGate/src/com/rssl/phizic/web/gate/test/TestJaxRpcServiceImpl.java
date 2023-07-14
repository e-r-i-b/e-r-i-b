package com.rssl.phizic.web.gate.test;

import com.rssl.phizic.web.gate.test.generated.Card;
import com.rssl.phizic.web.gate.test.generated.Client;
import com.rssl.phizic.web.gate.test.generated.TestJaxRpcService;

import java.rmi.RemoteException;

/**
 * Реализация тестового JAX-RPC интерфейса
 * @author Puzikov
 * @ created 11.09.13
 * @ $Author$
 * @ $Revision$
 */

public class TestJaxRpcServiceImpl implements TestJaxRpcService
{

	/**
	 * Реализация тестового метода
	 * @param card
	 * @return
	 * @throws RemoteException
	 */
	public Client testCardToClient(Card card) throws RemoteException
	{
		return null;
	}
}
