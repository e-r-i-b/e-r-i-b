package com.rssl.phizic.gate.mobilebank;

import com.rssl.phizic.common.types.UUID;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.mbv.ClientAccPh;
import com.rssl.phizic.gate.mbv.MbvClientIdentity;

import java.util.List;

/**
 * User: Moshenko
 * Date: 03.09.13
 * Time: 15:57
 * Интерфейс интегрированного шлюза с МБВ
 */
public interface DepoMobileBankService extends Service
{
	/**
	 * Метод проверки принадлежности номера телефона к МБВ
	 * @param phoneNumber  номер телефона
	 * @return  список MbvClientIdentity
	 */
	List<MbvClientIdentity> checkPhoneOwn(String phoneNumber) throws GateException;

    /**
     * Метод получения списка вкладов, списка телефонов с временем последнего использования по ФИО, ДУЛ, ДР клиента
     * @param clientIdentity Идентификационные данные клиента
     * @return  Телефоны и счетами клиента в МБВ
     * @throws GateException
     */
    ClientAccPh getClientAccPh(MbvClientIdentity clientIdentity)throws GateException;

    /**
     * Метод для старта транзакции миграции клиента
     * @param clientIdentity Идентификационные данные клиента
       @return  Идентификатор миграции клиента
     * @throws GateException
     */
    UUID beginMigration(MbvClientIdentity clientIdentity)throws GateException;

    /**
     * Метод завершения транзакции миграции клиента
     * @param migrationId Идентификатор миграции клиента
     * @throws GateException
     */
    void commitMigration(UUID migrationId)throws GateException;

    /**
     * Метод отмены старта транзакции миграции клиента
     * @param migrationId Идентификатор миграции клиента
     * @throws GateException
     */
    void rollbackMigration(UUID migrationId)throws GateException;

	/**
	 * Метод отката миграции клиента
	 * @param migrationId Идентификатор миграции клиента
	 * @throws GateException
	 */
	void reverseMigration(UUID migrationId)throws GateException;

    /**
     * Метод отключения договора МБВ по номеру телефона
     * @param phoneNumber номер телефона
     * @throws GateException
     */
    void discByPhone(String phoneNumber)throws GateException;
}
