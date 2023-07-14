package com.rssl.phizic.config;

import com.rssl.phizic.utils.StringHelper;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

/**
 * @author Evgrafov
 * @ created 25.07.2007
 * @ $Author$
 * @ $Revision$
 */

public abstract class PropertyReader implements PropertyGetter
{
	/**
	 * ��������� --- ������� ����������, ������� ������������ ��� ������������� �������� � ����� �� ��� ������ ����������.
	 */
	protected final String category;

	/**
	 * ������ ��������.
	 */
	private volatile Map<String, String> properties;

	/**
	 * ���������� ��� ����������.
	 */
	private final ReentrantLock LOCK = new ReentrantLock();

	/**
	 * ��� ���� ������� ��������� ������ �����������.
	 * ������ ����� �������� ������ � �������� ��� �� ConfigFactoryHelper.
	 *
	 * @param category �������� ��������� ��������.
	 */
	PropertyReader(String category)
	{
		this.category = category;
	}

	/**
	 * @return ���� � �����������.
	 */
	public abstract String getFileName();

	/**
	 * @param prefix ������� (���������, � ������� ���������� ����� �������)
	 * @return ������ �������
	 */
	public Properties getProperties(String prefix)
	{
		refreshIfNecessary();

		Properties result = new Properties();
		for (Map.Entry entry : properties.entrySet())
		{
			String key = (String) entry.getKey();
			if (!key.startsWith(prefix))
				continue;

			result.setProperty(key, (String) entry.getValue());
		}

		return result;
	}

	/**
	 * @return ��� ��������� ��������� �� ������
	 */
	public Properties getAllProperties()
	{
		refreshIfNecessary();

		Properties result = new Properties();
		for (Map.Entry entry : properties.entrySet())
		{
			result.setProperty((String) entry.getKey(), (String) entry.getValue());
		}

		return result;
	}

	/**
	 * ���������� �������� ���������, � ���� �������� ���, �� �������� �� ���������.
	 * @param <T> ������������� ��� ������.
	 */
	private abstract class DefaultValueResolver<T>
	{
		/**
		 * @param key ���������.
		 * @param defaultValue �������� �� ���������.
		 * @return �������� ���������.
		 */
		T getProperty(String key, T defaultValue)
		{
			String value = PropertyReader.this.getProperty(key);
			if (StringHelper.isEmpty(value))
				return defaultValue;
			return parse(value);
		}

		abstract T parse(String value);
	}

	private final DefaultValueResolver<Long> LONG_RESOLVER = new DefaultValueResolver<Long>()
	{
		@Override Long parse(String value) {return Long.parseLong(value);}
	};
	private final DefaultValueResolver<Integer> INTEGER_RESOLVER = new DefaultValueResolver<Integer>()
	{
		@Override Integer parse(String value) {return "null".equals(value) ? null : Integer.parseInt(value);}
	};
	private final DefaultValueResolver<Float> FLOAT_RESOLVER = new DefaultValueResolver<Float>()
	{
		@Override Float parse(String value) {return Float.parseFloat(value);}
	};
	private final DefaultValueResolver<Double> DOUBLE_RESOLVER = new DefaultValueResolver<Double>()
	{
		@Override Double parse(String value) {return Double.parseDouble(value);}
	};
	private final DefaultValueResolver<Boolean> BOOLEAN_RESOLVER = new DefaultValueResolver<Boolean>()
	{
		@Override Boolean parse(String value) {return Boolean.parseBoolean(value);}
	};
	private final DefaultValueResolver<Pattern> PATTERN_RESOLVER = new DefaultValueResolver<Pattern>()
	{
		@Override Pattern parse(String value) {return Pattern.compile(value);}
	};

	/**
	 * @param key ���������.
	 * @return ���������, ���� ��������� �� ������ ������������ ������ ������.
	 */
	public String getProperty(String key)
	{
		return getProperty(key, "");
	}

	/**
	 * @param propertyName ��� ���������.
	 * @return �������� � long.
	 */
	public long getLongProperty(String propertyName)
	{
		return getLongProperty(propertyName, 0L);
	}

