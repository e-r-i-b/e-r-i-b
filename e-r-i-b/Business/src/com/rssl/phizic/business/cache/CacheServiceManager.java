package com.rssl.phizic.business.cache;

import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.cache.CacheService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.config.GateConnectionConfig;
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
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;
import com.rssl.phizicgate.manager.services.routable.RouteInfoService;

/**
 * @ author: filimonova
 * @ created: 27.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class CacheServiceManager extends AbstractService implements CacheService
{
	private CacheService esb;
	private CacheService routable;
	private CacheService businessDelegate;

	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private RouteInfoService routeInfoService = new RouteInfoService();

	public CacheServiceManager(GateFactory factory)
	{
		super(factory);

		try
		{
			esb = (CacheService) ConfigFactory.getConfig(GateConnectionConfig.class).getEsbClass().getConstructor(GateFactory.class).newInstance(factory);
			routable = (CacheService) ConfigFactory.getConfig(GateConnectionConfig.class).getRoutableClass().getConstructor(GateFactory.class).newInstance(factory);

			businessDelegate = (CacheService) getDelegate(CacheService.class.getName() + BUSINESS_DELEGATE_KEY);
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public void clearClientCache(Client client) throws GateException, GateLogicException
	{
		if (ESBHelper.isESBSupported(client.getOffice()))
			esb.clearClientCache(client);
		else
			routable.clearClientCache(client);
	}

    public void clearClientProductsCache(Client client, Class... clazz) throws GateException, GateLogicException
	{
		if (ESBHelper.isESBSupported(client.getOffice()))
			esb.clearClientProductsCache(client, clazz);
		else
			routable.clearClientProductsCache(client, clazz);
	}

	public void clearAccountCache(Account account) throws GateException, GateLogicException
	{
		try
		{
			if (ESBHelper.isESBSupported(account.getId()))
				esb.clearAccountCache(account);
		}
		catch(Exception e)
		{
			log.error("Ошибка при очистке кеша для счета № " + account.getNumber(), e);
		}
		//Чистить прямую инткграцию по счетам нужно всегда,
		// т.к. если шина не подключено, то все сообющения туда идут,
		// если подключена, то расширенная выписка все равно всегда туда идёт.
		try
		{
			routable.clearAccountCache(routeInfoService.appendRouteInfo(account));
		}
		catch(Exception e)
		{
			log.error("Ошибка при очистке кеша сообщений по прямой интеграции для счета № " + account.getNumber(), e);
		}
		try
		{
			businessDelegate.clearAccountCache(account);
		}
		catch(Exception e)
		{
			log.error("Ошибка при очистке кеша динамического справочника по счету №" + account.getNumber(), e);
		}
	}

	public void clearCardCache(Card card) throws GateException, GateLogicException
	{
		//карта никогда не ходит в прямую интеграцию
		try
		{
			esb.clearCardCache(card);
		}
		catch(Exception e)
		{
			log.error("Ошибка при очистке кеша по карте №" + card.getNumber(), e);
		}
		try
		{
			businessDelegate.clearCardCache(card);
		}
		catch(Exception e)
		{
			log.error("Ошибка при очистке кеша динамического справочника по карте №" + card.getNumber(), e);
		}
	}

	public void clearDepositCache(Deposit deposit) throws GateException, GateLogicException
	{
		//такого понятия на шине нет		
		routable.clearDepositCache(deposit);
	}

	public void clearCurrencyRateCache(CurrencyRate rate, Office office) throws GateException, GateLogicException
	{
		try
		{
			if (ESBHelper.isESBSupported(office))
				esb.clearCurrencyRateCache(rate, office);
			else
				routable.clearCurrencyRateCache(rate, office);
		}
		catch (Exception e)
		{
			log.error("Ошибка при очистке кеша ресурса курса валют", e);
		}
		try
		{
			businessDelegate.clearCurrencyRateCache(rate, office);
		}
		catch(Exception e)
		{
			log.error("Ошибка при очистке кеша динамического справочника курса валют", e);
		}
	}

	public void clearIMACache(IMAccount imAccount) throws GateException, GateLogicException
	{
		//ОМС никогда не ходит в прямую интеграцию
		try
		{
			esb.clearIMACache(imAccount);
		}
		catch(Exception e)
		{
			log.error("Ошибка при очистке кеша ОМС №"+ imAccount.getNumber(), e);
		}
		try
		{
			businessDelegate.clearIMACache(imAccount);
		}
		catch(Exception e)
		{
			log.error("Ошибка при очистке кеша динамического справочника ОМС №"+ imAccount.getNumber(), e);
		}
	}

	public void clearLoanCache(Loan loan) throws GateException, GateLogicException
	{
		businessDelegate.clearLoanCache(loan);
	}

	public void clearDepoAccountCache(DepoAccount depoAccount) throws GateException, GateLogicException
	{
		businessDelegate.clearDepoAccountCache(depoAccount);
	}

	public void clearLongOfferCache(LongOffer longOffer) throws GateException, GateLogicException
	{
		if (ESBHelper.isESBSupported(longOffer.getOffice()))
			esb.clearLongOfferCache(longOffer);
		else
			routable.clearLongOfferCache(longOffer);
	}

	public void clearAutoPaymentCache(AutoPayment autoPayment, Card ... cards) throws GateException, GateLogicException
	{
		routable.clearAutoPaymentCache(autoPayment, cards);
	}


	public void clearAutoSubscriptionCache(AutoSubscription autoSubscription) throws GateException, GateLogicException
	{
		// автоподписки не ходят по прямой интеграции
		esb.clearAutoSubscriptionCache(autoSubscription);
	}

	public void clearLoyaltyProgramCache(LoyaltyProgram loyaltyProgram) throws GateException, GateLogicException
	{
		routable.clearLoyaltyProgramCache(loyaltyProgram);
	}

	public void clearInsuranceAppCache(InsuranceApp insuranceApp) throws GateException, GateLogicException
	{
		businessDelegate.clearInsuranceAppCache(insuranceApp);
	}

	public void clearSecurityAccountCache(SecurityAccount securityAccount) throws GateException, GateLogicException
	{
		businessDelegate.clearSecurityAccountCache(securityAccount);
	}

	public void clearShopOrderCache(String orderUuid) throws GateException, GateLogicException
	{
		businessDelegate.clearShopOrderCache(orderUuid);
	}
}
