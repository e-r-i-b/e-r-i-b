package com.rssl.phizicgate.esberibgate.ws.jms.common.message;

/**
 * @author akrenev
 * @ created 21.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * процессор оффлайн jms сообщений
 */

public interface OfflineMessageProcessor extends MessageProcessor<OfflineMessageProcessor>
{
	/**
	 * действия, необходимые после отправки запроса
	 * @param request запрос
	 */
	void processAfterSend(Request request);
}
