package com.rssl.phizic.gate.ips;

import com.rssl.phizic.common.types.transmiters.GroupResult;
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
 * Сервис для приёма результатов запросов к ИПС
 */
public interface IPSReceiverService extends Service
{
	/**
	 * Принять результаты обработки заявок по получению операций по картам
	 * @param result - групповой результат с данными по карточным операциям
	 * @return список заявок, операции по которым не удалось принять
	 */
	List<IPSCardOperationClaim> receiveCardOperationClaimResult(GroupResult<IPSCardOperationClaim, List<IPSCardOperation>> result) throws GateException;

	/**
	 * Перевести заявки в статус TIMEOUT
	 * @param claims - список заявок
	 */
	void setTimeoutStatusClaims(List<IPSCardOperationClaim> claims) throws GateException;
}
