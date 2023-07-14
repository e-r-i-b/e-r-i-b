package com.rssl.phizic.gate.ips;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Erkin
 * @ created 26.07.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Заявка на получение операций по карте
 */
public interface IPSCardOperationClaim extends Serializable
{
	/**
	 * @return клиент, по которому надо получить транзакции
	 */
	Client getClient();
	
	/**
	 * @return карта, по которой надо получить транзакции
	 */
	Card getCard();

	/**
	 * @return дата, начиная с которой нужно получить транзакции (включительно)
	 */
	Calendar getStartDate();
}
