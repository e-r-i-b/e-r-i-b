package com.rssl.auth.csa.front.security;

import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.config.CSAFrontConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.DateHelper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author tisov
 * @ created 16.03.15
 * @ $Author$
 * @ $Revision$
 *  Менеджер проверки ip-адреса для гостевого функционала
 */
public class GuestEntryIPSecurityManager extends IPSecurityManager
{
	private static volatile GuestEntryIPSecurityManager this0;
	private static final String IP_TO_PHONES_CACHE_NAME = IPSecurityManager.class.getName() + ".ipToPhones";
	private Cache ipToPhones;
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	/**
	 * Создает менеджер ip адресов.
	 */
	private GuestEntryIPSecurityManager()
	{
		super();
		ipToPhones = CacheProvider.getCache(IP_TO_PHONES_CACHE_NAME);
	}

	/**
	 * @return экземпляр менеджера.
	 */
	public static GuestEntryIPSecurityManager getIt()
	{
		if (this0 != null)
			return this0;

		synchronized (GuestEntryIPSecurityManager.class)
		{
			if (this0 != null)
				return this0;

			this0 = new GuestEntryIPSecurityManager();
			return this0;
		}
	}

	protected long getCaptchaDelayForTrusted()
	{
		CSAFrontConfig config = ConfigFactory.getConfig(CSAFrontConfig.class);
		return config.getTrustedGuestCaptchaDelay();
	}

	protected long getCaptchaDelayForUntrusted()
	{
		CSAFrontConfig config = ConfigFactory.getConfig(CSAFrontConfig.class);
		return config.getUntrustedGuestCaptchaDelay();
	}

	/**
	 * @param ip ip-адрес.
	 * @return необходимо ли отображать каптчу по привышении количества телефонов с одного ip.
	 */
	public boolean needShowCaptchaForOvercountPhones(String ip)
	{
		lock.readLock().lock();
		try
		{
			SecurityManagerData data = getDataByKey(ip);
			if (data == null)
				return false;

			CSAFrontConfig config = ConfigFactory.getConfig(CSAFrontConfig.class);
			if (data.getPhones().size() >= config.getGuestEntryPhonesLimit())
				return true;

			return false;
		}
		finally
		{
			lock.readLock().unlock();
		}
	}

	/**
	 * Получить данные из кэша
	 * @param ip - ключ записи
	 * @return данные из кеша для номеров телефонов.
	 */
	private SecurityManagerData getDataByKey(String ip)
	{
		if (!ConfigFactory.getConfig(CSAFrontConfig.class).isGuestCapthcaActive())
			return null;

		Element phoneCacheElem = ipToPhones.get(ip);
		if (phoneCacheElem == null)
		{
			return null;
		}

		SecurityManagerData data = (SecurityManagerData) phoneCacheElem.getValue();

		CSAFrontConfig config = ConfigFactory.getConfig(CSAFrontConfig.class);
		long phonesLimitMillisecondsCooldown = config.getGuestEntryPhonesLimitCooldown() * DateHelper.MILLISECONDS_IN_MINUTE;
		//Время последнего обращения к системе превысило время блокировки.
		if (System.currentTimeMillis() - data.getLastVisitedTime() > phonesLimitMillisecondsCooldown)
		{
			ipToPhones.remove(ip);
			return null;
		}

		return data;
	}

	/**
	 * Добавление телефона к списку телефонов, отслежеиваемых для отображения каптчи гостю.
	 *
	 * @param ip ip-адрес клиента.
	 * @param phone номер телефона.
	 */
	public void addPhone(String ip, String phone)
	{
		if (!ConfigFactory.getConfig(CSAFrontConfig.class).isGuestCapthcaActive())
			return;

		lock.writeLock().lock();
		try
		{
			SecurityManagerData data = getDataByKey(ip);
			if (data == null)
				ipToPhones.put(new Element(ip, new SecurityManagerData(getCurrentTime(), phone)));
			else
			{
				Set<String> phones = data.getPhones();
				if (data.getPhones().size() < ConfigFactory.getConfig(CSAFrontConfig.class).getGuestEntryPhonesLimit())
				{
					phones.add(phone);
				}
				ipToPhones.put(new Element(ip, new SecurityManagerData(getCurrentTime(), phones)));
			}
		}
		finally
		{
			lock.writeLock().unlock();
		}
	}
}
