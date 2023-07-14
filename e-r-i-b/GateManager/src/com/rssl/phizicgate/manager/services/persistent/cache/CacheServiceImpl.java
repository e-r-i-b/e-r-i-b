package com.rssl.phizicgate.manager.services.persistent.cache;

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
import com.rssl.phizicgate.manager.services.persistent.PersistentServiceBase;

/**
 * @author osminin
 * @ created 01.09.2009
 * @ $Author$
 * @ $Revision$
 */

public class CacheServiceImpl extends PersistentServiceBase<CacheService> implements CacheService
{
	public CacheServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public void clearClientCache(Client client) throws GateException, GateLogicException
	{
		delegate.clearClientCache(removeRouteInfo(client));
	}

	public void clearClientProductsCache(Client client, Class... clazz) throws GateException, GateLogicException
	{
		delegate.clearClientProductsCache(removeRouteInfo(client), clazz);
	}

	public void clearAccountCache(Account account) throws GateException, GateLogicException
	{
		delegate.clearAccountCache(removeRouteInfo(account));
	}

	public void clearCardCache(Card card) throws GateException, GateLogicException
	{
		delegate.clearCardCache(removeRouteInfo(card));
	}

	public void clearDepositCache(Deposit deposit) throws GateException, GateLogicException
	{
		delegate.clearDepositCache(removeRouteInfo(deposit));
	}

	public void clearCurrencyRateCache(CurrencyRate rate, Office office) throws GateException, GateLogicException
	{
		delegate.clearCurrencyRateCache(rate, removeRouteInfo(office));
	}

	public void clearIMACache(IMAccount imAccount) throws GateException, GateLogicException
	{
		delegate.clearIMACache(imAccount);
	}

	public void clearLoanCache(Loan loan) throws GateException, GateLogicException
	{
		delegate.clearLoanCache(loan);
	}

	public void clearDepoAccountCache(DepoAccount depoAccount) throws GateException, GateLogicException
	{
		delegate.clearDepoAccountCache(depoAccount);
	}

	public void clearLongOfferCache(LongOffer longOffer) throws GateException, GateLogicException
	{
		delegate.clearLongOfferCache(longOffer);
	}

	public void clearAutoPaymentCache(AutoPayment autoPayment, Card ... cards) throws GateException, GateLogicException
	{
		delegate.clearAutoPaymentCache(removeRouteInfo(autoPayment), removeRouteInfo(cards));
	}

	public void clearAutoSubscriptionCache(AutoSubscription autoSubscription) throws GateException, GateLogicException
	{
		delegate.clearAutoSubscriptionCache(autoSubscription);
	}

	public void clearLoyaltyProgramCache(LoyaltyProgram loyaltyProgram) throws GateException, GateLogicException
	{
		delegate.clearLoyaltyProgramCache(removeRouteInfo(loyaltyProgram));
	}

	public void clearInsuranceAppCache(InsuranceApp insuranceApp) throws GateException, GateLogicException
	{
		delegate.clearInsuranceAppCache(insuranceApp);
	}

	public void clearSecurityAccountCache(SecurityAccount securityAccount) throws GateException, GateLogicException
	{
		delegate.clearSecurityAccountCache(securityAccount);
	}

	public void clearShopOrderCache(String orderUuid) throws GateException, GateLogicException
	{
		delegate.clearShopOrderCache(orderUuid);
	}
}
