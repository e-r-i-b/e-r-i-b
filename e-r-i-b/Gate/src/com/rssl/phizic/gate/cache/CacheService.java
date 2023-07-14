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
Интерфейс для работы с кешем из бизнеса, т.е. для форсированной очистки GateBusinessCacheSingleton
 */
public interface CacheService extends Service
{
	/**
	 * очистить кеш связанный с клиентом
	 *
	 * @param client
	 * @throws GateException
	 */
	void clearClientCache(Client client) throws GateException, GateLogicException;

	/**
	 * Очистить кеш по продуктам клиента
	 * @param client - клиент
	 * @param clazz - массив классов продуктов (сейчас релизовано удаление по всем типам продуктов, clazz не используется!)
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void clearClientProductsCache(Client client, Class... clazz) throws GateException, GateLogicException;

	/**
	 * очистить весь кеш связанный со счетом клиента
	 * @param account
	 * @throws GateException
	 */
	void clearAccountCache(Account account) throws GateException, GateLogicException;

	/**
	 * очистить весь кеш связанный с картой клиента
	 * @param card
	 * @throws GateException
	 */
	void clearCardCache(Card card) throws GateException, GateLogicException;

	/**
	 * очистить весь кеш связанный с вкладом клиента
	 * @param deposit
	 * @throws GateException
	 */
	void clearDepositCache(Deposit deposit) throws GateException, GateLogicException;

	/**
	 * очистить весь кеш связанный с курсом валют
	 * @param rate
	 * @param office
	 * @throws GateException
	 */
	void clearCurrencyRateCache(CurrencyRate rate, Office office) throws GateException, GateLogicException;

	/**
	 * очистить весь кеш связанный с ОМС
	 * @param imAccount
	 * @throws GateException
	 */
	void clearIMACache(IMAccount imAccount) throws GateException, GateLogicException;

	/**
	 * очистить весь кеш, связанный с кредитами
	 * @param loan
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void clearLoanCache(Loan loan)  throws GateException, GateLogicException;

	/**
	 * очистить весь кеш, связанный со счетами ДЕПО
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
	 * Очистить весь кэш, связанный с автоплатежами
	 * @param autoPayment автоплатеж
	 * @param cards список кард
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void clearAutoPaymentCache(AutoPayment autoPayment, Card ... cards) throws GateException, GateLogicException;

	/**
	 * Очистить кэш связанный с автоподпиской
	 * @param autoSubscription авптоподписка
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void clearAutoSubscriptionCache(AutoSubscription autoSubscription) throws GateException, GateLogicException;

	/**
	 * Очистить кэш связанный с программой лояльности
	 * @param loyaltyProgram программа лояльности
	 */
	void clearLoyaltyProgramCache(LoyaltyProgram loyaltyProgram) throws GateException, GateLogicException;

	/**
	 * Очистить кэш связанный со страховым продуктом
	 * @param insuranceApp страховой продукт
	 */
	void clearInsuranceAppCache(InsuranceApp insuranceApp) throws GateException, GateLogicException;

	/**
	 * Очистить кэш связанный с сберегательным сертификатом
	 * @param securityAccount сберегательный сертификат
	 */
	void clearSecurityAccountCache(SecurityAccount securityAccount) throws GateException, GateLogicException;

	/**
	 * Очищает кеш по интернет-заказу.
	 *
	 * @param orderUuid идентификатор заказа.
	 */
	void clearShopOrderCache(String orderUuid) throws GateException, GateLogicException;
}
