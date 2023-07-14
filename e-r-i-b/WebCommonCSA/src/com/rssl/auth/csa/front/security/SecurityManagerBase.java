package com.rssl.auth.csa.front.security;

import com.rssl.phizic.config.CSAFrontConfig;
import com.rssl.phizic.config.ConfigFactory;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.auth.security.SecurityManager;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ������� �������� ��� �������� ������� ��������
 * @author niculichev
 * @ created 06.02.2013
 * @ $Author$
 * @ $Revision$
 */
public class SecurityManagerBase implements SecurityManager
{
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	/**
	 * ��� ��� �������� ���������� ip-�������.
	 */
	protected Cache lastVisitingTimeCache;
	/**
	 * ��� ��� �������� ������������ ip-�������.
	 */
	protected Cache nonTrustedLastVisitingTimeCache;

	private String PHIZIC_APP_PREFIX = "phiz";

	/**
	 * ������� �������� ip �������.
	 */
	protected SecurityManagerBase(String lastVisitingTimeCacheName, String nonTrustedVisitingCachName)
	{
		lastVisitingTimeCache = CacheProvider.getCache(lastVisitingTimeCacheName);
		nonTrustedLastVisitingTimeCache = CacheProvider.getCache(nonTrustedVisitingCachName);
	}

	/**
	 * ��������� �� ��, ��� ������������ ���� ����� �� ������� ������.
	 * ���� ������������ ����� �������� �������� ��� ����� �������, ��� ����� 10 ������, �� ������������
	 * ���������������� �������� ������������, ���� ��� ���������.
	 * ���� ��� �������� ������������ �������� ������� ��� �� 30 ������, ���� ��� ��� ���������, �� ��� ���
	 * ���� �� ������� ������� ��� ����� 30 ������.
	 *
	 * @param key ���� ����.
	 */
	public final void processUserAction(String key)
	{
		lock.writeLock().lock();
		try
		{
			boolean hasLastVisitingIp = lastVisitingTimeCache.isKeyInCache(key);
			boolean hasNonTrustedIp = nonTrustedLastVisitingTimeCache.isKeyInCache(key);
			//���� ������������ ������ ��� ������, �� �������� ��� � ������� ��������� �������.

		   if (!hasLastVisitingIp && !hasNonTrustedIp)
			{
				addNewIp(key);
			}
			//���� ������������ ��� �������� � �� ��� ����������
			else if (!hasNonTrustedIp)
			{
				checkIPForTrusted(key);
			}
			//������������ ��� �� �����������.
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
	 * ��������� ����� ip-�����.
	 * @param ip ip-�����.
	 * @return ���������� ��� ���.
	 */
	private void addNewIp(String ip)
	{
		lastVisitingTimeCache.put(new Element(ip, getCurrentTime()));
	}

	/**
	 * ��������� ������� �� ip-����� ����������.
	 * @param ip ip-�����.
	 * @return ������� �� ip-����� ����������.
	 */
	private void checkIPForTrusted(String ip)
	{
		//��������, ��� ������������ ������� �����, �.�. �� ����������.
		Element elem = lastVisitingTimeCache.get(ip);
		//��� ������� � ������ ip. ������� �����.
		if (elem == null) {
			//������������ ������� ������ �������.
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
			//������������ ������� ������ �������.
			nonTrustedLastVisitingTimeCache.put(new Element(ip, getCurrentTime()));
			lastVisitingTimeCache.remove(ip);
		}
	}

	/**
	 * �������� �� ��, ���� �� ������������ ip-����� ����������.
	 * @param ip ip-�����.
	 * @return ���������� ��� ���.
	 */
	private void checkIPBecomeTrusted(String ip)
	{
		//��������, ��� ������������ ������� ������.
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

		//������������ ������� �� ���������� �����.
		lastVisitingTimeCache.put(new Element(ip, getCurrentTime()));
		nonTrustedLastVisitingTimeCache.remove(ip);
	}

	/**
	 * ���������� ������������ ��� ���.
	 *
	 * @param ip ip-����� ������������.
	 * @return ���������� ��� ���.
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
	 * ������� �����.
	 *
	 * @return ����� � ������������.
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
	 * @return ���������� �������� (� ��������) ������� ����� ��� ���������� �������������
	 */
	protected long getCaptchaDelayForTrusted()
	{
		CSAFrontConfig config = ConfigFactory.getConfig(CSAFrontConfig.class);
		return config.getCommonCaptchaDelay();
	}

	/**
	 * @return ���������� �������� (� ��������) ������� ����� ��� ������������ �������������
	 */
	protected long getCaptchaDelayForUntrusted()
	{
		CSAFrontConfig config = ConfigFactory.getConfig(CSAFrontConfig.class);
		return config.getCaptchaBlockRulingStoppingDelay();
	}
}