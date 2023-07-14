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
 * ������������ �������� ����� ��� �������� �����.
 * ������ ���� ���� "���������� id �������" + "����� �����" ��������� ���������� ���������������� ����.
 */
public interface BackRefBankrollService extends Service
{
   /**
    * ����� ���� �� ����������� id ������� � ������ ��� �����.
    * ���� ���� �� ������ - ��������� ���������� AccountNotFoundExeption
    * Domain: ExternalID
    *
    * @param accountNumber ����� ����� (Domain: AccountNumber)
	* @return id ����� �� ������� �������
    * @exception GateException
    */
    String findAccountExternalId(String accountNumber) throws GateException, GateLogicException, AccountNotFoundException;

	/**
	 * ����� ������� ������������� ����� �� ����������� ������ �����
	 * @param cardNumber ����� �����
	 * @exception GateException
	 * @return ������� �������������
	 */
	String findCardExternalId(String cardNumber) throws GateException,GateLogicException;

	/**
    * ����� ����� �� ����������� ������ �����
	* @param loginId ID ������� � ���� (login.id)
    * @param cardNumber ����� �����
    * @exception GateException
	* @return ������� �������������
    */
	String findCardExternalId(Long loginId, String cardNumber) throws GateException,GateLogicException;

	/**
	 * ����� ��������� ����� � �������
	 * @param account ����
	 * @return id ������� �� ������� �������(clientId) ��� null ���� id ������� �� ������� ������� ���� �� ��������
	 * @throws GateException
	 * @throws AccountNotFoundException ���� � ������� �� ������
	 */
	@Cachable(keyResolver= AccountCacheKeyComposer.class, name = "BackRefBankroll.findAccountBusinessOwner")
	String findAccountBusinessOwner(Account account) throws GateException, GateLogicException;

	/**
	 * �������� ����, � ������� ������������� ���� �������
	 * @param loginId ID ������� � ���� (login.id)
	 * @param accountNumber ����� �����
	 * @return ����, � ������� ������������� ���� �������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	Office getAccountOffice(Long loginId, String accountNumber) throws GateException, GateLogicException;

	/**
	 * �������� ����, � ������� ������������� ����� �������
	 * @param loginId ID ������� � ���� (login.id)
	 * @param cardNumber ����� �����
	 * @return ����, � ������� ������������� ����� ������� 
	 * @throws GateException
	 * @throws GateLogicException
	 */
	Office getCardOffice(Long loginId, String cardNumber) throws GateException, GateLogicException;

	/**
	 * �������� ����-����
	 * @param cardNumber ����� �����
	 * @return ����-����
	 * @throws GateException
	 * @throws GateLogicException
	 */
	Account getCardAccount(String cardNumber) throws GateException, GateLogicException;

	/**
	 * �������� ����-����
	 * @param loginId ID ������� � ���� (login.id)
	 * @param cardNumber ����� �����
	 * @return ����-����
	 * @throws GateException
	 * @throws GateLogicException
	 */
	Account getCardAccount(Long loginId, String cardNumber) throws GateException, GateLogicException;

	/**
	 * �������� ����� �� ��.
	 * @param loginId ID ������� � ���� (login.id)
	 * @param cardNumber ����� �����
	 * @return �����(StoredCard)
	 * @throws GateException
	 * @throws GateLogicException
	 */
	Card getStoredCard(Long loginId, String cardNumber) throws GateException, GateLogicException;
}
