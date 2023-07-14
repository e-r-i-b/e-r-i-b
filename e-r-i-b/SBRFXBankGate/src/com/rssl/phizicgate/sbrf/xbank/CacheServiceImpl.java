package com.rssl.phizicgate.sbrf.xbank;

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
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.insurance.InsuranceApp;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.gate.loyalty.LoyaltyProgram;
import com.rssl.phizic.gate.security.SecurityAccount;

/**
 * @author gololobov
 * @ created 03.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class CacheServiceImpl extends AbstractService implements CacheService
{
	protected CacheServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public void clearClientProductsCache(Client client, Class... clazz) throws GateException, GateLogicException
	{

	}

	public void clearClientCache(Client client) throws GateException, GateLogicException
	{

	}

	public void clearAccountCache(Account account) throws GateException, GateLogicException
	{

	}

	public void clearCardCache(Card card) throws GateException, GateLogicException
	{

	}

	public void clearDepositCache(Deposit deposit) throws GateException, GateLogicException
	{

	}

	public void clearCurrencyRateCache(CurrencyRate rate, Office office) throws GateException, GateLogicException
	{

	}

	public void clearIMACache(IMAccount imAccount) throws GateException, GateLogicException
	{

	}

	public void clearLoanCache(Loan loan) throws GateException, GateLogicException
	{

	}

	public void clearDepoAccountCache(DepoAccount depoAccount) throws GateException, GateLogicException
	{

	}

	public void clearLongOfferCache(LongOffer longOffer) throws GateException, GateLogicException
	{

	}

	public void clearAutoPaymentCache(AutoPayment autoPayment, Card... cards) throws GateException, GateLogicException
	{

	}

	public void clearAutoSubscriptionCache(AutoSubscription autoSubscription) throws GateException, GateLogicException
	{

	}

	public void clearLoyaltyProgramCache(LoyaltyProgram loyaltyProgram) throws GateException, GateLogicException
	{

	}
	
	public void clearInsuranceAppCache(InsuranceApp insuranceApp) throws GateException, GateLogicException
	{

	}

	public void clearSecurityAccountCache(SecurityAccount securityAccount) throws GateException, GateLogicException
	{
	}

	public void clearShopOrderCache(String orderUuid) throws GateException, GateLogicException
	{
	}
}
