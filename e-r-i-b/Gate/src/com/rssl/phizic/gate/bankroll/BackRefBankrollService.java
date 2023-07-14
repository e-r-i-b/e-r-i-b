/***********************************************************************
 * Module:  BackRefBankrollService.java
 * Author:  Evgrafov
 * Purpose: Defines the Interface BackRefBankrollService
 ***********************************************************************/

package com.rssl.phizic.gate.bankroll;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.cache.proxy.Cachable;
import com.rssl.phizic.gate.cache.proxy.composers.AccountCacheKeyComposer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * Обеспечивает обратную связь для сервисов гейта.
 * Внутри ИКФЛ ключ "внутренний id клиента" + "номер счета" позволяет однозначно идентифицировать счет.
 */
public interface BackRefBankrollService extends Service
{
   /**
    * Найти счет по внутреннему id клиента и номеру его счета.
    * Если счет не найден - бросается исключение AccountNotFoundExeption
    * Domain: ExternalID
    *
    * @param accountNumber Номер счета (Domain: AccountNumber)
	* @return id счета во внешней системе
    * @exception GateException
    */
    String findAccountExternalId(String accountNumber) throws GateException, GateLogicException, AccountNotFoundException;

	/**
	 * Найти внешний идентификатор карты по внутреннему номеру карты
	 * @param cardNumber Номер карты
	 * @exception GateException
	 * @return внешний идентификатор
	 */
	String findCardExternalId(String cardNumber) throws GateException,GateLogicException;

	/**
    * Найти карты по внутреннему номеру карты
	* @param loginId ID клиента в ИКФЛ (login.id)
    * @param cardNumber Номер карты
    * @exception GateException
	* @return внешний идентификатор
    */
	String findCardExternalId(Long loginId, String cardNumber) throws GateException,GateLogicException;

	/**
	 * Найти владельца счета в системе
	 * @param account счет
	 * @return id клиента во внешней системе(clientId) или null если id клиента во внешней системе пока не известно
	 * @throws GateException
	 * @throws AccountNotFoundException счет в системе не найден
	 */
	@Cachable(keyResolver= AccountCacheKeyComposer.class, name = "BackRefBankroll.findAccountBusinessOwner")
	String findAccountBusinessOwner(Account account) throws GateException, GateLogicException;

	/**
	 * Получить офис, в котором обслуживается счет клиента
	 * @param loginId ID клиента в ИКФЛ (login.id)
	 * @param accountNumber номер счета
	 * @return офис, в котором обслуживается счет клиента
	 * @throws GateException
	 * @throws GateLogicException
	 */
	Office getAccountOffice(Long loginId, String accountNumber) throws GateException, GateLogicException;

	/**
	 * Получить офис, в котором обслуживается карта клиента
	 * @param loginId ID клиента в ИКФЛ (login.id)
	 * @param cardNumber номер карты
	 * @return офис, в котором обслуживается карта клиента 
	 * @throws GateException
	 * @throws GateLogicException
	 */
	Office getCardOffice(Long loginId, String cardNumber) throws GateException, GateLogicException;

	/**
	 * Получить карт-счет
	 * @param cardNumber номер карты
	 * @return карт-счет
	 * @throws GateException
	 * @throws GateLogicException
	 */
	Account getCardAccount(String cardNumber) throws GateException, GateLogicException;

	/**
	 * Получить карт-счет
	 * @param loginId ID клиента в ИКФЛ (login.id)
	 * @param cardNumber номер карты
	 * @return карт-счет
	 * @throws GateException
	 * @throws GateLogicException
	 */
	Account getCardAccount(Long loginId, String cardNumber) throws GateException, GateLogicException;

	/**
	 * Получить карту из БД.
	 * @param loginId ID клиента в ИКФЛ (login.id)
	 * @param cardNumber номер карты
	 * @return карта(StoredCard)
	 * @throws GateException
	 * @throws GateLogicException
	 */
	Card getStoredCard(Long loginId, String cardNumber) throws GateException, GateLogicException;
}
