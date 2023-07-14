package com.rssl.phizic.rsa.senders.builders;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.GenericRequest;

/**
 * Билдер запросов во ВС ФМ
 *
 * @author khudyakov
 * @ created 14.06.15
 * @ $Author$
 * @ $Revision$
 */
public interface RequestBuilder<RQ>
{
	/**
	 * Построить запрос во фрод-мониторинг
	 * @return запрос
	 */
	RQ build() throws GateLogicException, GateException;
}
