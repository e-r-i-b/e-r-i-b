package com.rssl.phizic.business.cache;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.rates.CurrencyRateServiceImpl;
import com.rssl.phizic.business.rates.Rate;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.business.xslt.lists.cache.XmlEntityListCacheSingleton;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.CurrencyRateType;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.insurance.InsuranceApp;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.cache.CacheService;
import com.rssl.phizic.gate.cache.proxy.CacheKeyComposer;
import com.rssl.phizic.gate.cache.proxy.GateBusinessCacheConfig;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.gate.loyalty.LoyaltyProgram;
import com.rssl.phizic.gate.security.SecurityAccount;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;
import net.sf.ehcache.Cache;

import java.io.Serializable;

/**
 * Сервис очистки кеша в бизнесе
 * @author gladishev
 * @ created 17.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class BusinessCacheServiceImpl extends AbstractService implements CacheService
{
	public static final String WRAPPER_RATE_CACHE = "CurrencyRateServiceWrapper.getRate";

	private static final DepartmentService departmentService = new DepartmentService();

	public BusinessCacheServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public void clearClientCache(Client client) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

    public void clearClientProductsCache(Client client, Class... clazz) throws GateException, GateLogicException
	{
	  throw new UnsupportedOperationException();
	}

	public void clearAccountCache(Account account) throws GateException, GateLogicException
	{
		XmlEntityListCacheSingleton.getInstance().clearCache(account, Account.class);
	}

	public void clearCardCache(Card card) throws GateException, GateLogicException
	{
		XmlEntityListCacheSingleton.getInstance().clearCache(card, Card.class);
	}

	public void clearCurrencyRateCache(CurrencyRate rate, Office office) throws GateException, GateLogicException
	{
		XmlEntityListCacheSingleton.getInstance().clearCache(rate, CurrencyRate.class);
		Cache cache = null;
		Department cachedDepartment = null;
		try
		{
			if (ESBHelper.isESBSupported(office))
			{
				cache = CacheProvider.getCache(CurrencyRateServiceImpl.BUSINESS_RATE_CACHE);
				cachedDepartment = departmentService.getTBByOffice(office);
			}
			else
			{
				cache = CacheProvider.getCache(WRAPPER_RATE_CACHE);
				if (DepartmentService.isTB(office))
					throw new GateException("Офис " + office.toString() + " является тербанком, не подключенным к шине");
				cachedDepartment = departmentService.getOSBByOffice(office);
			}

			CacheKeyComposer composer = GateBusinessCacheConfig.getCleanableComposer(CurrencyRate.class);

			clearCurrencyRateCache(cache, composer, cachedDepartment, rate.getFromCurrency(), rate.getToCurrency(),
					rate.getType(), rate.getTarifPlanCodeType());
			clearCurrencyRateCache(cache, composer, cachedDepartment, rate.getToCurrency(), rate.getFromCurrency(),
					Rate.inverseCurrencyRateType(rate.getType()), rate.getTarifPlanCodeType());

			Currency nationalCurrency = MoneyUtil.getNationalCurrency();
			// очищаем кэш курса покупки/продажи банком у клиента для валюты списания, чтобы в запросе уходили актуальные курсы
			clearCurrencyRateCache(cache, composer, cachedDepartment, rate.getFromCurrency(), nationalCurrency,
					CurrencyRateType.SALE_REMOTE, rate.getTarifPlanCodeType());
			clearCurrencyRateCache(cache, composer, cachedDepartment, rate.getFromCurrency(), nationalCurrency,
					CurrencyRateType.BUY_REMOTE, rate.getTarifPlanCodeType());

			// очищаем кэш курса покупки/продажи банком у клиента для валюты зачисления, чтобы в запросе уходили актуальные курсы
			clearCurrencyRateCache(cache, composer, cachedDepartment, rate.getToCurrency(), nationalCurrency,
					CurrencyRateType.SALE_REMOTE, rate.getTarifPlanCodeType());
			clearCurrencyRateCache(cache, composer, cachedDepartment, rate.getToCurrency(), nationalCurrency,
					CurrencyRateType.BUY_REMOTE, rate.getTarifPlanCodeType());
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	private void clearCurrencyRateCache(Cache cache, CacheKeyComposer composer, Office office, Currency fromCurrency,
	                                    Currency toCurrency, CurrencyRateType type, String tarifPlanCodeType)
	{
		Serializable cacheKey = composer.getKey(new Object[]{fromCurrency, toCurrency, type, office, tarifPlanCodeType}, null);
		if (cacheKey != null)
			cache.remove(cacheKey);
	}

	public void clearIMACache(IMAccount imAccount) throws GateException, GateLogicException
	{
		XmlEntityListCacheSingleton.getInstance().clearCache(imAccount, IMAccount.class);
	}

	public void clearLoanCache(Loan loan) throws GateException, GateLogicException
	{
		if (PersonContext.isAvailable() && !loan.getIsAnnuity())
			PersonContext.getPersonDataProvider().getPersonData().setHasOpenedDifLoan(null);
		XmlEntityListCacheSingleton.getInstance().clearCache(loan, Loan.class);
	}

	public void clearDepoAccountCache(DepoAccount depoAccount) throws GateException, GateLogicException
	{
		XmlEntityListCacheSingleton.getInstance().clearCache(depoAccount, DepoAccount.class);
	}

	public void clearLongOfferCache(LongOffer longOffer) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	public void clearAutoPaymentCache(AutoPayment autoPayment, Card ... cards) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	public void clearAutoSubscriptionCache(AutoSubscription autoSubscription) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	public void clearLoyaltyProgramCache(LoyaltyProgram loyaltyProgram) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	public void clearDepositCache(Deposit deposit) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	public void clearInsuranceAppCache(InsuranceApp insuranceApp) throws GateException, GateLogicException
	{
		XmlEntityListCacheSingleton.getInstance().clearCache(insuranceApp, InsuranceApp.class);
	}

	public void clearSecurityAccountCache(SecurityAccount securityAccount) throws GateException, GateLogicException
	{
		XmlEntityListCacheSingleton.getInstance().clearCache(securityAccount, SecurityAccount.class);
	}

	public void clearShopOrderCache(String orderUuid) throws GateException, GateLogicException
	{
	}
}
