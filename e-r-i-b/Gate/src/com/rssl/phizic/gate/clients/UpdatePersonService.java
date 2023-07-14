package com.rssl.phizic.gate.clients;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.common.types.Money;

import java.util.Date;

/**
 * @author Omeliyanchuk
 * @ created 07.06.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * Обновление клиента в бизнесе при изменении во внешней системе.
 */
public interface UpdatePersonService extends Service
{
	/**
	 * обновить состояние клиента
	 * @param clientId внешний идентификатор клиента
	 * @param newState новый статус клиента
	 */
	void updateState(String clientId, ClientState newState) throws GateException, GateLogicException;

	/**
	 * обновить состояние клиента
	 * @param callback колбек-соеденитель, для двухфазного рассторжения
	 * @param newState новый статус клиента
	 */
	void updateState(CancelationCallBack callback, ClientState newState) throws GateException, GateLogicException;


	/**
	 * заблокировать клиента
	 * @param clientId - внешний идентификатор клиента
	 * @param lockDate - дата, начиная с которой клиенту запрещена работа в системе
	 * @param islock - признак блокировки. Если true - блокируем, false - разблокируем
	 * @param liability - Объем задолженности (при блокировке)
	 */
	void lockOrUnlock(String clientId, Date lockDate, Boolean islock, Money liability) throws GateException, GateLogicException;
}
