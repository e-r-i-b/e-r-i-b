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
 * ��������� ���������� �� ������ � ������ ������������
 * �����!!! ������������ ����� ������� �� ���������� ����������� �����!!!!
 */
public interface BankrollService extends Service
{
   /**
    * ������ ������ �������.
    * ���� ������ �� ������ - ��������� GateException
    * ���� ��� ������ - ������ ������
    *
    * @param client - ������
    * @return ������ ������
    * @exception GateException
    */
   @Cachable(keyResolver= ClientCacheKeyComposer.class, name = "Bankroll.clientAccounts")
   List<Account> getClientAccounts(Client client) throws GateException, GateLogicException;

   /**
    * �������� ����
    * ���� ���� �� ������ - ��������� GateExceprion
    *
    * @param accountIds ������ ������� ID ����� (Domain: ExternalID)
    * @return ������ ������
    */
   @Cachable(keyResolver= AccountIDCacheKeyComposer.class, name = "Bankroll.accounts")
   GroupResult<String, Account> getAccount(String... accountIds);

	/**
	 * �������� ���� �� ������ � �������� �����
	 * @param accountInfo - ���������� �� ������ - ����� � ����
	 * @return  ������ ������
	 */
   @Cachable(keyResolver= PairCacheKeyComposer.class, name = "Bankroll.accountByNumber")
	GroupResult<Pair<String, Office>, Account> getAccountByNumber(Pair<String, Office>... accountInfo);

   /**
    * �������� �������.
    *
    *
    * @param object ������ ��� �������� ������ ���� ��������� �������.
    *  ����� ���� Account, Card, Deposit.
    * @param fromDate ��������� ���� (������� ��) (Domain: Date)
    * @param toDate �������� ���� (������� ��) (Domain: Date)
    * @return �������
    * @exception GateException
    */
   @Cachable(keyResolver= FullAbstractCacheKeyComposer.class,linkable = false, name = "Bankroll.abstract")
   AbstractBase getAbstract(Object object, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateLogicException, GateException;
	/**
	 * �������� ������� �� ��������� ���������. 
	 *
	 *
	 * @param object ������ ��� �������� ������ ���� ��������� �������.
	 *  ����� ���� Account, Card, Deposit.
	 * @param number ���-�� �������, �������� �������� ���������� �������. ���� null, ���� ���-�� ������ ���� ���������� ������.
	 * @return �������
	 * @exception GateException
	 */
    @Cachable(keyResolver= NumberAbstractCacheKeyComposer.class,linkable = false,resolverParams="1", name = "Bankroll.abstracts")
	GroupResult<Object, AbstractBase> getAbstract(java.lang.Long number, Object... object);

   /**
    * ������ ���� �������.
    * ���� ������ �� ������ - ��������� GateException
    * ���� ��� ���� - ������ ������
    *
    * @param client ������ (Domain: ExternalID)
    * @return ������ ����
    * @exception GateException
    */
   @Cachable(keyResolver= ClientCacheKeyComposer.class, name = "Bankroll.clientCards")
   List<Card> getClientCards(Client client) throws GateException, GateLogicException;
   /**
    * �������� ��������� ���������� �� �����
    * @param cardIds ������� ID ����� (Domain: ExternalID)
    * @return <ID �����, �����>
    * @exception GateException
    * @exception GateLogicException
    */
   @Cachable(keyResolver= CardIDCacheKeyComposer.class, name = "Bankroll.card")
   GroupResult<String, Card> getCard(String... cardIds);

   /**
    * �������� ������ �������������� ���� �� �������� �����
    * ���� ��� ���� ��� ������������ ������ ������
    *
    * @param mainCard �������� ����� �� ������� ���������� ��� �����
    * @return ������
    * @exception GateException
    */
   @Cachable(keyResolver= CardCacheKeyComposer.class, name = "Bankroll.additionalCards")
   GroupResult<Card, List<Card>> getAdditionalCards(Card... mainCard);

   /**
    * �������� ��� ��� �����
    *
    * @param card ����� ��� ������� ������ ���
    * @return CKC
    * @exception GateException
    */
   @Cachable(keyResolver= CardCacheKeyComposer.class, cachingWithNullValue = true, name = "Bankroll.cardPrimaryAccount")
   GroupResult<Card, Account> getCardPrimaryAccount(Card... card);

   /**
    * �������� ������ � ��������� �����
    * ������ ������ ���� ��������� ������������ ������� getClientById ������� ClientService
    *
    * @param card ����� ��� ������� ������ ��������
    * @return ��������
    * @exception GateException
    */
    @Cachable(keyResolver= CardCacheKeyComposer.class, name = "Bankroll.ownerInfo")
	GroupResult<Card, Client> getOwnerInfo(Card... card);

	/**
	 * ��������� ������ � ��������� �����
	 *
	 * @param cardInfo ���������� �� �����, ������� ��:
	 *  ����� �����; ����, � ������� ���� �������, � ������ ������ �������, �.�. rbTbBranch ����������� �� �� + 000000
	 * @return �������� �����
	 */
	@Cachable(keyResolver = PairCacheKeyComposer.class, name = "Bankroll.ownerInfoByCard")
	GroupResult<Pair<String, Office>, Client> getOwnerInfoByCardNumber(Pair<String, Office> ... cardInfo);

   /**
    * �������� ������ � ��������� �����.
    * ������ ������ ���� ��������� ������������ ������� getClientById ������� ClientService
    *
    * @param account ���� ��� ������� ������ ��������
    * @return ��������
    * @exception GateException
    */
	@Cachable(keyResolver= AccountCacheKeyComposer.class, name = "Bankroll.accountOwnerInfo")
	GroupResult<Account, Client> getOwnerInfo(Account... account);
	  /**
    * �������� ������� � ��������� ������(����������� �������)
    *
    * @param account ���� ��� ��������� �������
    * @param fromDate ���� ������ �������
    * @param toDate ���� ����� �������
    * @return ������� � ��������� ������
    */
	@Cachable(keyResolver= FullAbstractCacheKeyComposer.class,linkable = false, name = "Bankroll.accountExtendedAbstract")
	AccountAbstract getAccountExtendedAbstract(Account account, Calendar fromDate, Calendar toDate) throws GateException, GateLogicException;

	/**
    * �������� ����� �� �� ������.
    * �� ����������� ���� ������������ ������ �� ��������������.
    *
    * @param client ������, ������� ��� � ��������� �������� ������������� ����������
	* @param cardInfo ���������� �� ������ - ����� � ����. ���� ����� ���� null, ����� ���� �� ���� ������� ��������.
    * @return ������ ����
    */
	@Cachable(keyResolver= CardByNumberCacheKeyComposer.class, resolverParams = "1", name = "Bankroll.cardByNumber")
	GroupResult<Pair<String,Office>,Card> getCardByNumber(Client client, Pair<String, Office>... cardInfo);

	/**
	 * �������� ������� �� ����������� �������
	 *
	 * @param account ���� ��� ��������� �������
	 * @param fromDate ���� ������ �������
	 * @param toDate ���� ����� �������
	 * @return ������� �� ����������� �������
	 */
	@Cachable(keyResolver= FullAbstractCacheKeyComposer.class,linkable = false, name = "Bankroll.getAccHistoryFullExtract")
	AccountAbstract getAccHistoryFullExtract(Account account, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateException, GateLogicException;
}