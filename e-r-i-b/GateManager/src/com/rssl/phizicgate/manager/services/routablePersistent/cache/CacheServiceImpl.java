package com.rssl.phizicgate.manager.services.routablePersistent.cache;

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
import com.rssl.phizicgate.manager.services.objects.*;
import com.rssl.phizicgate.manager.services.routablePersistent.RoutablePersistentServiceBase;

/**
 * @author bogdanov
 * @ created 29.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class CacheServiceImpl extends RoutablePersistentServiceBase<CacheService> implements CacheService
{
	public CacheServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	protected CacheService endService(String routeInfo) throws GateLogicException, GateException
	{
		return getDelegateFactory(routeInfo).service(CacheService.class);
	}

	public void clearClientProductsCache(Client client, Class... clazz) throws GateException, GateLogicException
	{
		ClientWithoutRouteInfo clientInner = removeRouteInfo(client);
		endService(clientInner.getRouteInfo()).clearClientProductsCache(clientInner, clazz);
	}

	public void clearClientCache(Client client) throws GateException, GateLogicException
	{
		ClientWithoutRouteInfo clientInner = removeRouteInfo(client);
		endService(clientInner.getRouteInfo()).clearClientCache(clientInner);
	}

	public void clearAccountCache(Account account) throws GateException, GateLogicException
	{
		AccountWithoutRouteInfo accountInner = removeRouteInfo(account);
		endService(accountInner.getRouteInfo()).clearAccountCache(accountInner);
	}

	public void clearCardCache(Card card) throws GateException, GateLogicException
	{
		CardWithoutRouteInfo cardInner = removeRouteInfo(card);
		endService(cardInner.getRouteInfo()).clearCardCache(cardInner);
	}

	public void clearDepositCache(Deposit deposit) throws GateException, GateLogicException
	{
		DepositWithoutRouteInfo depositInner = removeRouteInfo(deposit);
		endService(depositInner.getRouteInfo()).clearDepositCache(depositInner);
	}

	public void clearCurrencyRateCache(CurrencyRate rate, Office office) throws GateException, GateLogicException
	{
		OfficeWithoutRouteInfo officeInner = removeRouteInfo(office);
		endService(officeInner.getRouteInfo()).clearCurrencyRateCache(rate, officeInner);
	}

	public void clearIMACache(IMAccount imAccount) throws GateException, GateLogicException
	{
		RouteInfoReturner routeInfo = removeRouteInfoString(imAccount.getId());
		endService(routeInfo.getRouteInfo()).clearIMACache(imAccount);
	}

	public void clearLoanCache(Loan loan) throws GateException, GateLogicException
	{
		LoanWithoutRouteInfo loanInner = removeRouteInfo(loan);
		endService(loanInner.getRouteInfo()).clearLoanCache(loan);
	}

	public void clearDepoAccountCache(DepoAccount depoAccount) throws GateException, GateLogicException
	{
		RouteInfoReturner routeInfo = removeRouteInfoString(depoAccount.getId());
		endService(routeInfo.getRouteInfo()).clearDepoAccountCache(depoAccount);
	}

	public void clearLongOfferCache(LongOffer longOffer) throws GateException, GateLogicException
	{
		RouteInfoReturner routeInfo = removeRouteInfoString(longOffer.getExternalId());
		endService(routeInfo.getRouteInfo()).clearLongOfferCache(longOffer);
	}

	public void clearAutoPaymentCache(AutoPayment autoPayment, Card... cards) throws GateException, GateLogicException
	{
		AutoPaymentWithoutRouteInfo autoPaymentInner = removeRouteInfo(autoPayment);
		endService(autoPaymentInner.getRouteInfo()).clearAutoPaymentCache(autoPaymentInner, removeRouteInfo(cards));
	}

	public void clearAutoSubscriptionCache(AutoSubscription autoSubscription) throws GateException, GateLogicException
	{
		RouteInfoReturner routeInfo = removeRouteInfoString(autoSubscription.getExternalId());
		endService(routeInfo.getRouteInfo()).clearAutoSubscriptionCache(autoSubscription);
	}

	public void clearLoyaltyProgramCache(LoyaltyProgram loyaltyProgram) throws GateException, GateLogicException
	{
		LoyaltyProgramWithoutRouteInfo loyatlyInner = removeRouteInfo(loyaltyProgram);
		endService(loyatlyInner.getRouteInfo()).clearLoyaltyProgramCache(loyatlyInner);
	}

	public void clearInsuranceAppCache(InsuranceApp insuranceApp) throws GateException, GateLogicException
	{
		RouteInfoReturner routeInfo = removeRouteInfoString(insuranceApp.getId());
		endService(routeInfo.getRouteInfo()).clearInsuranceAppCache(insuranceApp);
	}

	public void clearSecurityAccountCache(SecurityAccount securityAccount) throws GateException, GateLogicException
	{
		RouteInfoReturner routeInfo = removeRouteInfoString(securityAccount.getId());
		endService(routeInfo.getRouteInfo()).clearSecurityAccountCache(securityAccount);
	}

	public void clearShopOrderCache(String orderUuid) throws GateException, GateLogicException
	{
	}
}
