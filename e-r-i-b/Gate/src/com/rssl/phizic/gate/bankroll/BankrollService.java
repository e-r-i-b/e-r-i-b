/***********************************************************************
 * Module:  BankrollService.java
 * Author:  Omeliyanchuk
 * Purpose: Defines the Interface BankrollService
 ***********************************************************************/

package com.rssl.phizic.gate.bankroll;

import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.cache.proxy.Cachable;
import com.rssl.phizic.gate.cache.proxy.composers.*;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.Calendar;
import java.util.List;

/**
 * Получение информации по счетам и картам пользователя
 * ВАЖНО!!! Заполненость полей зависит от реализации конкретного гейта!!!!
 */
public interface BankrollService extends Service
{
   /**
    * Список счетов клиента.
    * Если клиент не найден - бросается GateException
    * Если нет счетов - пустой список
    *
    * @param client - клиент
    * @return список счетов
    * @exception GateException
    */
   @Cachable(keyResolver= ClientCacheKeyComposer.class, name = "Bankroll.clientAccounts")
   List<Account> getClientAccounts(Client client) throws GateException, GateLogicException;

   /**
    * Получить счет
    * Если счет не найден - бросается GateExceprion
    *
    * @param accountIds Список внешних ID счета (Domain: ExternalID)
    * @return список счетов
    */
   @Cachable(keyResolver= AccountIDCacheKeyComposer.class, name = "Bankroll.accounts")
   GroupResult<String, Account> getAccount(String... accountIds);

	/**
	 * получить счет по номеру в заданном офисе
	 * @param accountInfo - Информация по счетам - номер и офис
	 * @return  список счетов
	 */
   @Cachable(keyResolver= PairCacheKeyComposer.class, name = "Bankroll.accountByNumber")
	GroupResult<Pair<String, Office>, Account> getAccountByNumber(Pair<String, Office>... accountInfo);

   /**
    * Получить выписки.
    *
    *
    * @param object Объект для которого должна быть построена выписка.
    *  Может быть Account, Card, Deposit.
    * @param fromDate Начальная дата (включая ее) (Domain: Date)
    * @param toDate Конечная дата (включая ее) (Domain: Date)
    * @return выписка
    * @exception GateException
    */
   @Cachable(keyResolver= FullAbstractCacheKeyComposer.class,linkable = false, name = "Bankroll.abstract")
   AbstractBase getAbstract(Object object, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateLogicException, GateException;
	/**
	 * Получить выписку по последним операциям. 
	 *
	 *
	 * @param object Объект для которого должна быть построена выписка.
	 *  Может быть Account, Card, Deposit.
	 * @param number Кол-во записей, небольше которого необходимо вернуть. Либо null, если кол-во должно быть определено гейтом.
	 * @return выписка
	 * @exception GateException
	 */
    @Cachable(keyResolver= NumberAbstractCacheKeyComposer.class,linkable = false,resolverParams="1", name = "Bankroll.abstracts")
	GroupResult<Object, AbstractBase> getAbstract(java.lang.Long number, Object... object);

   /**
    * Список карт клиента.
    * Если клиент не найден - бросается GateException
    * Если нет карт - пустой список
    *
    * @param client клиент (Domain: ExternalID)
    * @return список карт
    * @exception GateException
    */
   @Cachable(keyResolver= ClientCacheKeyComposer.class, name = "Bankroll.clientCards")
   List<Card> getClientCards(Client client) throws GateException, GateLogicException;
   /**
    * Получить детальную информацию по карте
    * @param cardIds Внешние ID карты (Domain: ExternalID)
    * @return <ID карты, карта>
    * @exception GateException
    * @exception GateLogicException
    */
   @Cachable(keyResolver= CardIDCacheKeyComposer.class, name = "Bankroll.card")
   GroupResult<String, Card> getCard(String... cardIds);

   /**
    * Получить список дополнительных карт по основной карте
    * Если доп карт нет возвращается пустой список
    *
    * @param mainCard Основная карта по которой получаются доп карты
    * @return список
    * @exception GateException
    */
   @Cachable(keyResolver= CardCacheKeyComposer.class, name = "Bankroll.additionalCards")
   GroupResult<Card, List<Card>> getAdditionalCards(Card... mainCard);

   /**
    * Получить СКС для карты
    *
    * @param card Карта для которой ищется СКС
    * @return CKC
    * @exception GateException
    */
   @Cachable(keyResolver= CardCacheKeyComposer.class, cachingWithNullValue = true, name = "Bankroll.cardPrimaryAccount")
   GroupResult<Card, Account> getCardPrimaryAccount(Card... card);

   /**
    * Получить данные о владельце карты
    * Данные должны быть идентичны возвращаемым методом getClientById сервиса ClientService
    *
    * @param card Карта для которой ищется владелец
    * @return владелец
    * @exception GateException
    */
    @Cachable(keyResolver= CardCacheKeyComposer.class, name = "Bankroll.ownerInfo")
	GroupResult<Card, Client> getOwnerInfo(Card... card);

	/**
	 * Получение данных о владельце карты
	 *
	 * @param cardInfo информация по карте, состоит из:
	 *  номер карты; офис, в котором ищем клиента, в данном случае тербанк, т.к. rbTbBranch формируется из ТБ + 000000
	 * @return владелец карты
	 */
	@Cachable(keyResolver = PairCacheKeyComposer.class, name = "Bankroll.ownerInfoByCard")
	GroupResult<Pair<String, Office>, Client> getOwnerInfoByCardNumber(Pair<String, Office> ... cardInfo);

   /**
    * Получить данные о владельце счета.
    * Данные должны быть идентичны возвращаемым методом getClientById сервиса ClientService
    *
    * @param account Счет для которой ищется владелец
    * @return владелец
    * @exception GateException
    */
	@Cachable(keyResolver= AccountCacheKeyComposer.class, name = "Bankroll.accountOwnerInfo")
	GroupResult<Account, Client> getOwnerInfo(Account... account);
	  /**
    * Получить справку о состоянии вклада(расширенная выписка)
    *
    * @param account счет для получения справки
    * @param fromDate Дата начала периода
    * @param toDate Дата конца периода
    * @return справка о состоянии вклада
    */
	@Cachable(keyResolver= FullAbstractCacheKeyComposer.class,linkable = false, name = "Bankroll.accountExtendedAbstract")
	AccountAbstract getAccountExtendedAbstract(Account account, Calendar fromDate, Calendar toDate) throws GateException, GateLogicException;

	/**
    * Получить карту по ее номеру.
    * По возможности надо пользоваться поиску по идентификатору.
    *
    * @param client клиент, который или в контексте которого запрашивается информация
	* @param cardInfo Информация по картам - номер и офис. Офис может быть null, тогда ищем по всем внешним системам.
    * @return список карт
    */
	@Cachable(keyResolver= CardByNumberCacheKeyComposer.class, resolverParams = "1", name = "Bankroll.cardByNumber")
	GroupResult<Pair<String,Office>,Card> getCardByNumber(Client client, Pair<String, Office>... cardInfo);

	/**
	 * Получить справку по начисленным пенсиям
	 *
	 * @param account счет для получения справки
	 * @param fromDate Дата начала периода
	 * @param toDate Дата конца периода
	 * @return справка по начисленным пенсиям
	 */
	@Cachable(keyResolver= FullAbstractCacheKeyComposer.class,linkable = false, name = "Bankroll.getAccHistoryFullExtract")
	AccountAbstract getAccHistoryFullExtract(Account account, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateException, GateLogicException;
}