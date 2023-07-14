package com.rssl.phizic.gate.bki;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * Сервис БКИ
 * @author Puzikov
 * @ created 03.10.14
 * @ $Author$
 * @ $Revision$
 */

public interface CreditBureauService extends Service
{
	/**
	 * отправка запроса на проверку наличия кредитной истории
	 * @param client клиент
	 */
	void sendCheckCreditHistory(Client client) throws GateException;

	/**
	 * отправка запроса на получение отчета кредитной истории
	 * @param client клиент
	 * @param payment платеж, по которому оплачено получение КИ
	 */
	void sendGetCreditHistory(Client client, GateDocument payment) throws GateException;
}
