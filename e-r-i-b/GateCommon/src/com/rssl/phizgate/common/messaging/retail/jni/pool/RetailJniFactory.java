package com.rssl.phizgate.common.messaging.retail.jni.pool;

import com.rssl.api.retail.Retail;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.config.GateConnectionConfig;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.utils.libraries.LibraryLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool.BaseKeyedPoolableObjectFactory;

import java.io.File;
import java.io.IOException;

/**
 * @author Omeliyanchuk
 * @ created 24.11.2009
 * @ $Author$
 * @ $Revision$
 */

/**
 * Фабрика для создания retail jni
 */
public class RetailJniFactory extends BaseKeyedPoolableObjectFactory
{
	private static final Log log = LogFactory.getLog(Constants.LOG_MODULE_GATE.toValue());

	private static final String RETAIL_JNI = "retail_jni";

	private static volatile boolean nativeFactoryInitialized = false;
	private boolean isDebug;
	private boolean isRemote;

	/**
	 * Инициализировать фабрику
	 * @param isRemote удаленный режим работы
	 * @param isDebug режим работы jni
	 */
	public void init(Boolean isRemote, Boolean isDebug)
	{
		this.isRemote = isRemote;
		this.isDebug = isDebug;
		//загружаем библиотеку только один раз
		if(!nativeFactoryInitialized)
		{
			loadAndInitializeLibrary();
			nativeFactoryInitialized = true;
		}

	}

	/**
	 * синхронизуем, чтоб только один загружал.
	 */
	private synchronized void loadAndInitializeLibrary()
	{
		//если уже кто-то загрузил
		if(nativeFactoryInitialized)
			return;

		try
		{
			RetailJniFactory.log.debug("Начинаем загружать библиотеку для retail jni");

			ApplicationConfig appConfig = ApplicationConfig.getIt();
			//Прочитать настройку из файла и выбрать соответствующее имя библиотеки
			String platformProperty=ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getPlatformType();
			if (platformProperty.equals("i64"))
				Retail.Factory.setPlatform(Retail.NATIVE_PLATFORM.WINDOWS_I64);
			else if (platformProperty.equals("x64"))
					Retail.Factory.setPlatform(Retail.NATIVE_PLATFORM.WINDOWS_X64);
				else
					Retail.Factory.setPlatform(Retail.NATIVE_PLATFORM.WINDOWS_X86);

			File dir = LibraryLoader.saveResourceToTmp(Retail.Factory.mapLibraryName(), appConfig.getApplicationPrefixAdoptedToFileName() + File.separator + RetailJniFactory.RETAIL_JNI);
			LibraryLoader.saveResourceToTmp(Retail.Factory.mapExecuteName(), appConfig.getApplicationPrefixAdoptedToFileName() + File.separator + RetailJniFactory.RETAIL_JNI);

			Retail.Factory.setNativeLibraryPath(dir.getCanonicalPath());
			if (isDebug)
				Retail.Factory.setJabberLevel(Retail.JABBER_LEVEL.DEBUG);

			nativeFactoryInitialized = true;

			RetailJniFactory.log.debug("Закончили загружать библиотеку для retail jni");

		}
		catch (IOException e)
		{
			throw new RuntimeException("Ошибка при распаковке retail jni", e);
		}
	}

	/**
	 * создать объект
	 * @param key ключ, для каждого ключа свой пул
	 * @return созданный объект
	 * @throws Exception
	 */
	public Object makeObject(Object key) throws Exception
	{
		RetailJniFactory.log.debug("Начали создавать новый инстанс retail jni. Нить:" + Thread.currentThread().getId());

		RetailWrapper wrapper = null;
		if (isRemote)
		{
			wrapper = new RemoteRetailWrapper(getHost(), getPort(), getVersion());
		}
		else
		{
			wrapper = new LocateRetailWrapper(getLocation(), getVersion());
		}

		RetailJniFactory.log.debug("Закончили создавать новый инстанс retail jni. Нить:" + Thread.currentThread().getId());
		
		return wrapper;
	}

	/**
	 * вернуть объект в пул(ВОЗВРАЩАТЬ НАДО ОБЯЗАТЕЛЬНО!!!!)
	 * @param key ключ
	 * @param obj объект который надо вернуть
	 * @throws Exception
	 */
	public void destroyObject(Object key, Object obj) throws Exception
	{
		RetailJniFactory.log.debug("Удаляем инстанс retail jni. Нить:" + Thread.currentThread().getId());
		Retail retail = (Retail)obj;
		retail.release();
		super.destroyObject(key, obj);
		RetailJniFactory.log.debug("Удалили инстанс retail jni. Нить:" + Thread.currentThread().getId());
	}

	/**
	 * активация инстанса
	 * @param key - ключ
	 * @param obj - обертка над инстансом ретейла
	 */
	public void activateObject(Object key, Object obj)
	{
		try
		{
			if (isRemote)
			{
				((RemoteRetailWrapper)obj).activate(getHost(), getPort(), getVersion());
			}
			else
			{
				((LocateRetailWrapper)obj).activate(getLocation(), getVersion());
			}
		}
		catch (Exception e)
		{
			log.error("Произошла ошибка при активации инстанса Ретейл: ", e);
		}
    }

	private String getHost()
	{
		return ConfigFactory.getConfig(GateConnectionConfig.class).getProperty("com.rssl.phizic.gate.rs-retail-v64.remote.host");
	}

	private int getPort()
	{
		return Integer.parseInt(ConfigFactory.getConfig(GateConnectionConfig.class).getProperty("com.rssl.phizic.gate.rs-retail-v64.remote.port"));
	}

	private String getLocation()
	{
		return ConfigFactory.getConfig(GateConnectionConfig.class).getProperty("com.rssl.phizic.gate.rs-retail-v64.location");
	}

	private int getVersion()
	{
		return Integer.parseInt(ConfigFactory.getConfig(GateConnectionConfig.class).getProperty("com.rssl.phizic.gate.rs-retail-v64.version"));
	}



}
