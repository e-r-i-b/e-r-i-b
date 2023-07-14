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
	 * �������� ��� �� �����.
	 * @param name ��� ����
	 * @return ��� ��� null, ���� ��� ����������.
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
		//������� ��������� ehcache.xml �� ���������� (�������� ����� settings)
		InputStream stream = ClassHelper.getInputStreamFromClassPath(configFile);
		if (stream == null)
		{
			//������� ���� �� ������������� ���� com/rssl/phizic/cache/
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
					log.error("������ �������� ������ erib-ehcache.xml", e);
				}
			}
		}
		return new CacheManager(); //�� ����� ������ - ������������� ���������.
	}

	/**
	 * ���������� ������������ �� �������� ������
	 * @param stream �����
	 * @return ��������� ������������� ������������
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
	 * �������� ���������� ������������� ����������� �������� ����������.
	 * @return ���������� ������������� ����������� �������� ����������
	 */
	private static String getApplicationInstanceUUID()
	{
		return new RandomGUID().getStringValue();
	}

	/**
	 * ������� ��� � ���������� �����������
	 * @param name ��� ����
	 * @param maxElementsInMemory ������������ ���������� ��������� � ������
	 * @param overflowToDisk ���������� �� ��������� �� ����.
	 * @param eternal ������ �� ���
	 * @param timeToIdleSeconds ����� "�������" ��������� � ����
	 * @param timeToLiveSeconds ����� ����� ��������� � ����.
	 */
	public static void addCache(String name, int maxElementsInMemory, boolean overflowToDisk, boolean eternal, int timeToIdleSeconds, int timeToLiveSeconds)
	{
		getCacheManager().addCache(new Cache(name, maxElementsInMemory, overflowToDisk, eternal, timeToIdleSeconds, timeToLiveSeconds));
	}

	/**
	 * �������� ���, ��������� ��������� ���������� ����
	 * @param name ��� ����
	 */
	public static void addCache(String name)
	{
		getCacheManager().addCache(name);
	}

	/**
	 * ��������� ���.
	 * @param ehcache ����������� ���.
	 */
	public static void addCache(Ehcache ehcache)
	{
		getCacheManager().addCache(ehcache);
	}

	/**
	 * ���������� ������ ����.
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
	 * �������� ����.
	 * @param name ��� ���������� ����. 
	 */
	public static synchronized void removeCache(String name)
	{
		getCacheManager().removeCache(name);
	}
}
