package com.rssl.phizic.gate.test;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;

/**
 * Тестовый сервис генерации JAX-RPC сервисов
 * @author Puzikov
 * @ created 11.09.13
 * @ $Author$
 * @ $Revision$
 */

public interface TestJaxRpcService
{
	/**
	 * Тестовый метод генерации
	 * @param card
	 * @return
	 */
	public Client testCardToClient(Card card);
}
