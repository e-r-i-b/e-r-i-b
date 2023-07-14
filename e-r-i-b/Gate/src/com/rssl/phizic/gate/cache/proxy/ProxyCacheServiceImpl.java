package com.rssl.phizic.gate.cache.proxy;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.CurrencyRateType;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.cache.CacheService;
import com.rssl.phizic.gate.cache.proxy.composers.ListCardCacheKeyComposer;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.einvoicing.ShopOrder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.insurance.InsuranceApp;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentService;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.gate.loyalty.LoyaltyProgram;
import com.rssl.phizic.gate.payments.autopayment.CreateAutoPayment;
import com.rssl.phizic.gate.security.SecurityAccount;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.ClassHelper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 02.05.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Чистить бизнес кеш интерфейса гейта.
 */
public class ProxyCacheServiceImpl extends AbstractService implements CacheService
{
	public static final String DELEGATE_KEY = CacheService.class.getName()+".delegate";
	private CacheService delegate;
	private static PropertyReader reader = ConfigFactory.getReaderByFileName("gate.properties");
	private static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CACHE);

	public ProxyCacheServiceImpl(GateFactory factory)
	{
		super(factory);
		try
		{
			String delegateClassName = reader.getProperty(DELEGATE_KEY);
			Class classManager = ClassHelper.loadClass(delegateClassName);
			delegate = (CacheService) classManager.getConstructor(GateFactory.class).newInstance(factory);

		}
		catch (Exception ex)
		{
			log.error("Ошибка при старте ProxyCacheServiceImpl", ex);
			throw new RuntimeException(ex);
		}
	}

	public void clearClientCache(Client client) throws GateException, GateLogicException
	{
		if(client!=null)
		{
			CacheKeyComposer callbackComposer = GateBusinessCacheConfig.getCleanableComposer(Client.class);
			Serializable callbackKey = callbackComposer.getClearCallbackKey(client, null);
			clearBusinessGateCache(callbackKey);
		}

//		delegate.clearClientCache(client); //там нечего чистить
	}

	public void clearClientProductsCache(Client client, Class... clazz) throws GateException, GateLogicException
	{
		if(client!=null)
		{
			Object [] params = new Object[] {client, clazz};
			CacheKeyComposer callbackComposer = GateBusinessCacheConfig.getCleanableClientProductComposer();
			Serializable callbackKey = callbackComposer.getClearCallbackKey(client, params);
			clearBusinessGateCache(callbackKey);
		}
	}

	public void clearAccountCache(Account account) throws GateException, GateLogicException
	{
		if(account!=null)
		{
			CacheKeyComposer callbackComposer = GateBusinessCacheConfig.getCleanableComposer(Account.class);
			Serializable callbackKey = callbackComposer.getClearCallbackKey(account, null);
			clearBusinessGateCache(callbackKey);
		}
		delegate.clearAccountCache(account);
	}

	public void clearCardCache(Card card) throws GateException, GateLogicException
	{
		if(card!=null)
		{
			CacheKeyComposer callbackComposer = GateBusinessCacheConfig.getCleanableComposer(Card.class);
			Serializable callbackKey = callbackComposer.getClearCallbackKey(card, null);
			clearBusinessGateCache(callbackKey);
		}
		delegate.clearCardCache(card);
	}

	public void clearDepositCache(Deposit deposit) throws GateException, GateLogicException
	{
		//todo
		delegate.clearDepositCache(deposit);
	}

	public void clearCurrencyRateCache(CurrencyRate rate, Office office) throws GateException, GateLogicException
	{
		if(rate!=null)
		{
			CacheKeyComposer callbackComposer = GateBusinessCacheConfig.getCleanableComposer(CurrencyRate.class);
			Currency from = rate.getFromCurrency();
			Currency to = rate.getToCurrency();
			CurrencyRateType type = rate.getType();
			Serializable callbackKey = callbackComposer.getClearCallbackKey(rate, new Object[]{from, to, type, office});
			clearBusinessGateCache(callbackKey);
		}
		delegate.clearCurrencyRateCache(rate, office);
	}

	public void clearIMACache(IMAccount imAccount) throws GateException, GateLogicException
	{
		if(imAccount != null)
		{
			CacheKeyComposer callbackComposer = GateBusinessCacheConfig.getCleanableComposer(IMAccount.class);
			Serializable callbackKey = callbackComposer.getClearCallbackKey(imAccount, null);
			clearBusinessGateCache(callbackKey);
		}
		delegate.clearIMACache(imAccount);
	}

	public void clearLoanCache(Loan loan) throws GateException, GateLogicException
	{
		if(loan != null)
		{
			CacheKeyComposer callbackComposer = GateBusinessCacheConfig.getCleanableComposer(Loan.class);
			Serializable callbackKey = callbackComposer.getClearCallbackKey(loan, null);
			clearBusinessGateCache(callbackKey);
		}
		delegate.clearLoanCache(loan);
	}

	public void clearDepoAccountCache(DepoAccount depoAccount) throws GateException, GateLogicException
	{
		if(depoAccount != null)
		{
			CacheKeyComposer composer = GateBusinessCacheConfig.getCleanableComposer(DepoAccount.class);
			Serializable callbackKey = composer.getClearCallbackKey(depoAccount, null);
			clearBusinessGateCache(callbackKey);
		}
		delegate.clearDepoAccountCache(depoAccount);
	}

	public void clearInsuranceAppCache(InsuranceApp insuranceApp) throws GateException, GateLogicException
	{
		if(insuranceApp != null)
		{
			CacheKeyComposer composer = GateBusinessCacheConfig.getCleanableComposer(InsuranceApp.class);
			Serializable callbackKey = composer.getClearCallbackKey(insuranceApp, null);
			clearBusinessGateCache(callbackKey);
		}
		delegate.clearInsuranceAppCache(insuranceApp);
	}

	public void clearSecurityAccountCache(SecurityAccount securityAccount) throws GateException, GateLogicException
	{
		if(securityAccount != null)
		{
			CacheKeyComposer composer = GateBusinessCacheConfig.getCleanableComposer(SecurityAccount.class);
			Serializable callbackKey = composer.getClearCallbackKey(securityAccount, null);
			clearBusinessGateCache(callbackKey);
		}
		delegate.clearSecurityAccountCache(securityAccount);
	}

	public void clearShopOrderCache(String orderUuid) throws GateException, GateLogicException
	{
		CacheKeyComposer composer = GateBusinessCacheConfig.getCleanableComposer(ShopOrder.class);
		Serializable callbackKey = composer.getClearCallbackKey(null, new Object[]{orderUuid});
		clearBusinessGateCache(callbackKey);
	}

	public void clearLongOfferCache(LongOffer longOffer) throws GateException, GateLogicException
	{
		if(longOffer != null)
		{
			CacheKeyComposer composer = GateBusinessCacheConfig.getCleanableComposer(LongOffer.class);
			Serializable callbackKey = composer.getClearCallbackKey(longOffer, null);
			clearBusinessGateCache(callbackKey);
		}
		delegate.clearLongOfferCache(longOffer);
	}

	public void clearAutoPaymentCache(AutoPayment autoPayment, Card ... cards) throws GateException, GateLogicException
	{
		if (cards != null && cards.length != 0)
		{
			CacheKeyComposer arrayComposer = GateBusinessCacheConfig.getCleanableComposer(ListCardCacheKeyComposer.class);
			CacheKeyComposer autoPaymentComposer = GateBusinessCacheConfig.getCleanableComposer(AutoPayment.class);
			Serializable callbackKey = autoPaymentComposer.getClearCallbackKey(autoPayment, null);
			Serializable parameterKey = arrayComposer.getClearCallbackKey(Arrays.asList(cards), null);
			clearBusinessGateCacheWithKeys(callbackKey, parameterKey);
			clearCacheForAutoPaymentLink(autoPayment, parameterKey);
		}
		else if (autoPayment != null)
		{
			CacheKeyComposer composer = GateBusinessCacheConfig.getCleanableComposer(AutoPayment.class);
			Serializable callbackKey = composer.getClearCallbackKey(autoPayment, null);
			clearBusinessGateCache(callbackKey);
		}
		delegate.clearAutoPaymentCache(autoPayment, cards);
	}

	public void clearAutoSubscriptionCache(AutoSubscription autoSubscription) throws GateException, GateLogicException
	{
		if(autoSubscription != null)
		{
			CacheKeyComposer composer = GateBusinessCacheConfig.getCleanableComposer(AutoSubscription.class);
			Serializable callbackKey = composer.getClearCallbackKey(autoSubscription, null);
			clearBusinessGateCache(callbackKey);
		}
		delegate.clearAutoSubscriptionCache(autoSubscription);
	}

	public void clearLoyaltyProgramCache(LoyaltyProgram loyaltyProgram) throws GateException, GateLogicException
	{
		if(loyaltyProgram != null)
		{
			CacheKeyComposer composer = GateBusinessCacheConfig.getCleanableComposer(LoyaltyProgram.class);
			Serializable callbackKey = composer.getClearCallbackKey(loyaltyProgram, null);
			clearBusinessGateCache(callbackKey);
		}
		delegate.clearLoyaltyProgramCache(loyaltyProgram);
	}

	/**
	 * Очищает весь КЭШ для автоплатежей, если нам пришел документ CreateAutoPayment.
	 *
	 * @param autoPayment автоплатеж.
	 * @param parameterKey параметры.
	 */
	private void clearCacheForAutoPaymentLink(AutoPayment autoPayment, Serializable parameterKey)
	{
		if (autoPayment.getType() != CreateAutoPayment.class)
			return;

		for (Method method : AutoPaymentService.class.getMethods())
			removeCache(CacheAnnotationHelper.getCachableMethodByMethod(method), parameterKey);
	}

	/**
	 * очишает кеш для конкретного вида входных параметров.
	 * @param callbackKey
	 * @param parameterKey
	 */
	private void clearBusinessGateCacheWithKeys(Serializable callbackKey, Serializable parameterKey)
	{
		if (callbackKey == null)
		{
			return;
		}
		Cache callbackCache = GateBusinessCacheSingleton.getInstance().getCache(GateBusinessCacheSingleton.LINKS_CACHE_NAME);
		Element elem = callbackCache.get(callbackKey);
		int numOfDeletedKeys = 0;       //количество удаленных ключей из кеша.
		int numOfKeys = 0;              //общее количество ключей в кеше.
		if (elem != null)
		{
			List<Pair<String, Serializable>> keys = (List<Pair<String, Serializable>>) elem.getObjectValue();
			numOfKeys = keys.size();
			Iterator iter = keys.iterator();
			while (iter.hasNext())
			{
				Pair<String, Serializable> key = (Pair<String, Serializable>) iter.next();
				if (key.getSecond().equals(parameterKey) || key.getSecond().equals(callbackKey))
				{
					log.trace("Удалено из кеша " + key.getFirst() + " по ключу " + key.getSecond());
					Cache cache = GateBusinessCacheSingleton.getInstance().getCache(key.getFirst());
					cache.remove(key.getSecond());
					iter.remove();
					numOfDeletedKeys++;
				}
			}
		}
		if (numOfDeletedKeys == numOfKeys && numOfKeys != 0) //Очишаешь колбек кеш, если все ключи удалены.
		{
			log.trace("Удалено все из колбек кеша для сущности с ключем " + callbackKey);
			callbackCache.remove(callbackKey);
		}
	}

	private void clearBusinessGateCache(Serializable callbackKey)
	{
		if(callbackKey!=null)
		{
			Cache callbackCache = GateBusinessCacheSingleton.getInstance().getCache(GateBusinessCacheSingleton.LINKS_CACHE_NAME);
			Element elem = callbackCache.get(callbackKey);
			if(elem!=null)
			{
				List<Pair<String,Serializable>> keys = (List<Pair<String,Serializable>> )elem.getObjectValue();
				for (Pair<String,Serializable> key : keys)
				{
					log.trace("Удалено  из кеша "+key.getFirst()+  " по ключу  "+key.getSecond());
					Cache cache = GateBusinessCacheSingleton.getInstance().getCache(key.getFirst());
					cache.remove(key.getSecond());
				}
			}
			log.trace("Удалено все из колбек кеша для сущности с ключем "+callbackKey);
			callbackCache.remove(callbackKey);
		}
	}

	/**
	 * Удалаяет КЭШ для метода с параметрами.
	 * @param method метод.
	 * @param parameterKey параметры метода.
	 */
	private void removeCache(Method method, Serializable parameterKey)
	{
		if (method == null)
			return;
		
		GateBusinessCacheSingleton businessCacheSingleton = GateBusinessCacheSingleton.getInstance();
		Cache cache = businessCacheSingleton.getCache(GateBusinessCacheConfig.getCacheNameByMethod(method));
		cache.remove(parameterKey);
	}
}
