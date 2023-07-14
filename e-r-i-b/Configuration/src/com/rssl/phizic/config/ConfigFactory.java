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
     * ������� ������� ��������.
     */
    private static final ConfigFactory instance = new ConfigFactory();

	/**
	 * ������� � ������������� �������� ��� �������. ���������� �������� ���������� �� ����� ��������� � ����.
	 * ��������������� ����� ���������� ��� ���� �������, � ������� ����� ������� ����� �� �����������.
	 */
	public static synchronized void sendRefreshAllConfig()
	{
		ConfigFactoryHelper.sendRefreshAllReaders();
	}

    //*********************************************************************//
    //**************************  INSTANCE MEMBERS  ***********************//
    //*********************************************************************//

	private ConfigFactory(){}

    // �������� ���������� ��������
    private final Map<String, Config> configs = new ConcurrentHashMap<String, Config>();

    /**
     * ���������� ������ �� ��� ����.
     * ���������� ������ ��� �������� ���-������ � �������� �������� ����������, ������������ � ApplicationConfig.getIt().getApplicationPrefix().
     * �� ��������� ������������ � ��������, �.�. ���������� ���������� ������� ���������� � ������ ������ ����� ������.
     *
     * @param clazz ��� �������
     * @return ������.
     * @throws ConfigurationException ���� ������������� ������ �� ������
     */
    public static <T extends Config> T getConfig(Class<T> clazz)
    {
	    return getConfig(clazz, ApplicationInfo.getCurrentApplication(), ApplicationConfig.getIt().getApplicationPrefix());
    }

	/**
	 * ���������� ������ �� ��� ����
	 * ���������� ������ ��� ���������� ���-������ � �������� �������� ����������, ������������ � ApplicationConfig.getIt().getApplicationPrefix().
	 * �� ��������� ������������ � ��������, �.�. ���������� ���������� ������� ���������� � ������ ������ ����� ������.
	 *
	 * @param clazz ��� �������
	 * @param application ������� ������ ��� ����������� ����������.
	 * @return ������.
	 * @throws ConfigurationException ���� ������������� ������ �� ������
	 */
	public static <T extends Config> T getConfig(Class<T> clazz, Application application)
	{
		return getConfig(clazz, application, ApplicationConfig.getIt().getApplicationPrefix());
	}

	/**
	 * ���������� ������ �� ��� ����
	 * ���������� ������ ��� ���������� ���-������ � ���������� �������� ����������.
	 * �� ��������� ������������ � ��������, �.�. ���������� ���������� ������� ���������� � ������ ������ ����� ������.
	 * 
	 * @param clazz ��� �������
	 * @param application ������� ������ ��� ����������� ����������.
	 * @param appPrefix ������� ����������.
	 * @return ������.
	 * @throws ConfigurationException ���� ������������� ������ �� ������
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

		//��� ������ ��������� � �������, �� ��� �� ��������, �������� ��������� ���.
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

		//������ ����� �������������
		synchronized (loadInfo.getClazz())
		{
			Config config = configs.get(configNameWithApp);
			if (!config.needRefresh())
				//������ ��� ���������, ������ ���.
				return config;

			config.startUpdating();
		}
		return loadConfig(loadInfo);
	}

	/**
     * ��������� ������.
     * @param loadInfo ���������� ��� �������� �������.
	 * @return ����� ������.
     * @throws InvalidClassException ���� ������ �� ����� ���� �������� � ���� clazz
     */
    private Config loadConfig(ConfigFactoryHelper.ConfigLoadInfo loadInfo)
    {
	    try
        {
	        Config config = ConfigFactoryHelper.loadConfig(loadInfo);

		    if(!loadInfo.getClazz().isInstance(config))
	            throw new InvalidClassException("������ " + config.getClass().getName() + " �� ��������� " + loadInfo.getClazz().getName());

	        configs.put(loadInfo.toString(), config);
		    return config;
	    }
        catch(InvalidClassException ex)
        {
            throw new ConfigurationException("������������ c ������" + loadInfo.toString() + " �� ����� ���� ����������������", ex);
        }
        catch(ConfigurationException ex)
        {
            throw ex;
        }
        catch(RuntimeException ex)
        {
            throw new ConfigurationException("������������ c ������" + loadInfo.toString() + " �� �������", ex);
        }
    }

	/**
	 * @param fileName ��� �����, ������ ����� ���������, ���� �� ��� � ��.
	 * @return ����� ��� ������ ��������.
	 */
	public static ResourcePropertyReader getReaderByFileName(String fileName)
	{
		return ConfigFactoryHelper.loadResourcePropertyReader(fileName);
	}
}
