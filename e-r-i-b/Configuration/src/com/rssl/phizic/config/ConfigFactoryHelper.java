package com.rssl.phizic.config;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.loader.ConfigFactoryDocument;
import com.rssl.phizic.config.loader.ConfigInfo;
import com.rssl.phizic.config.loader.DbInfo;
import com.rssl.phizic.config.loader.ImplementationInfo;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.resources.ResourceHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.lang.ArrayUtils;
import org.w3c.dom.Document;

import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Построитель фабрики конфигов.
 *
 * Задачи, которые он решает:
 * -Загрузка файла настроек ConfigFactory.
 * -Создание и распространение необходимых ридеров.
 * -Обновление ридеров.
 * -Создание конфигов и распространение для конкретного инстанса модуля.
 *
 * @author bogdanov
 * @ created 11.07.2013
 * @ $Author$
 * @ $Revision$
 */

class ConfigFactoryHelper
{
	private static final Class[] parameterTypesWithApplication =
    {
	    PropertyReader.class,
        Application.class
    };

	private static final Class[] parameterTypes =
    {
	    PropertyReader.class,
    };

	static class ConfigLoadInfo
	{
		private final Class clazz;
		private final Application application;
		private final String appPrefix;

		ConfigLoadInfo (Class clazz, Application application, String appPrefix)
		{
			if (clazz == null)
				throw new ConfigurationException("Не установлено поле 'clazz'");

			if (application == null)
				throw new ConfigurationException("Не установлено поле 'application'");

			if (StringHelper.isEmpty(appPrefix))
				throw new ConfigurationException("Не установлено поле 'appPrefix'");

			this.application = application;
			this.appPrefix = appPrefix;
			this.clazz = clazz;
		}

		Application getApplication()
		{
			return application;
		}

		String getAppPrefix()
		{
			return appPrefix;
		}

		Class getClazz()
		{
			return clazz;
		}

		@Override
		public String toString()
		{
			return clazz.getName() + "$$" + application.name() + "$$" + appPrefix;
		}

		@Override
		public boolean equals(Object o)
		{
			if (this == o)
			{
				return true;
			}
			if (o == null || getClass() != o.getClass())
			{
				return false;
			}
			return toString().equals(o.toString());
		}

		@Override
		public int hashCode()
		{
			return toString().hashCode();
		}
	}

	private static final String CONFIG_FACTORY_CONFIG_FILE_NAME = "config-factory.xml";
	/**
	 * Загруженные из xml настройки.
	 */
	private static volatile ConfigFactoryDocument configFactoryDocument;

	/**
	 * Список ридеров из файлов.
	 */
	private static final ConcurrentHashMap<String, ResourcePropertyReader> readersByFileName = new ConcurrentHashMap<String, ResourcePropertyReader>();

	/**
	 * Список подключенных ридеров из базы данных.
	 */
	private static final ConcurrentHashMap<String, DbPropertyReader> dbReadersByInstance = new ConcurrentHashMap<String, DbPropertyReader>();
	/**
	 * Загружает конфиг по имени класса и названию приложения.
	 *
	 * @param loadInfo информация о конфиге.
	 * @return нужный конфиг.
	 */
	static Config loadConfig(ConfigLoadInfo loadInfo)
	{
		try
		{
			ConfigFactoryDocument configsInfo = getConfigFactoryDocument();
			ConfigInfo configInfo = configsInfo.getConfig(loadInfo.getClazz().getName());
			Application application = loadInfo.getApplication();
			ImplementationInfo implInfo = configInfo.getImplementation(application);

			if (implInfo == null)
			{
				implInfo = configInfo.getDefaultImplementation();
			}

			Class clazz = ClassHelper.loadClass(implInfo.getClazz());

			Config config = getConfig(loadInfo, implInfo, clazz);
			//Вызываем вычитывание настроек конфига.
			config.init();
			return config;
		}
		catch (Exception ex)
		{
			throw new ConfigurationException("Конфиг " + loadInfo.toString() + " не удалось загрузить из " + CONFIG_FACTORY_CONFIG_FILE_NAME, ex);
		}
	}

	private static Config getConfig(ConfigLoadInfo loadInfo, ImplementationInfo implInfo, Class clazz) throws Exception
	{
		Constructor[] constructors = clazz.getConstructors();
		if (ArrayUtils.isNotEmpty(constructors) && constructors.length == 1)
		{
			if (ArrayUtils.isEquals(constructors[0].getParameterTypes(), parameterTypes))
				return (Config) constructors[0].newInstance(getReaders(implInfo, loadInfo.getAppPrefix()));
			if (ArrayUtils.isEquals(constructors[0].getParameterTypes(), parameterTypesWithApplication))
				return (Config)constructors[0].newInstance(getReaders(implInfo, loadInfo.getAppPrefix()), loadInfo.getApplication());
		}
		throw new ConfigurationException("Конфиг " + loadInfo.toString() + " не реализует конструктор по умолчанию");
	}

