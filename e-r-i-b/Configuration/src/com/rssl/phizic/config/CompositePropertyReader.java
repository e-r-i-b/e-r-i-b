package com.rssl.phizic.config;

import com.rssl.phizic.utils.StringHelper;

import java.util.*;

/**
 * Композитный ридер.
 *
 * @author bogdanov
 * @ created 24.12.13
 * @ $Author$
 * @ $Revision$
 */

public class CompositePropertyReader extends PropertyReader
{
	private final LinkedList<PropertyReader> readers;
	private long refreshEvery;

	public CompositePropertyReader(PropertyReader ... readers)
	{
		super(null);
		this.readers = new LinkedList<PropertyReader>();
		for (PropertyReader reader : readers)
		    if (reader instanceof DbPropertyReader)
			    this.readers.addFirst(reader);
			else
			    this.readers.addLast(reader);
	}

	@Override
	public Properties getAllProperties()
	{
		Properties prps = new Properties();
		for (ListIterator<PropertyReader> it = readers.listIterator(readers.size()); it.hasPrevious();)
			prps.putAll(it.previous().getAllProperties());

		return prps;
	}

	@Override
	public Properties getProperties(String prefix)
	{
		Properties prps = new Properties();

		for (ListIterator<PropertyReader> it = readers.listIterator(readers.size()); it.hasPrevious();)
			prps.putAll(it.previous().getProperties(prefix));

		return prps;
	}

	@Override
	public String getProperty(String key, String defaultValue)
	{
		for (ListIterator<PropertyReader> it = readers.listIterator(); it.hasNext();)
		{
			String value = it.next().getProperty(key);
			if (!StringHelper.isEmpty(value))
				return value;
		}

		return defaultValue;
	}

	protected boolean containProperty(String key)
	{
		for (ListIterator<PropertyReader> it = readers.listIterator(); it.hasNext();)
		{
			if (it.next().containProperty(key))
				return true;
		}

		return false;
	}

	@Override
	protected Map<String, String> doRefresh()
	{
		for (ListIterator<PropertyReader> it = readers.listIterator(); it.hasNext();)
			it.next().doRefresh();

		return new HashMap<String, String>();
	}

	/**
	 * @param configLastUpdateTime время последнего обновления конфига.
	 * @return необходимо ли обновлять конфиг.
	 */
	protected boolean needUpdate(long configLastUpdateTime)
	{
		for (ListIterator<PropertyReader> it = readers.listIterator(); it.hasNext();)
			if (it.next().needUpdate(configLastUpdateTime))
				return true;

		return false;
	}

	@Override
	protected boolean needUpdateReader()
	{
		for (ListIterator<PropertyReader> it = readers.listIterator(); it.hasNext();)
			if (it.next().needUpdateReader())
				return true;

		return false;
	}

	@Override
	public String getFileName()
	{
		for (ListIterator<PropertyReader> it = readers.listIterator(); it.hasNext();)
		{
			PropertyReader reader = it.next();
			if (reader.getClass() == ResourcePropertyReader.class)
				return reader.getFileName();
		}

		return null;
	}

	public long getRefreshEvery()
	{
		return refreshEvery;
	}

	public void setRefreshEvery(long refreshEvery)
	{
		this.refreshEvery = refreshEvery;
	}

	public List<PropertyReader> getReaders()
	{
		return Collections.unmodifiableList(readers);
	}
}
