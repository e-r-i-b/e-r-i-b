package com.rssl.phizicgate.esberibgate.cache;

import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.loyalty.LoyaltyProgram;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizicgate.esberibgate.ima.IMAccountRequestHelper;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import net.sf.ehcache.Cache;

/**
 * @author Pankin
 * @ created 22.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class CacheServiceImpl extends com.rssl.phizic.gate.impl.cache.CacheServiceImpl
{
	public CacheServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public void clearCardCache(Card card) throws GateException, GateLogicException
	{
		Cache cardDetailCache = CacheProvider.getCache(CacheKeyConstants.CARD_DETAILS_CACHE);
		cardDetailCache.remove(ESBCacheKeyGenerator.getProductDetailsKey(Card.class, card.getId()));
	}

	public void clearAccountCache(Account account) throws GateException, GateLogicException
	{
		Cache accountDetailCache = CacheProvider.getCache(CacheKeyConstants.ACCOUNT_DETAILS_CACHE);
		accountDetailCache.remove(ESBCacheKeyGenerator.getCardOrAccountDetailsKey(account.getId()));
	}

	// Очистка кеша сообщений шины
	public void clearIMACache(IMAccount imAccount) throws GateException, GateLogicException
	{
		IMAccountRequestHelper requestHelper = new IMAccountRequestHelper(getFactory());

		Cache imaClientCache = CacheProvider.getCache(CacheKeyConstants.IMA_CLIENT_CACHE);
		Client client = requestHelper.getEntityOwner(EntityIdHelper.getCommonCompositeId(imAccount.getId()));
		for (ClientDocument document : client.getDocuments())
		{
			imaClientCache.remove(ESBCacheKeyGenerator.getIMAClientDocumentKey(client, document));
		}

		Cache abstractCache = CacheProvider.getCache(CacheKeyConstants.IMA_ABSTRACT_CACHE);
		for (Object key : abstractCache.getKeys())
		{
			if (key instanceof String && ((String) key).startsWith(imAccount.getNumber()))
			{
				abstractCache.remove(key);
			}
		}

		CacheProvider.getCache(CacheKeyConstants.IMA_SHORT_ABSTRACT_CACHE).remove(imAccount.getId());
		CacheProvider.getCache(CacheKeyConstants.IMA_DETAILS_CACHE).remove(imAccount.getId());
	}

	public void clearAutoSubscriptionCache(AutoSubscription autoSubscription) throws GateException, GateLogicException
	{
		// отдельного кэша для автоподписок нет
	}

	public void clearLoyaltyProgramCache(LoyaltyProgram loyaltyProgram) throws GateException, GateLogicException
	{
		// программа лояльности не идет через шину
	}
}
