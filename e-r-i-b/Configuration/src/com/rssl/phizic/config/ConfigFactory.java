package com.rssl.phizic.config;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;

import java.io.InvalidClassException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 16.09.2005
 * Time: 14:00:24
 */
public final class ConfigFactory
{
    //*********************************************************************//
    //***************************  CLASS MEMBERS  *************************//
    //*********************************************************************//
    /**
     * Инстенс фабрики конфигов.
     */
    private static final ConfigFactory instance = new ConfigFactory();

	/**
	 * Говорим о необходимости обновить все конфиги. Обновление конфигов происходит во время обращения к нему.
	 * Устанавливается время обновления для всех ридеров, а конфиги потом смотрят нужно ли обновляться.
	 */
	public static synchronized void sendRefreshAllConfig()
	{
		ConfigFactoryHelper.sendRefreshAllReaders();
	}

    //*********************************************************************//
    //**************************  INSTANCE MEMBERS  ***********************//
    //*********************************************************************//

	private ConfigFactory(){}

    // содержит экземпляры конфигов
    private final Map<String, Config> configs = new ConcurrentHashMap<String, Config>();

    /**
     * Возвращает конфиг по его типу.
     * Выбирается конфиг для текущего веб-модуля и текущего названия приложения, прописанного в ApplicationConfig.getIt().getApplicationPrefix().
     * Не разрешено использовать в статиках, т.к. обновление переменных конфига происходит в момент вызова этого метода.
     *
     * @param clazz тип конфига
     * @return конфиг.
     * @throws ConfigurationException если запрашиваемый конфиг не найден
     */
    public static <T extends Config> T getConfig(Class<T> clazz)
    {
	    return getConfig(clazz, ApplicationInfo.getCurrentApplication(), ApplicationConfig.getIt().getApplicationPrefix());
    }

	/**
	 * Возвращает конфиг по его типу
	 * Выбирается конфиг для выбранного веб-модуля и текущего названия приложения, прописанного в ApplicationConfig.getIt().getApplicationPrefix().
	 * Не разрешено использовать в статиках, т.к. обновление переменных конфига происходит в момент вызова этого метода.
	 *
	 * @param clazz тип конфига
	 * @param application извлечь конфиг для конкретного приложения.
	 * @return конфиг.
	 * @throws ConfigurationException если запрашиваемый конфиг не найден
	 */
	public static <T extends Config> T getConfig(Class<T> clazz, Application application)
	{
		return getConfig(clazz, application, ApplicationConfig.getIt().getApplicationPrefix());
	}

	/**
	 * Возвращает конфиг по его типу
	 * Выбирается конфиг для выбранного веб-модуля и выбранного названия приложения.
	 * Не разрешено использовать в статиках, т.к. обновление переменных конфига происходит в момент вызова этого метода.
	 * 
	 * @param clazz тип конфига
	 * @param application извлечь конфиг для конкретного приложения.
	 * @param appPrefix префикс приложения.
	 * @return конфиг.
	 * @throws ConfigurationException если запрашиваемый конфиг не найден
	 */
	public static <T extends Config> T getConfig(Class<T> clazz, Application application, String appPrefix)
	{
		ConfigFactoryHelper.ConfigLoadInfo info = new ConfigFactoryHelper.ConfigLoadInfo(clazz, application, appPrefix);
		//noinspection unchecked
		return (T) instance.getConfigInner(info);
	}

	private Config getConfigInner(ConfigFactoryHelper.ConfigLoadInfo loadInfo)
	{
		String configNameWithApp = loadInfo.toString();

		//При первом обращении к конфигу, он еще не загружен, пытаемся загрузить его.
		if (!configs.containsKey(configNameWithApp))
		{
			synchronized (loadInfo.getClazz())
			{
				if (!configs.containsKey(configNameWithApp))
					return loadConfig(loadInfo);
			}
		}

		{
			Config config = configs.get(configNameWithApp);
			if (!config.needRefresh())
				return config;
		}

		//Конфиг нужно перезагрузить
		synchronized (loadInfo.getClazz())
		{
			Config config = configs.get(configNameWithApp);
			if (!config.needRefresh())
				//Конфиг уже обновился, отдаем его.
				return config;

			config.startUpdating();
		}
		return loadConfig(loadInfo);
	}

	/**
     * Загрузить конфиг.
     * @param loadInfo информация для загрузки конфига.
	 * @return новый конфиг.
     * @throws InvalidClassException Если конфиг не может быть приведен к типу clazz
     */
    private Config loadConfig(ConfigFactoryHelper.ConfigLoadInfo loadInfo)
    {
	    try
        {
	        Config config = ConfigFactoryHelper.loadConfig(loadInfo);

		    if(!loadInfo.getClazz().isInstance(config))
	            throw new InvalidClassException("Конфиг " + config.getClass().getName() + " не реализует " + loadInfo.getClazz().getName());

	        configs.put(loadInfo.toString(), config);
		    return config;
	    }
        catch(InvalidClassException ex)
        {
            throw new ConfigurationException("Конфигурация c именем" + loadInfo.toString() + " не может быть зарегистрирована", ex);
        }
        catch(ConfigurationException ex)
        {
            throw ex;
        }
        catch(RuntimeException ex)
        {
            throw new ConfigurationException("Конфигурация c именем" + loadInfo.toString() + " не найдена", ex);
        }
    }

	/**
	 * @param fileName имя файла, откуда брать настройки, если их нет в БД.
	 * @return ридер для чтения настроек.
	 */
	public static ResourcePropertyReader getReaderByFileName(String fileName)
	{
		return ConfigFactoryHelper.loadResourcePropertyReader(fileName);
	}
}
