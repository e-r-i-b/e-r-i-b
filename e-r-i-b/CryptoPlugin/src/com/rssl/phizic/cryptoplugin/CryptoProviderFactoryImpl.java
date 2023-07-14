package com.rssl.phizic.cryptoplugin;

import com.rssl.crypto.Plugin;
import com.rssl.crypto.PluginFactory;
import com.rssl.phizic.security.crypto.CryptoProvider;
import com.rssl.phizic.security.crypto.CryptoProviderFactory;
import com.rssl.phizic.utils.PropertyHelper;
import com.rssl.phizic.utils.libraries.LibraryLoader;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.Constants;

import java.util.Properties;
import java.io.File;
import java.io.IOException;

/**
 * @author Omeliyanchuk
 * @ created 28.02.2007
 * @ $Author$
 * @ $Revision$
 */

public class CryptoProviderFactoryImpl implements CryptoProviderFactory
{
	private final static String PLUGIN_STORAGE_PATH  = "com.rssl.iccs.crypto.plugin.storage.path";
	private final static String PLUGIN_DLL_NAME      = "plugin_jni.dll";
	private final static String CRYPTO_PROVIDER_NAME = "com.rssl.iccs.crypto.provider.name";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static volatile boolean nativeFactoryInitialized = false;

	private PluginFactory agavaFactory;
	private CryptoProviderImpl agavaProvider;
	private Properties properties;
	private File path = null;


	private File loadLibrary()
	{
		ApplicationConfig config = ApplicationConfig.getIt();
		return LibraryLoader.saveResourceToTmp(PLUGIN_DLL_NAME, config.getApplicationPrefixAdoptedToFileName());
	}

	public CryptoProviderFactoryImpl()
	{
		properties = PropertyHelper.readProperties("system.properties");
	}

	public String getName()
	{
		return properties.getProperty(CRYPTO_PROVIDER_NAME);

/*
		Plugin.Information info = agavaPlugin.getPluginInfo();
		return info.getName();
*/
	}

	public CryptoProvider getProvider()
	{
		if(agavaProvider != null)
			return agavaProvider;

		if( agavaFactory ==null )
			createFactory();

		try
		{
			String cryptoProviderName = getName();
			if( cryptoProviderName == null )
			{
				String message = "Не задано имя крипто провайдера";
				log.error(message);
				throw new RuntimeException( message );
			}

			Plugin nativePlugin = agavaFactory.newPlugin( cryptoProviderName );

			if(nativePlugin == null)
			{
				String message = "Не удалось создать crypto plugin";
				log.error(message);
				throw new RuntimeException( message );
			}

			agavaProvider = new CryptoProviderImpl(nativePlugin);
			if(agavaProvider == null)
			{
				String message = "Не удалось создать crypto provider";
				log.error(message);
				throw new RuntimeException( message );
			}

			return agavaProvider;
		}
		catch(Plugin.Failure ex)
		{
			log.error("Ошибка при создании crypto provider",ex);
			throw new RuntimeException(ex);
		}
	}

	private void createFactory()
	{
		if(!nativeFactoryInitialized)
		{
			path = loadLibrary();
			nativeFactoryInitialized = true;
		}

		try
		{
			PluginFactory.setNativeLibraryPath(path.getCanonicalPath());
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}

		String cryptoPath = properties.getProperty(PLUGIN_STORAGE_PATH);
		if( cryptoPath == null )
		{
			String message = "Не задан путь к крипто-провайдеру";
			log.error(message);
			throw new RuntimeException( message );
		}

		agavaFactory = new PluginFactory( cryptoPath );

		if(agavaFactory == null)
		{
			String message = "Не удалось создать фабрику для крипто-провайдера";
			log.error(message);
			throw new RuntimeException( message );
		}
	}

	public void close()
	{
		if( agavaProvider != null )
		{
			agavaProvider.release();
			agavaProvider = null;
			agavaFactory = null;

		}
	}
}
