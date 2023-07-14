package com.rssl.phizic.esb.ejb.mdm;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author akrenev
 * @ created 17.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * ќбработчик сообщени€
 */

public interface MessageProcessor
{
	/**
	 * получить тип сообщени€
	 * @param message сообщение
	 * @return тип сообщени€
	 */
	String getMessageType(Object message);

	/**
	 * получить идентификатор сообщени€
	 * @param message сообщение
	 * @return идентификатор сообщени€
	 */
	String getMessageId(Object message);

	/**
	 * обработать сообщение
	 * @param message сообщение
	 */
	void processMessage(Object message) throws GateLogicException, GateException;
}
