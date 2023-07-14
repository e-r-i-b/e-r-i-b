package com.rssl.phizic.gate.deposit;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;

import java.util.List;

/**
 * Получение данных по депозитам
 */
public interface DepositService extends Service
{
	/**
	 * Получение списка депозитов клиента.
	 *
	 * @param client клиент
	 * @return список
	 * @exception GateException
	 */
	List<? extends Deposit> getClientDeposits(Client client) throws GateException, GateLogicException;

	/**
	 * Получение депозита по его внешнему ID
	 *
	 * @param depositId (Domain: ExternalID)
	 * @return депозит
    *  @exception GateException
	 */
	Deposit getDepositById(String depositId) throws GateException, GateLogicException;

	/**
	 * Получение расширенных данных по депозиту
	 *
	 * @param deposit депозит для которого надо получить доп данные
	 * @return расширенная информация
	 * @exception GateException
	 */
	DepositInfo getDepositInfo(Deposit deposit) throws GateException, GateLogicException;
}
