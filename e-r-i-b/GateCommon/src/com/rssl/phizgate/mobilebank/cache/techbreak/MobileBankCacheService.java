package com.rssl.phizgate.mobilebank.cache.techbreak;

import com.rssl.phizgate.mobilebank.MobileBankConfig;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Calendar;

/**
 * Сервис для работы с кеш-таблицами МБК
 * Доступ к таблицам скрыт кешом СП
 * @author Puzikov
 * @ created 16.09.14
 * @ $Author$
 * @ $Revision$
 */

@SuppressWarnings("JpaQueryApiInspection")
public class MobileBankCacheService
{
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);
	/**
	 * Сохранить кешируемую запись в БД
	 * Проносит изменение в БД только при отличии значения от кеша СП
	 * @param entry запись кеша
	 * @throws SystemException
	 */
	public void saveCacheEntry(final MbkCacheEntry entry)
	{
		//Если в ehcache уже есть совпадающее значение - не проносить в БД
		Cache cache = CacheProvider.getCache(entry.getAppServerCacheName());
		String key = entry.getCacheKey();
		Element element = cache.get(key);
		if (element != null && entry.equals(element.getObjectValue()))
			return;

		updateCache(entry);
		cache.put(new Element(key, entry));
	}

	/**
	 * кеш для mb_WWW_GetRegistrations
	 * @param key ключ кеша
	 * @return кешируемая сущность
	 * @throws SystemException
	 */
	public GetRegistrationsCacheEntry getRegistrationsCacheEntry(final String key) throws SystemException
	{
		return loadThroughCache(key, GetRegistrationsCacheEntry.CACHE_NAME, new HibernateAction<GetRegistrationsCacheEntry>()
		{
			public GetRegistrationsCacheEntry run(Session session) throws Exception
			{
				Query query = session.getNamedQuery("com.rssl.phizgate.mobilebank.cache.techbreak.GetRegistrationsCacheEntry.getByKey")
						.setParameter("key", key);
				return (GetRegistrationsCacheEntry) query.uniqueResult();
			}
		});
	}

	/**
	 * кеш для mb_WWW_GetRegistrations2
	 * @param key ключ кеша
	 * @return кешируемая сущность
	 * @throws SystemException
	 */
	public GetRegistrations2CacheEntry getRegistrations2CacheEntry(final String key) throws SystemException
	{
		return loadThroughCache(key, GetRegistrations2CacheEntry.CACHE_NAME, new HibernateAction<GetRegistrations2CacheEntry>()
		{
			public GetRegistrations2CacheEntry run(Session session) throws Exception
			{
				Query query = session.getNamedQuery("com.rssl.phizgate.mobilebank.cache.techbreak.GetRegistrations2CacheEntry.getByKey")
						.setParameter("key", key);
				return (GetRegistrations2CacheEntry) query.uniqueResult();
			}
		});
	}

	/**
	 * кеш для mb_WWW_GetRegistrations3
	 * @param key ключ кеша
	 * @return кешируемая сущность
	 * @throws SystemException
	 */
	public GetRegistrations3CacheEntry getRegistrations3CacheEntry(final String key) throws SystemException
	{
		return loadThroughCache(key, GetRegistrations3CacheEntry.CACHE_NAME, new HibernateAction<GetRegistrations3CacheEntry>()
		{
			public GetRegistrations3CacheEntry run(Session session) throws Exception
			{
				Query query = session.getNamedQuery("com.rssl.phizgate.mobilebank.cache.techbreak.GetRegistrations3CacheEntry.getByKey")
						.setParameter("key", key);
				return (GetRegistrations3CacheEntry) query.uniqueResult();
			}
		});
	}

	/**
	 * кеш для mb_WWW_GetRegistrations3
	 * @param key ключ кеша
	 * @return кешируемая сущность
	 * @throws SystemException
	 */
	public GetRegistrationsPackCacheEntry getRegistrationsPackCacheEntry(final String key) throws SystemException
	{
		return loadThroughCache(key, GetRegistrationsPackCacheEntry.CACHE_NAME, new HibernateAction<GetRegistrationsPackCacheEntry>()
		{
			public GetRegistrationsPackCacheEntry run(Session session) throws Exception
			{
				Query query = session.getNamedQuery("com.rssl.phizgate.mobilebank.cache.techbreak.GetRegistrationsPackCacheEntry.getByKey")
						.setParameter("key", key);
				return (GetRegistrationsPackCacheEntry) query.uniqueResult();
			}
		});
	}

	/**
	 * кеш для mb_WWW_GetRegistrations3
	 * @param key ключ кеша
	 * @return кешируемая сущность
	 * @throws SystemException
	 */
	public GetRegistrationsPack3CacheEntry getRegistrationsPack3CacheEntry(final String key) throws SystemException
	{
		return loadThroughCache(key, GetRegistrationsPack3CacheEntry.CACHE_NAME, new HibernateAction<GetRegistrationsPack3CacheEntry>()
		{
			public GetRegistrationsPack3CacheEntry run(Session session) throws Exception
			{
				Query query = session.getNamedQuery("com.rssl.phizgate.mobilebank.cache.techbreak.GetRegistrationsPack3CacheEntry.getByKey")
						.setParameter("key", key);
				return (GetRegistrationsPack3CacheEntry) query.uniqueResult();
			}
		});
	}

	/**
	 * кеш для mb_WWW_GetClientByCardNumber
	 * @param key ключ кеша
	 * @return кешируемая сущность
	 * @throws SystemException
	 */
	public GetClientByCardNumberCacheEntry getClientByCardNumberCacheEntry(final String key) throws SystemException
	{
		return loadThroughCache(key, GetClientByCardNumberCacheEntry.CACHE_NAME, new HibernateAction<GetClientByCardNumberCacheEntry>()
		{
			public GetClientByCardNumberCacheEntry run(Session session) throws Exception
			{
				Query query = session.getNamedQuery("com.rssl.phizgate.mobilebank.cache.techbreak.GetClientByCardNumberCacheEntry.getByKey")
						.setParameter("key", key);
				return (GetClientByCardNumberCacheEntry) query.uniqueResult();
			}
		});
	}

	/**
	 * кеш для mb_WWW_GetClientByLogin
	 * @param key ключ кеша
	 * @return кешируемая сущность
	 * @throws SystemException
	 */
	public GetClientByLoginCacheEntry getClientByLoginCacheEntry(final String key) throws SystemException
	{
		return loadThroughCache(key, GetClientByLoginCacheEntry.CACHE_NAME, new HibernateAction<GetClientByLoginCacheEntry>()
		{
			public GetClientByLoginCacheEntry run(Session session) throws Exception
			{
				Query query = session.getNamedQuery("com.rssl.phizgate.mobilebank.cache.techbreak.GetClientByLoginCacheEntry.getByKey")
						.setParameter("key", key);
				return (GetClientByLoginCacheEntry) query.uniqueResult();
			}
		});
	}

	/**
	 * кеш для ERMB_GetCardsByPhone
	 * @param key ключ кеша
	 * @return кешируемая сущность
	 * @throws SystemException
	 */
	public GetCardsByPhoneCacheEntry getCardsByPhoneCacheEntry(final String key) throws SystemException
	{
		return loadThroughCache(key, GetCardsByPhoneCacheEntry.CACHE_NAME, new HibernateAction<GetCardsByPhoneCacheEntry>()
		{
			public GetCardsByPhoneCacheEntry run(Session session) throws Exception
			{
				Query query = session.getNamedQuery("com.rssl.phizgate.mobilebank.cache.techbreak.GetCardsByPhoneCacheEntry.getByKey")
						.setParameter("key", key);
				return (GetCardsByPhoneCacheEntry) query.uniqueResult();
			}
		});
	}

	/**
	 * кеш для проверки IMSI
	 * @param key ключ кеша
	 * @return кешируемая сущность
	 * @throws SystemException
	 */
	public ImsiCheckResultCacheEntry getImsiCheckResultCacheEntry(final String key) throws SystemException
	{
		return loadThroughCache(key, ImsiCheckResultCacheEntry.CACHE_NAME, new HibernateAction<ImsiCheckResultCacheEntry>()
		{
			public ImsiCheckResultCacheEntry run(Session session) throws Exception
			{
				Query query = session.getNamedQuery("com.rssl.phizgate.mobilebank.cache.techbreak.ImsiCheckResultCacheEntry.getByKey")
						.setParameter("key", key);
				return (ImsiCheckResultCacheEntry) query.uniqueResult();
			}
		});
	}

	//Приоритет загрузки
	//1. Кеш СП
	//2. Кеш на отдельных таблицах
	private <T extends MbkCacheEntry> T loadThroughCache(String key, String cacheName, HibernateAction<T> action) throws SystemException
	{
		Cache cache = CacheProvider.getCache(cacheName);
		Element element = cache.get(key);
		if (element == null)
		{
			try
			{
				T result = validateEntry(HibernateExecutor.getInstance().execute(action));
				cache.put(new Element(key, result));
				return result;
			}
			catch (Exception e)
			{
				throw new SystemException(e);
			}
		}
		else
		{
			//noinspection unchecked
			return validateEntry((T) element.getObjectValue());
		}
	}

	//Возвращает null, если кеш протух
	private <T extends MbkCacheEntry> T validateEntry(T cacheEntry)
	{
		if (cacheEntry == null)
		{
			return null;
		}

		int spCacheLifetime = ConfigFactory.getConfig(MobileBankConfig.class).getSpCacheLifetime();
		Calendar liveDate = DateHelper.getPreviousHours(spCacheLifetime);
		if (cacheEntry.getRequestTime().before(liveDate))
		{
			return null;
		}

		return cacheEntry;
	}

	private void updateCache(final MbkCacheEntry entry)
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(entry.getClass().getName() + ".getByKey");
					query.setParameter("key", entry.getCacheKey());
					MbkCacheEntry expired = (MbkCacheEntry) query.uniqueResult();
					if (expired != null)
						session.delete(expired);
					session.save(entry);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			log.error(e);
		}
	}

}
