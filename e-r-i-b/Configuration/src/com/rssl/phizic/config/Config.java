package com.rssl.phizic.config;

import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Pattern;

/**
 * @author Roshka
 * @ created 07.02.2006
 * @ $Author: gololobov $
 * @ $Revision: 68154 $
 */
public abstract class Config implements PropertyGetter
{
	/**
	 * Читатель настроек для конфига.
	 */
	private final PropertyReader reader;
	private long lastUpdateTime = 0L;
	private volatile boolean isUpdating = false;

	/**
	 * Любой конфиг должен реализовать данный конструктор.
	 *
	 * @param reader ридер.
	 */
	public Config(PropertyReader reader)
	{
		if (reader == null)
			throw new ConfigurationException("Необходимо указать ридер для конфига");

		this.reader = reader;
	}

	/**
	 * Устанавливает флаг начала обновления конфига.
	 */
    void startUpdating()
    {
        isUpdating = true;
    }

	/**
	 * @param key имя свойства
	 * @return значение свойства
	 */
	public String getProperty(String key)
	{
		return reader.getProperty(key);
	}

	/**
	 * @param key имя свойства
	 * @param defaultValue значение по уполчанию.
	 * @return значение свойства
	 */
	public String getProperty(String key, String defaultValue)
	{
		return reader.getProperty(key, defaultValue);
	}

	/**
	 * @param prefix префикс (подстрока, с которой начинаются именв свойств)
	 * @return список свойств
	 */
	protected Properties getProperties(String prefix)
	{
		return reader.getProperties(prefix);
	}

	/**
	 * @return все строковые настнойки из ридера
	 */
	protected Properties getAllProperties()
	{
		return reader.getAllProperties();
	}

	/**
	 * @param propertyName имя параметра.
	 * @return параметр в long.
	 */
	protected long getLongProperty(String propertyName)
	{
		return reader.getLongProperty(propertyName);
	}

	/**
	 * @param propertyName имя параметра.
	 * @param defaultValue значение по уполчанию.
	 * @return параметр в long.
	 */
	protected long getLongProperty(String propertyName, long defaultValue)
	{
		return reader.getLongProperty(propertyName, defaultValue);
	}

	/**
	 * @param propertyName имя параметра.
	 * @return параметр в int.
	 */
	protected int getIntProperty(String propertyName)
	{
		return reader.getIntProperty(propertyName);
	}

	/**
	 * @param propertyName имя параметра.
	 * @param defaultValue значение по умолчению
	 * @return параметр в int.
	 */
	protected int getIntProperty(String propertyName, int defaultValue)
	{
		return reader.getIntProperty(propertyName, defaultValue);
	}

	/**
	 * @param propertyName имя параметра.
	 * @return параметр в float.
	 */
	protected float getFloatProperty(String propertyName)
	{
		return reader.getFloatProperty(propertyName);
	}

	/**
	 * @param propertyName имя параметра.
	 * @param defaultValue значение по умолчению
	 * @return параметр в float.
	 */
	protected float getFloatProperty(String propertyName, float defaultValue)
	{
		return reader.getFloatProperty(propertyName, defaultValue);
	}

	/**
	 * @param propertyName имя параметра.
	 * @return параметр в double.
	 */
	protected double getDoubleProperty(String propertyName)
	{
		return reader.getDoubleProperty(propertyName);
	}

	/**
	 * @param propertyName имя параметра.
	 * @param defaultValue значение по умолчению
	 * @return параметр в double.
	 */
	protected double getFloatProperty(String propertyName, double defaultValue)
	{
		return reader.getDoubleProperty(propertyName, defaultValue);
	}

	/**
	 * @param propertyName имя параметра.
	 * @return параметр в boolean.
	 */
	protected boolean getBoolProperty(String propertyName)
	{
		return reader.getBoolProperty(propertyName);
	}

	/**
	 * @param propertyName имя параметра.
	 * @param defaultValue значение по умолчению
	 * @return параметр в boolean.
	 */
	protected boolean getBoolProperty(String propertyName, boolean defaultValue)
	{
		return reader.getBoolProperty(propertyName, defaultValue);
	}

	protected Pattern getPatternProperty(String propertyName)
	{
		return reader.getPatternProperty(propertyName);
	}

	protected Pattern getPatternProperty(String propertyName, Pattern defaultValue)
	{
		return reader.getPatternProperty(propertyName, defaultValue);
	}

	/**
	 * @param propertyKey ключ настройки.
	 * @return имеется ли настройка.
	 */
	protected boolean hasProperty(String propertyKey)
	{
		return reader.containProperty(propertyKey);
	}

	/**
	 * Инициализация конфига.
	 */
	void init()
	{
		doRefresh();
		lastUpdateTime = Calendar.getInstance().getTimeInMillis();
	}

	/**
	 * @return необходимо ли обновлять конфиг.
	 */
	boolean needRefresh()
	{
		if (isUpdating)
			return false;

		return reader.needUpdate(lastUpdateTime);
	}

	/**
	 * Обновляет конфиг.
	 */
	protected abstract void doRefresh() throws ConfigurationException;

	protected PropertyReader getReader()
	{
		return reader;
	}
}
