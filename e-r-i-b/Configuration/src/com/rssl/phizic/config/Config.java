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
	 * �������� �������� ��� �������.
	 */
	private final PropertyReader reader;
	private long lastUpdateTime = 0L;
	private volatile boolean isUpdating = false;

	/**
	 * ����� ������ ������ ����������� ������ �����������.
	 *
	 * @param reader �����.
	 */
	public Config(PropertyReader reader)
	{
		if (reader == null)
			throw new ConfigurationException("���������� ������� ����� ��� �������");

		this.reader = reader;
	}

	/**
	 * ������������� ���� ������ ���������� �������.
	 */
    void startUpdating()
    {
        isUpdating = true;
    }

	/**
	 * @param key ��� ��������
	 * @return �������� ��������
	 */
	public String getProperty(String key)
	{
		return reader.getProperty(key);
	}

	/**
	 * @param key ��� ��������
	 * @param defaultValue �������� �� ���������.
	 * @return �������� ��������
	 */
	public String getProperty(String key, String defaultValue)
	{
		return reader.getProperty(key, defaultValue);
	}

	/**
	 * @param prefix ������� (���������, � ������� ���������� ����� �������)
	 * @return ������ �������
	 */
	protected Properties getProperties(String prefix)
	{
		return reader.getProperties(prefix);
	}

	/**
	 * @return ��� ��������� ��������� �� ������
	 */
	protected Properties getAllProperties()
	{
		return reader.getAllProperties();
	}

	/**
	 * @param propertyName ��� ���������.
	 * @return �������� � long.
	 */
	protected long getLongProperty(String propertyName)
	{
		return reader.getLongProperty(propertyName);
	}

	/**
	 * @param propertyName ��� ���������.
	 * @param defaultValue �������� �� ���������.
	 * @return �������� � long.
	 */
	protected long getLongProperty(String propertyName, long defaultValue)
	{
		return reader.getLongProperty(propertyName, defaultValue);
	}

	/**
	 * @param propertyName ��� ���������.
	 * @return �������� � int.
	 */
	protected int getIntProperty(String propertyName)
	{
		return reader.getIntProperty(propertyName);
	}

	/**
	 * @param propertyName ��� ���������.
	 * @param defaultValue �������� �� ���������
	 * @return �������� � int.
	 */
	protected int getIntProperty(String propertyName, int defaultValue)
	{
		return reader.getIntProperty(propertyName, defaultValue);
	}

	/**
	 * @param propertyName ��� ���������.
	 * @return �������� � float.
	 */
	protected float getFloatProperty(String propertyName)
	{
		return reader.getFloatProperty(propertyName);
	}

	/**
	 * @param propertyName ��� ���������.
	 * @param defaultValue �������� �� ���������
	 * @return �������� � float.
	 */
	protected float getFloatProperty(String propertyName, float defaultValue)
	{
		return reader.getFloatProperty(propertyName, defaultValue);
	}

	/**
	 * @param propertyName ��� ���������.
	 * @return �������� � double.
	 */
	protected double getDoubleProperty(String propertyName)
	{
		return reader.getDoubleProperty(propertyName);
	}

	/**
	 * @param propertyName ��� ���������.
	 * @param defaultValue �������� �� ���������
	 * @return �������� � double.
	 */
	protected double getFloatProperty(String propertyName, double defaultValue)
	{
		return reader.getDoubleProperty(propertyName, defaultValue);
	}

	/**
	 * @param propertyName ��� ���������.
	 * @return �������� � boolean.
	 */
	protected boolean getBoolProperty(String propertyName)
	{
		return reader.getBoolProperty(propertyName);
	}

	/**
	 * @param propertyName ��� ���������.
	 * @param defaultValue �������� �� ���������
	 * @return �������� � boolean.
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
	 * @param propertyKey ���� ���������.
	 * @return ������� �� ���������.
	 */
	protected boolean hasProperty(String propertyKey)
	{
		return reader.containProperty(propertyKey);
	}

	/**
	 * ������������� �������.
	 */
	void init()
	{
		doRefresh();
		lastUpdateTime = Calendar.getInstance().getTimeInMillis();
	}

	/**
	 * @return ���������� �� ��������� ������.
	 */
	boolean needRefresh()
	{
		if (isUpdating)
			return false;

		return reader.needUpdate(lastUpdateTime);
	}

	/**
	 * ��������� ������.
	 */
	protected abstract void doRefresh() throws ConfigurationException;

	protected PropertyReader getReader()
	{
		return reader;
	}
}
