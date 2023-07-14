package com.rssl.phizic.dataaccess.hibernate;

import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.engine.EngineBase;
import com.rssl.phizic.engine.EngineManager;
import com.rssl.phizic.module.loader.LoadOrder;
import org.hibernate.SessionFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Erkin
 * @ created 10.01.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Реализация движка хибернейта
 */
public class HibernateEngineImpl extends EngineBase implements HibernateEngine
{
	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(true);
	private final Lock readLock = rwl.readLock();
	private final Lock writeLock = rwl.writeLock();

	/**
	 * Фабрики сессий
	 * Мапа "имя_инстанса -> фабрика"
	 */
	private final Map<String, SessionFactory> sessionFactories = new HashMap<String, SessionFactory>();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param manager - менеджер движков
	 */
	public HibernateEngineImpl(EngineManager manager)
	{
		super(manager);
	}

	public LoadOrder getLoadOrder()
	{
		return LoadOrder.HIBERNATE_ENGINE_ORDER;
	}

	public void start()
	{
	}

	public void stop()
	{
	}

	public void addSessionFactory(String instanceName, SessionFactory factory)
	{
		if (StringHelper.isEmpty(instanceName))
			throw new IllegalArgumentException("Аргумент 'instanceName' не может быть пустым");
		if (factory == null)
			throw new IllegalArgumentException("Аргумент 'factory' не может быть null");

		String name = adaptInstanceName(instanceName);
		writeLock.lock();
		try
		{
			if (sessionFactories.containsKey(name))
				throw new IllegalStateException("Хибернейт фабрика с именем " + instanceName + " уже зарегистрирована");
			sessionFactories.put(name, factory);
		}
		finally
		{
			writeLock.unlock();
		}
	}

	public SessionFactory getSessionFactory(String instanceName)
	{
		if (StringHelper.isEmpty(instanceName))
			throw new IllegalArgumentException("Аргумент 'instanceName' не может быть пустым");

		String name = adaptInstanceName(instanceName);
		readLock.lock();
		try
		{
			return sessionFactories.get(name);
		}
		finally
		{
			readLock.unlock();
		}
	}

	public void removeSessionFactory(String instanceName)
	{
		if (StringHelper.isEmpty(instanceName))
			throw new IllegalArgumentException("Аргумент 'instanceName' не может быть пустым");

		String name = adaptInstanceName(instanceName);
		writeLock.lock();
		try
		{
			sessionFactories.remove(name);
		}
		finally
		{
			writeLock.unlock();
		}
	}

	private String adaptInstanceName(String instanceName)
	{
		return instanceName.toUpperCase();
	}
}
