package com.rssl.phizicgate.iqwave.listener;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizicgate.iqwave.messaging.MessageInfoContainer;

/**
 * @author Krenev
 * @ created 25.05.2010
 * @ $Author$
 * @ $Revision$
 */
public interface MesssageProcessor
{
	/**
	 * обработать сообщение
	 * @param request сообщение для обработки
	 * @return результат обработки
	 */
	String process(MessageInfoContainer request) throws GateException, GateLogicException;
}
