package com.rssl.phizic.esb.ejb;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import javax.jms.Message;

/**
 * @author akrenev
 * @ created 01.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик сообщения
 */

public interface MessageProcessor
{
	/**
	 * получить тип сообщения
	 * @param message сообщение
	 * @return тип сообщения
	 */
	String getMessageType(Object message);

	/**
	 * получить идентификатор сообщения
	 * @param message сообщение
	 * @return идентификатор сообщения
	 */
	String getMessageId(Object message);

	/**
	 * обработать сообщение
	 * @param message сообщение
	 * @param source исходное сообщение
	 */
	void processMessage(Object message, Message source) throws GateLogicException, GateException;
}
