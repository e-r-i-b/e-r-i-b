package com.rssl.phizic.gate.cache.proxy;

import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.cache.RefreshCacheService;
import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.io.Serializable;
import java.util.List;

/**
 * @author mihaylov
 * @ created 19.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class RefreshCacheServiceImpl extends AbstractService implements RefreshCacheService
{
	private static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CACHE);
	private static final long ONE_SECOND = 1000L;

	public RefreshCacheServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public boolean refreshDepoCacheService(DepoAccount depoAccount, int seconds)
	{
		if(depoAccount != null)
		{
			CacheKeyComposer composer = GateBusinessCacheConfig.getCleanableComposer(DepoAccount.class);
			Serializable callbackKey = composer.getClearCallbackKey(depoAccount, null);
			return refreshBusinessGateCache(callbackKey, seconds);
		}

		return false;
	}

	private boolean refreshBusinessGateCache(Serializable callbackKey, int seconds)
	{
		if(callbackKey!=null)
		{
			boolean clearCallbackCache = true;
			Cache callbackCache = GateBusinessCacheSingleton.getInstance().getCache(GateBusinessCacheSingleton.LINKS_CACHE_NAME);
			Element elem = callbackCache.get(callbackKey);
			if(elem!=null)
			{
				List<Pair<String,Serializable>> keys = (List<Pair<String,Serializable>> )elem.getObjectValue();
				for (Pair<String,Serializable> key : keys)
				{
					Cache cache = GateBusinessCacheSingleton.getInstance().getCache(key.getFirst());
					Element element = cache.get(key.getSecond());

					if(element==null)//���� �������� null, �� �������� ���� �� ����, ���� ��� �������
						continue;

					if(isExpiredRefreshTime(element,seconds))
					{
						log.trace("�������  �� ���� "+key.getFirst()+  " �� �����  "+key.getSecond());
						cache.remove(key.getSecond());
					}
					else
						clearCallbackCache = false;//����� ������� callbackCache, ������ ���� ������� ��� �� ���� ��������� ��������
				}
			}
			if(clearCallbackCache)
			{
				log.trace("������� ��� �� ������ ���� ��� �������� � ������ "+callbackKey);
				callbackCache.remove(callbackKey);
				return true;
			}
		}
		return false;
	}

	/**
	 * ���������, ������� �� ����� ����� �������� ����� ������� ������ �� ���� ��� ���.
	 * @param element - ������� ��� �������� ���������� ��������
	 * @param seconds - �����, �� ��������� �������� ����� ������� ������� �� ����
	 * @return true - ������� ����� �������
	 */
	private boolean isExpiredRefreshTime(Element element,int seconds)
	{
		return System.currentTimeMillis() - element.getCreationTime() > seconds * ONE_SECOND;		
	}
}
