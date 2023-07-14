package com.rssl.phizic.cache;

import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.files.FileHelper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;
import net.sf.ehcache.config.DiskStoreConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * @author krenev
 * @ created 27.04.2012
 * @ $Author$
 * @ $Revision$
 */

public class CacheProvider
{
	private static final Log log = LogFactory.getLog(CacheProvider.class);
	private static volatile CacheManager cacheManager = null;
	private static Set<String> cachePathes = new HashSet<String>();

	/**
	 * Получить кеш по имени.
	 * @param name имя кеша
	 * @return кеш или null, если кеш отсутсвует.
	 */
	public static Cache getCache(String name)
	{
		return getCacheManager().getCache(name);
	}

	private static CacheManager getCacheManager()
	{
		if (cacheManager != null)
		{
			return cacheManager;
		}
		synchronized (CacheProvider.class)
		{
			if (cacheManager != null)
			{
				return cacheManager;
			}
			cacheManager = createCacheManager("/erib-ehcache.xml");
		}

		return cacheManager;
	}

	public static CacheManager createCacheManager(String configFile)
	{
		//Пробуем загрузить ehcache.xml из класспасов (например папка settings)
		InputStream stream = ClassHelper.getInputStreamFromClassPath(configFile);
		if (stream == null)
		{
			//пробуем нйти по страндартному пути com/rssl/phizic/cache/
			stream = ClassHelper.getInputStreamFromClassPath("com/rssl/phizic/cache" + configFile);
		}
		if (stream != null)
		{
			try
			{
				return new CacheManager(parseConfiguration(stream));
			}
			finally
			{
				try
				{
					stream.close();
				}
				catch (IOException e)
				{
					log.error("Ошибка закрытия потока erib-ehcache.xml", e);
				}
			}
		}
		return new CacheManager(); //не нашли ничего - инстанциируем дефолтный.
	}

	/**
	 * Распарсить конфигурацию из входного потока
	 * @param stream поток
	 * @return объектное представление конфигурации
	 */
	private static Configuration parseConfiguration(InputStream stream)
	{
		Configuration configuration = ConfigurationFactory.parseConfiguration(stream);
		DiskStoreConfiguration diskStoreConfiguration = configuration.getDiskStoreConfiguration();
		cachePathes.add(diskStoreConfiguration.getPath());
		diskStoreConfiguration.setPath(diskStoreConfiguration.getPath() + File.separator + getApplicationInstanceUUID());
		return configuration;
	}

	/**
	 * Получить уникальный идентификатор запущенного инстанса приложения.
	 * @return уникальный идентификатор запущенного инстанса приложения
	 */
	private static String getApplicationInstanceUUID()
	{
		return new RandomGUID().getStringValue();
	}

	/**
	 * создать кеш с указанными настройками
	 * @param name имя кеша
	 * @param maxElementsInMemory максимальное количество элементов в памяти
	 * @param overflowToDisk сбрасывать ли сообщения на диск.
	 * @param eternal вечный ли кеш
	 * @param timeToIdleSeconds время "простоя" сообщений в кеше
	 * @param timeToLiveSeconds время жизни сообщений в кеше.
	 */
	public static void addCache(String name, int maxElementsInMemory, boolean overflowToDisk, boolean eternal, int timeToIdleSeconds, int timeToLiveSeconds)
	{
		getCacheManager().addCache(new Cache(name, maxElementsInMemory, overflowToDisk, eternal, timeToIdleSeconds, timeToLiveSeconds));
	}

	/**
	 * Добавить кеш, используя настройки дефолтного кеша
	 * @param name имя кеша
	 */
	public static void addCache(String name)
	{
		getCacheManager().addCache(name);
	}

	/**
	 * Добавляет кеш.
	 * @param ehcache добавляемый кеш.
	 */
	public static void addCache(Ehcache ehcache)
	{
		getCacheManager().addCache(ehcache);
	}

	/**
	 * Остановить работу кеша.
	 */
	public static synchronized void shutdown()
	{
		if (cacheManager == null)
		{
			return;
		}
		cacheManager.shutdown();
		cacheManager = null;

		for (String  path : cachePathes)
		{
			try
			{
				FileHelper.deleteDirectory(path, false);
			}
			catch (Exception ignore)
			{
			}
		}
	}

	/**
	 * удаление кэша.
	 * @param name имя удаляемого кэша. 
	 */
	public static synchronized void removeCache(String name)
	{
		getCacheManager().removeCache(name);
	}
}