	/**
	 * Возвращает ридер для данного конфига.
	 *
	 * @param info информация по ридерам.
	 * @param appPrefix прификс приложения.
	 * @return ридер.
	 * @throws Exception
	 */
	private static PropertyReader getReaders(ImplementationInfo info, String appPrefix) throws Exception
	{
		//сначала загружаем ридер из базы данных.
		final List<PropertyReader> readers = new LinkedList<PropertyReader>();
		readers.add(loadDbPropertyReader(info.getDbInfo().getName(), appPrefix));

		//затем загружаем ридер из файла.
		if (!StringHelper.isEmpty(info.getFileName()))
			readers.add(loadResourcePropertyReader(info.getFileName()));

		PropertyReader[] readersArray = readers.toArray(new PropertyReader[readers.size()]);
		if (StringHelper.isEmpty(info.getReader()))
		{
			CompositePropertyReader result = new CompositePropertyReader(readersArray);
			result.setRefreshEvery(DateHelper.MILLISECONDS_IN_MINUTE * info.getDbInfo().getPeriodForUpdate());
			return result;
		}
		else
		{
			Class readerClass = ClassHelper.loadClass(info.getReader());
			if (!CompositePropertyReader.class.isAssignableFrom(readerClass))
				throw new ConfigurationException("Загруженный класс ридер " + info.getReader() + " не является наследником от " + CompositePropertyReader.class);
			CompositePropertyReader result = (CompositePropertyReader) readerClass.getConstructor(readersArray.getClass()).newInstance((Object)readersArray);
			result.setRefreshEvery(DateHelper.MILLISECONDS_IN_MINUTE * info.getDbInfo().getPeriodForUpdate());
			return result;
		}
	}

	/**
	 * Загружает настройки по имени.
	 *
	 * @param dbName название базы данных.
	 * @param appPrefix перфикс приложения.
	 * @return читатель настроек.
	 */
	static DbPropertyReader loadDbPropertyReader(String dbName, String appPrefix)
	{
		DbInfo dbInfo = ConfigFactoryHelper.getConfigFactoryDocument().getDbInfoByName(dbName);
		String dbInstance = dbInfo.getDbInstance(appPrefix);
		String dbInstanceKey = dbInstance + "$$" + appPrefix;

		if (dbReadersByInstance.containsKey(dbInstanceKey))
			return dbReadersByInstance.get(dbInstanceKey);

		try
		{
			DbPropertyReader reader = new DbPropertyReader(appPrefix, dbInstance);
			reader.setUpdatePeriod(DateHelper.MILLISECONDS_IN_MINUTE * dbInfo.getPeriodForUpdate());

			dbReadersByInstance.putIfAbsent(dbInstanceKey, reader);
			return dbReadersByInstance.get(dbInstanceKey);
		}
		catch (Exception ex)
		{
			throw new ConfigurationException("Не удалось вычитать DbPropertyReader из конфигурационного файла для " + dbInfo.getName(), ex);
		}
	}

	/**
	 * Загружает настройки из файла.
	 *
	 * @param fileName имя файла.
	 * @return читатель настроек.
	 */
	static ResourcePropertyReader loadResourcePropertyReader(String fileName)
	{
		if (readersByFileName.containsKey(fileName))
			return readersByFileName.get(fileName);

		readersByFileName.putIfAbsent(fileName, new ResourcePropertyReader(fileName));
		return readersByFileName.get(fileName);
	}

	/**
	 * Возвращает настройки для фабрики конфигов.
	 *
	 * @return настройки фабрики конфигов.
	 */
	static ConfigFactoryDocument getConfigFactoryDocument()
	{
		if (configFactoryDocument != null)
			return configFactoryDocument;

		synchronized (ConfigFactoryHelper.class)
		{
			if (configFactoryDocument != null)
				return configFactoryDocument;

			try
			{
				configFactoryDocument = new ConfigFactoryDocument(loadConfigs(CONFIG_FACTORY_CONFIG_FILE_NAME));
			}
			catch (Exception e)
			{
				throw new ConfigurationException("Невозможно загрузить файл с настройками " + CONFIG_FACTORY_CONFIG_FILE_NAME, e);
			}
			return configFactoryDocument;
		}
	}

	/**
	 * Загружает настройки фабрики конфигов.
	 * @param configFileName имя файла, в котором хранятся конфиги.
	 * @return загруженные настройки.
	 */
	private static Document loadConfigs(String configFileName)
	{
		try
		{
			String configXml = ResourceHelper.loadResourceAsString(configFileName);
			return XmlHelper.parse(configXml);
		}
		catch (Exception e)
		{
			throw new ConfigurationException("Не удалось открыть конфигурационный файл " + configFileName + " для загрузки " + ConfigFactory.class.getName(), e);
		}
	}

	/**
	 * Уведомить все ридеры о необходимости перечитаться.
	 */
	static void sendRefreshAllReaders()
	{
		for (DbPropertyReader reader : dbReadersByInstance.values())
		{
			reader.sendRefresh();
		}
	}
}
