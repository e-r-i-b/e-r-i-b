package com.rssl.auth.csa.front.security;

import com.rssl.phizic.config.CSAFrontConfig;
import com.rssl.phizic.config.ConfigFactory;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.auth.security.SecurityManager;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Ѕазовый ћенеджер дл€ проверки частоты запросов
 * @author niculichev
 * @ created 06.02.2013
 * @ $Author$
 * @ $Revision$
 */
public class SecurityManagerBase implements SecurityManager
{
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	/**
	 *  эш дл€ хранени€ доверенных ip-адресов.
	 */
	protected Cache lastVisitingTimeCache;
	/**
	 *  эш дл€ хранени€ недоверенных ip-адресов.
	 */
	protected Cache nonTrustedLastVisitingTimeCache;

	private String PHIZIC_APP_PREFIX = "phiz";

	/**
	 * —оздает менеджер ip адресов.
	 */
	protected SecurityManagerBase(String lastVisitingTimeCacheName, String nonTrustedVisitingCachName)
	{
		lastVisitingTimeCache = CacheProvider.getCache(lastVisitingTimeCacheName);
		nonTrustedLastVisitingTimeCache = CacheProvider.getCache(nonTrustedVisitingCachName);
	}

	/**
	 * ѕровер€ет на то, что пользователь ввел ответ не слишком быстро.
	 * ≈сли пользователь после открыти€ страницы дал ответ быстрее, чем через 10 секунд, то пользователь
	 * предположительно €вл€етс€ недоверенным, надо его провер€ть.
	 * ≈сли при проверке пользователь отвечает быстрее чем за 30 секунд, надо еще раз провер€ть, до тех пор
	 * пока не ответит позднее чем через 30 секунд.
	 *
	 * @param key ключ кэша.
	 */
	public final void processUserAction(String key)
	{
		lock.writeLock().lock();
		try
		{
			boolean hasLastVisitingIp = lastVisitingTimeCache.isKeyInCache(key);
			boolean hasNonTrustedIp = nonTrustedLastVisitingTimeCache.isKeyInCache(key);
			//если пользователь только что пришел, то помещаем его в таблицу последних визитов.

		   if (!hasLastVisitingIp && !hasNonTrustedIp)
			{
				addNewIp(key);
			}
			//если пользователь уже приходил и он еще доверенный
			else if (!hasNonTrustedIp)
			{
				checkIPForTrusted(key);
			}
			//пользователь уже не проверенный.
			else
			{
				checkIPBecomeTrusted(key);
			}
		}
		finally
		{
			lock.writeLock().unlock();
		}
	}

	/**
	 * ƒобавл€ет новый ip-адрес.
	 * @param ip ip-адрес.
	 * @return добавилось или нет.
	 */
	private void addNewIp(String ip)
	{
		lastVisitingTimeCache.put(new Element(ip, getCurrentTime()));
	}

	/**
	 * ѕровер€ет осталс€ ли ip-адрес доверенным.
	 * @param ip ip-адрес.
	 * @return осталс€ ли ip-адрес доверенным.
	 */
	private void checkIPForTrusted(String ip)
	{
		//проверим, что пользователь отвечал долго, т.е. он нормальный.
		Element elem = lastVisitingTimeCache.get(ip);
		//Ќас атакуют с одного ip. ѕокажем капчу.
		if (elem == null) {
			//пользователь слишком быстро ответил.
			nonTrustedLastVisitingTimeCache.put(new Element(ip, getCurrentTime()));
			return;
		}

		Long lastTime = (Long) elem.getValue();
		long firstTimeCheckValue = getCaptchaDelayForTrusted() * 1000;
		if (getCurrentTime() - lastTime > firstTimeCheckValue)
		{
			addNewIp(ip);
		}
		else
		{
			//пользователь слишком быстро ответил.
			nonTrustedLastVisitingTimeCache.put(new Element(ip, getCurrentTime()));
			lastVisitingTimeCache.remove(ip);
		}
	}

	/**
	 * ѕроверка на то, стал ли недоверенный ip-адрес доверенным.
	 * @param ip ip-адрес.
	 * @return доверенный или нет.
	 */
	private void checkIPBecomeTrusted(String ip)
	{
		//проверим, что пользователь отвечал быстро.
		Element elem = nonTrustedLastVisitingTimeCache.get(ip);
		if (elem == null)
		{
			nonTrustedLastVisitingTimeCache.put(new Element(ip, getCurrentTime()));
			return;
		}
		
		Long lastTime = (Long)elem .getValue();
		long nonTrustedIpCheckTime = getCaptchaDelayForUntrusted() * 1000;
		if (getCurrentTime() - lastTime <= nonTrustedIpCheckTime)
		{
			nonTrustedLastVisitingTimeCache.put(new Element(ip, getCurrentTime()));
		}

		//пользователь ответил за нормальное врем€.
		lastVisitingTimeCache.put(new Element(ip, getCurrentTime()));
		nonTrustedLastVisitingTimeCache.remove(ip);
	}

	/**
	 * ƒоверенный пользователь или нет.
	 *
	 * @param ip ip-адрес пользовател€.
	 * @return доверенный или нет.
	 */
	public final boolean userTrusted(String ip)
	{
		lock.readLock().lock();
		try
		{
			return !nonTrustedLastVisitingTimeCache.isKeyInCache(ip);
		}
		finally
		{
			lock.readLock().unlock();
		}
	}

	/**
	 * “екущее врем€.
	 *
	 * @return врем€ в милисекундах.
	 */
	protected long getCurrentTime()
	{
		return System.currentTimeMillis();
	}

	public void reset(String key)
	{
		lastVisitingTimeCache.remove(key);
		nonTrustedLastVisitingTimeCache.remove(key);
	}

	/**
	 * @return допустимый интервал (в секундах) попыток капчи дл€ доверенных пользователей
	 */
	protected long getCaptchaDelayForTrusted()
	{
		CSAFrontConfig config = ConfigFactory.getConfig(CSAFrontConfig.class);
		return config.getCommonCaptchaDelay();
	}

	/**
	 * @return допустимый интервал (в секундах) попыток капчи дл€ недоверенных пользователей
	 */
	protected long getCaptchaDelayForUntrusted()
	{
		CSAFrontConfig config = ConfigFactory.getConfig(CSAFrontConfig.class);
		return config.getCaptchaBlockRulingStoppingDelay();
	}
}