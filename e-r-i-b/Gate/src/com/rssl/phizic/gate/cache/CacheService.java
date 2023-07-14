package com.rssl.phizic.gate.cache;

import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.insurance.InsuranceApp;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.gate.loyalty.LoyaltyProgram;
import com.rssl.phizic.gate.security.SecurityAccount;

/**
 * @author Omeliyanchuk
 * @ created 25.03.2008
 * @ $Author$
 * @ $Revision$
 */

/*
��������� ��� ������ � ����� �� �������, �.�. ��� ������������� ������� GateBusinessCacheSingleton
 */
public interface CacheService extends Service
{
	/**
	 * �������� ��� ��������� � ��������
	 *
	 * @param client
	 * @throws GateException
	 */
	void clearClientCache(Client client) throws GateException, GateLogicException;

	/**
	 * �������� ��� �� ��������� �������
	 * @param client - ������
	 * @param clazz - ������ ������� ��������� (������ ���������� �������� �� ���� ����� ���������, clazz �� ������������!)
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void clearClientProductsCache(Client client, Class... clazz) throws GateException, GateLogicException;

	/**
	 * �������� ���� ��� ��������� �� ������ �������
	 * @param account
	 * @throws GateException
	 */
	void clearAccountCache(Account account) throws GateException, GateLogicException;

	/**
	 * �������� ���� ��� ��������� � ������ �������
	 * @param card
	 * @throws GateException
	 */
	void clearCardCache(Card card) throws GateException, GateLogicException;

	/**
	 * �������� ���� ��� ��������� � ������� �������
	 * @param deposit
	 * @throws GateException
	 */
	void clearDepositCache(Deposit deposit) throws GateException, GateLogicException;

	/**
	 * �������� ���� ��� ��������� � ������ �����
	 * @param rate
	 * @param office
	 * @throws GateException
	 */
	void clearCurrencyRateCache(CurrencyRate rate, Office office) throws GateException, GateLogicException;

	/**
	 * �������� ���� ��� ��������� � ���
	 * @param imAccount
	 * @throws GateException
	 */
	void clearIMACache(IMAccount imAccount) throws GateException, GateLogicException;

	/**
	 * �������� ���� ���, ��������� � ���������
	 * @param loan
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void clearLoanCache(Loan loan)  throws GateException, GateLogicException;

	/**
	 * �������� ���� ���, ��������� �� ������� ����
	 * @param depoAccount
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void clearDepoAccountCache(DepoAccount depoAccount)  throws GateException, GateLogicException;

	/**
	 *
	 * @param longOffer
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void clearLongOfferCache(LongOffer longOffer) throws GateException, GateLogicException;

	/**
	 * �������� ���� ���, ��������� � �������������
	 * @param autoPayment ����������
	 * @param cards ������ ����
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void clearAutoPaymentCache(AutoPayment autoPayment, Card ... cards) throws GateException, GateLogicException;

	/**
	 * �������� ��� ��������� � �������������
	 * @param autoSubscription �������������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void clearAutoSubscriptionCache(AutoSubscription autoSubscription) throws GateException, GateLogicException;

	/**
	 * �������� ��� ��������� � ���������� ����������
	 * @param loyaltyProgram ��������� ����������
	 */
	void clearLoyaltyProgramCache(LoyaltyProgram loyaltyProgram) throws GateException, GateLogicException;

	/**
	 * �������� ��� ��������� �� ��������� ���������
	 * @param insuranceApp ��������� �������
	 */
	void clearInsuranceAppCache(InsuranceApp insuranceApp) throws GateException, GateLogicException;

	/**
	 * �������� ��� ��������� � �������������� ������������
	 * @param securityAccount �������������� ����������
	 */
	void clearSecurityAccountCache(SecurityAccount securityAccount) throws GateException, GateLogicException;

	/**
	 * ������� ��� �� ��������-������.
	 *
	 * @param orderUuid ������������� ������.
	 */
	void clearShopOrderCache(String orderUuid) throws GateException, GateLogicException;
}
