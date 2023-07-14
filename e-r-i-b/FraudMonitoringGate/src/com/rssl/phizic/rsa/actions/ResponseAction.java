package com.rssl.phizic.rsa.actions;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.exceptions.RSAIntegrationException;
import com.rssl.phizic.rsa.exceptions.ProhibitionOperationFraudGateException;
import com.rssl.phizic.rsa.exceptions.RequireAdditionConfirmFraudGateException;
import com.rssl.phizic.rsa.integration.ws.control.generated.GenericRequest;
import com.rssl.phizic.rsa.integration.ws.control.generated.GenericResponse;

/**
 * @author tisov
 * @ created 04.02.15
 * @ $Author$
 * @ $Revision$
 * интерфейса экшна, посылающего фрод-мониторинг-запросы и обрабатывающего овтеты
 */
public interface ResponseAction<RQ extends GenericRequest, RS extends GenericResponse>
{
	/**
	 * Отправить запрос во ВС ФМ
	 */
	void send() throws GateLogicException, GateException;

	/**
	 * Выполнить взаимодействие с ВС ФМ
	 * @param response запрос
	 */
	void process(RS response) throws GateLogicException, GateException;

	/**
	 * @return соответствующий данному экшну фрод-запрос
	 */
	RQ getRequest();
}
