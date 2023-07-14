package com.rssl.phizicgate.esberibgate.ws.jms.common.message;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.jms.MessageCreator;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;

import javax.jms.TextMessage;

/**
 * @author akrenev
 * @ created 19.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * процессор jms сообщений
 */

public interface MessageProcessor<MP extends MessageProcessor<MP>> extends MessageCreator<TextMessage>
{
	/**
	 * Формирует запрос
	 * @return запрос
	 */
	Request<MP> makeRequest() throws GateException, GateLogicException;

	/**
	 * @return сегмент шины
	 */
	ESBSegment getSegment();
}
