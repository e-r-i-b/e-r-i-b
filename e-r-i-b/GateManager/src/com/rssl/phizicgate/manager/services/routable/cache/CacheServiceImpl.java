package com.rssl.phizicgate.manager.services.routable.cache;

import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.cache.CacheService;
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
import com.rssl.phizicgate.manager.services.routable.RoutableServiceBase;

/**
 * @author hudyakov
 * @ created 09.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class CacheServiceImpl extends RoutableServiceBase implements CacheService
{
	public CacheServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public void clearClientCache(Client client) throws GateException, GateLogicException
	{
		CacheService delegate = getDelegateFactory(client).service(CacheService.class);
		delegate.clearClientCache(client);
	}

	public void clearClientProductsCache(Client client, Class... clazz) throws GateException, GateLogicException
	{
		CacheService delegate = getDelegateFactory(client).service(CacheService.class);
		delegate.clearClientProductsCache(client, clazz);
	}

	public void clearAccountCache(Account account) throws GateException, GateLogicException
	{
		CacheService delegate = getDelegateFactory(account).service(CacheService.class);
		delegate.clearAccountCache(account);
	}

	public void clearCardCache(Card card) throws GateException, GateLogicException
	{
		CacheService delegate = getDelegateFactory(card).service(CacheService.class);
		delegate.clearCardCache(card);
	}

	public void clearDepositCache(Deposit deposit) throws GateException, GateLogicException
	{
		CacheService delegate = getDelegateFactory(deposit).service(CacheService.class);
		delegate.clearDepositCache(deposit);
	}

	public void clearCurrencyRateCache(CurrencyRate rate, Office office) throws GateException, GateLogicException
	{
		CacheService delegate = getDelegateFactory(office).service(CacheService.class);
		delegate.clearCurrencyRateCache(rate, office);
	}

	public void clearIMACache(IMAccount imAccount) throws GateException, GateLogicException
	{
		CacheService delegate = getDelegateFactory(imAccount).service(CacheService.class);
		delegate.clearIMACache(imAccount);
	}

	public void clearLoanCache(Loan loan) throws GateException, GateLogicException
	{
		CacheService delegate = getDelegateFactory(loan).service(CacheService.class);
		delegate.clearLoanCache(loan);
	}

	public void clearDepoAccountCache(DepoAccount depoAccount) throws GateException, GateLogicException
	{
		CacheService delegate = getDelegateFactory(depoAccount).service(CacheService.class);
		delegate.clearDepoAccountCache(depoAccount);
	}

	public void clearLongOfferCache(LongOffer longOffer) throws GateException, GateLogicException
	{
		CacheService delegate = getDelegateFactory(longOffer).service(CacheService.class);
		delegate.clearLongOfferCache(longOffer);
	}

	public void clearAutoPaymentCache(AutoPayment autoPayment, Card ... cards) throws GateException, GateLogicException
	{
		CacheService delegate = getDelegateFactory(autoPayment).service(CacheService.class);
		delegate.clearAutoPaymentCache(autoPayment, cards);
	}

	public void clearAutoSubscriptionCache(AutoSubscription autoSubscription) throws GateException, GateLogicException
	{
		// автоподписки идут через шину
	}

	public void clearLoyaltyProgramCache(LoyaltyProgram loyaltyProgram) throws GateException, GateLogicException
	{
		CacheService delegate = getDelegateFactory(loyaltyProgram).service(CacheService.class);
		delegate.clearLoyaltyProgramCache(loyaltyProgram);
	}

	public void clearInsuranceAppCache(InsuranceApp insuranceApp) throws GateException, GateLogicException
	{
		CacheService delegate = getDelegateFactory(insuranceApp).service(CacheService.class);
		delegate.clearInsuranceAppCache(insuranceApp);
	}

	public void clearSecurityAccountCache(SecurityAccount securityAccount) throws GateException, GateLogicException
	{
		CacheService delegate = getDelegateFactory(securityAccount).service(CacheService.class);
		delegate.clearSecurityAccountCache(securityAccount);
	}

	public void clearShopOrderCache(String orderUuid) throws GateException, GateLogicException
	{
	}
}