	/**
	 * @param propertyName ��� ���������.
	 * @return �������� � int.
	 */
	public Integer getIntProperty(String propertyName)
	{
		return getIntProperty(propertyName, 0);
	}

	/**
	 * @param propertyName ��� ���������.
	 * @return �������� � float.
	 */
	public float getFloatProperty(String propertyName)
	{
		return getFloatProperty(propertyName, 0f);
	}

	/**
	 * @param propertyName ��� ���������.
	 * @return �������� � double.
	 */
	public double getDoubleProperty(String propertyName)
	{
		return getDoubleProperty(propertyName, 0);
	}

	/**
	 * @param propertyName ��� ���������.
	 * @return �������� � boolean.
	 */
	public boolean getBoolProperty(String propertyName)
	{
		return getBoolProperty(propertyName, false);
	}

	/**
	 * @param propertyName ��� ���������.
	 * @return �������.
	 */
	public Pattern getPatternProperty(String propertyName)
	{
		return getPatternProperty(propertyName, null);
	}

	/**
	 * @param key ���������.
	 * @param defaultValue �������� �� ���������.
	 * @return �������� ���������.
	 */
	public String getProperty(String key, String defaultValue)
	{
		refreshIfNecessary();

		String value = properties.get(key);
		if (value == null)
			return defaultValue;
		return value.trim();
	}

	/**
	 * @param propertyName ��� ���������.
	 * @return �������� � long.
	 */
	public long getLongProperty(String propertyName, long defaultValue)
	{
		return LONG_RESOLVER.getProperty(propertyName, defaultValue);
	}

	/**
	 * @param propertyName ��� ���������.
	 * @param defaultValue �������� �� ���������
	 * @return �������� � int.
	 */
	public Integer getIntProperty(String propertyName, Integer defaultValue)
	{
		return INTEGER_RESOLVER.getProperty(propertyName, defaultValue);
	}

	/**
	 * @param propertyName ��� ���������.
	 * @param defaultValue �������� �� ���������
	 * @return �������� � float.
	 */
	public float getFloatProperty(String propertyName, float defaultValue)
	{
		return FLOAT_RESOLVER.getProperty(propertyName, defaultValue);
	}

	/**
	 * @param propertyName ��� ���������.
	 * @param defaultValue �������� �� ���������
	 * @return �������� � float.
	 */
	public double getDoubleProperty(String propertyName, double defaultValue)
	{
		return DOUBLE_RESOLVER.getProperty(propertyName, defaultValue);
	}

	/**
	 * @param propertyName ��� ���������.
	 * @return �������� � boolean.
	 */
	public boolean getBoolProperty(String propertyName, boolean defaultValue)
	{
		return BOOLEAN_RESOLVER.getProperty(propertyName, defaultValue);
	}

	public Pattern getPatternProperty(String propertyName, Pattern defaultValue)
	{
		return PATTERN_RESOLVER.getProperty(propertyName, defaultValue);
	}

	/**
	 * @param key ���� ���������.
	 * @return ���������� �� ��������� � ��������� ������.
	 */
	protected boolean containProperty(String key)
	{
		refreshIfNecessary();
		return properties.containsKey(key);
	}

	/**
	 * ���������� ��������, ���� ��� ����������.
	 * �� ������������� ������ ����������� ��� ����� ������.
	 */
	protected final void refreshIfNecessary()
	{
		if (!needUpdateReader())
			return;

		LOCK.lock();
		try
		{
			if (!needUpdateReader())
				return;

			properties = doRefresh();
		}
		finally
		{
			LOCK.unlock();
		}
	}

	/**
	 * @param configLastUpdateTime ����� ���������� ���������� �������.
	 * @return ���������� �� ��������� ������.
	 */
	protected boolean needUpdate(long configLastUpdateTime)
	{
		return needUpdateReader();
	}

	/**
	 * @return ���������� �� ��������� �����.
	 */
	protected boolean needUpdateReader()
	{
		return properties == null;
	}

	/**
	 * ��������� ���������� ������.
	 */
	protected abstract Map<String, String> doRefresh();
}
