package com.rssl.phizic.gate.impl.cache;

import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.cache.CacheService;
import com.rssl.phizic.gate.cache.MessagesCacheManager;
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
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.security.SecurityAccount;

/**
 * @author Omeliyanchuk
 * @ created 25.03.2008
 * @ $Author$
 * @ $Revision$
 */

/*
—ервис дл€ работы с кешем из бизнеса, т.е. дл€ форсированной очистки
 */
public class CacheServiceImpl extends AbstractService implements CacheService
{
	public CacheServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public void clearClientCache(Client client) throws GateException, GateLogicException
	{
		//ничего не чистим
	}

    public void clearClientProductsCache(Client client, Class... clazz) throws GateException, GateLogicException
	{
		//ничего не чистим
	}

	public void clearAccountCache(Account account) throws GateException, GateLogicException
	{
		//≈сли сервис и менеджер сделать статичными, будет падать при создании гейта
		WebBankServiceFacade serviceFacade = getFactory().service(WebBankServiceFacade.class);
		MessagesCacheManager messagesManager = serviceFacade.getMessagesCacheManager();
		messagesManager.clear(account);
	}

	public void clearCardCache(Card card) throws GateException, GateLogicException
	{
		//≈сли сервис и менеджер сделать статичными, будет падать при создании гейта
		WebBankServiceFacade serviceFacade = getFactory().service(WebBankServiceFacade.class);
		MessagesCacheManager messagesManager = serviceFacade.getMessagesCacheManager();
		messagesManager.clear(card);
	}

	public void clearDepositCache(Deposit deposit) throws GateException, GateLogicException
	{
		//≈сли сервис и менеджер сделать статичными, будет падать при создании гейта
		WebBankServiceFacade serviceFacade = getFactory().service(WebBankServiceFacade.class);
		MessagesCacheManager messagesManager = serviceFacade.getMessagesCacheManager();
		messagesManager.clear(deposit);
	}

	public void clearCurrencyRateCache(CurrencyRate rate, Office office) throws GateException, GateLogicException
	{
		//≈сли сервис и менеджер сделать статичными, будет падать при создании гейта
		WebBankServiceFacade serviceFacade = getFactory().service(WebBankServiceFacade.class);
		MessagesCacheManager messagesManager = serviceFacade.getMessagesCacheManager();
		messagesManager.clear(rate);
	}

	public void clearIMACache(IMAccount imAccount) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = getFactory().service(WebBankServiceFacade.class);
		MessagesCacheManager messagesManager = serviceFacade.getMessagesCacheManager();
		messagesManager.clear(imAccount);
	}

	public void clearLoanCache(Loan loan) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = getFactory().service(WebBankServiceFacade.class);
		MessagesCacheManager messagesManager = serviceFacade.getMessagesCacheManager();
		messagesManager.clear(loan);
	}

	public void clearDepoAccountCache(DepoAccount depoAccount) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = getFactory().service(WebBankServiceFacade.class);
		MessagesCacheManager messagesManager = serviceFacade.getMessagesCacheManager();
		messagesManager.clear(depoAccount);
	}

	public void clearLongOfferCache(LongOffer longOffer) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = getFactory().service(WebBankServiceFacade.class);
		MessagesCacheManager messagesManager = serviceFacade.getMessagesCacheManager();
		messagesManager.clear(longOffer);
	}

	public void clearAutoPaymentCache(AutoPayment autoPayment, Card ... cards) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = getFactory().service(WebBankServiceFacade.class);
		MessagesCacheManager messagesManager = serviceFacade.getMessagesCacheManager();
		messagesManager.clear(autoPayment);
	}

	public void clearAutoSubscriptionCache(AutoSubscription autoSubscription) throws GateException, GateLogicException
	{
		// автоподписки не идут через пр€мую интеграцию
	}

	public void clearLoyaltyProgramCache(LoyaltyProgram loyaltyProgram) throws GateException, GateLogicException
	{
		//реализовать при необходимости
	}

	public void clearInsuranceAppCache(InsuranceApp insuranceApp) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = getFactory().service(WebBankServiceFacade.class);
		MessagesCacheManager messagesManager = serviceFacade.getMessagesCacheManager();
		messagesManager.clear(insuranceApp);
	}

	public void clearSecurityAccountCache(SecurityAccount securityAccount) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = getFactory().service(WebBankServiceFacade.class);
		MessagesCacheManager messagesManager = serviceFacade.getMessagesCacheManager();
		messagesManager.clear(securityAccount);
	}

	public void clearShopOrderCache(String orderUuid) throws GateException, GateLogicException
	{
	}
}
