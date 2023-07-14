package com.rssl.phizic.gate.cache;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.cache.proxy.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.impl.ServiceCreator;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.cache.CacheProvider;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import net.sf.ehcache.Cache;

import java.lang.reflect.Method;

/**
 * @author Omeliyanchuk
 * @ created 11.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class CacheServiceCreator implements ServiceCreator
{
	private static final int NOOP_INDEX  = 0; // индекс в таблице перехватчиков для пустого перехратчика
	private static final Callback callbacks[] = {NoOp.INSTANCE, new CacheCallback()};
	private static final Class callbackTypes[] = {NoOp.class,CacheCallback.class};
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CACHE);

	private class CachableFilter implements CallbackFilter
	{
		public int accept(Method method)
		{
			Method interfaceMethod = CacheAnnotationHelper.getInterfaceMethodByMethod(method);
			if(interfaceMethod==null)
				return NOOP_INDEX;

			Cachable cachable = interfaceMethod.getAnnotation(Cachable.class);
			if(cachable==null)
				return NOOP_INDEX;

			GateBusinessCacheSingleton sing = GateBusinessCacheSingleton.getInstance();
			synchronized (CacheServiceCreator.class) {
				//в кэше, записи добавленные из erib-ehcache.xml содержатся без завязки на приложение.
				//поэтому будем создавать свой кэш, с параметрами данного кэша, если он конечно же есть.
				Cache cache = CacheProvider.getCache(cachable.name());
				if (sing.getCache(GateBusinessCacheConfig.getCacheNameByMethod(interfaceMethod)) != null)
				{
					//ничего не делаем, кеш уже добавлен ранее.
				}
				else if (cache == null)
				{
				    log.warn("Для метода " + interfaceMethod.toString() + " нет описания в erib-ehcache.xml");
					sing.addCache(
						GateBusinessCacheConfig.getCacheNameByMethod(interfaceMethod),
						cachable.maxElementsInMemory(),	cachable.timeToIdleSeconds(),cachable.timeToLiveSeconds());
				}
				else
				{
					CacheProvider.removeCache(cachable.name());
					sing.addCache(GateBusinessCacheConfig.getCacheNameByMethod(interfaceMethod),
						cache.getMaxElementsInMemory(), cache.isEternal(),
						(int) cache.getTimeToIdleSeconds(), (int) cache.getTimeToLiveSeconds());
				}

				log.trace("Добавлен кеш для метода " + interfaceMethod.toString());
			}
			sing.registerMethod(interfaceMethod);
			return 1;
		}
	}

	public Service createService(String serviceClassName,GateFactory factory) throws GateException
	{
		Class serviceClass = loadServiceClass(serviceClassName);
		Enhancer en = new Enhancer();
		en.setUseCache(true);
		en.setInterceptDuringConstruction(false);

		en.setCallbackTypes(callbackTypes);
		en.setCallbackFilter(new CachableFilter());
		en.setSuperclass(serviceClass);
		en.setCallbacks(callbacks);

		Service service = (Service)en.create(new Class[]{GateFactory.class},new Object[]{factory});

		return service;
	}

	private <T extends Service> Class<T> loadServiceClass(String serviceClassName) throws GateException
	{
		Class<T> serviceClass = null;
		try
		{
			serviceClass = ClassHelper.loadClass(serviceClassName);
		}
		catch (ClassNotFoundException e)
		{
			throw new GateException(e);
		}
		return serviceClass;
	}
}
