package com.rssl.phizic.gate.ips;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;

import java.util.List;

/**
 * @author Erkin
 * @ created 26.07.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сервис для выполнения запросов к ИПС
 */
public interface IPSExecutiveService extends Service
{
	/**
	 * Выполнить заявки на получение операций по картам
	 * Результаты принимает IPSReceiverService
	 * @param claims - список заявок
	 */
	void executeCardOperationClaims(List<IPSCardOperationClaim> claims) throws GateException;
}
