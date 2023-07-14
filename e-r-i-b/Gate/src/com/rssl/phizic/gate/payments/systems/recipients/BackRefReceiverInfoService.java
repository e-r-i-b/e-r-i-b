package com.rssl.phizic.gate.payments.systems.recipients;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * Обратный сервис BackRefReceiverInfoService предоставляет информацию о получателе платежа,
 * хранящуюся в б.д.
 *
 * @author khudyakov
 * @ created 20.03.2011
 * @ $Author$
 * @ $Revision$
 */
public interface BackRefReceiverInfoService extends Service
{
	/**
	 * Вернуть информацию о получателе платежа из б.д.
	 * @param pointCode   код поставщика услуг
	 * @param serviceCode код услуги в биллинговой системе
	 * @return информация о поставщике кслуг
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public BusinessRecipientInfo getRecipientInfo(String pointCode, String serviceCode) throws GateException, GateLogicException;
}
