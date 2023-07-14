package com.rssl.phizicgate.manager;

import com.rssl.phizic.gate.Gate;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.config.RefreshGateConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizicgate.manager.builder.endService.GateImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ���� �������� ��� �������� ��������� �������.
 *
 * @author bogdanov
 * @ created 28.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class PersistentGateManager
{
	private Map<String, Gate> gates = new HashMap<String, Gate>();
	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private final Lock readLock = rwl.readLock();
	private final Lock writeLock = rwl.writeLock();
	private static volatile PersistentGateManager instance = null;

	private PersistentGateManager() {}

	/**
	 * @return ��������� ���������.
	 */
	public static PersistentGateManager getInstance() throws GateException
	{
		if (instance == null)
		{
			synchronized (GateManager.class)
			{
				if (instance == null)
				{
					instance = new PersistentGateManager();
				}
			}
		}
		return instance;
	}

	/**
	 * ������ ���� �� �������, �������������� �������� ���� ��������.
	 * @param config ������������ �����.
	 */
	public void dropGate(RefreshGateConfig config) throws GateException
	{
		writeLock.lock();
		try
		{
			gates.remove(config.getRouteCode());
		}
		finally
		{
			writeLock.unlock();
		}
	}

	/**
	 * ��������� �����, ���������������� ��������� ���� ��������.
	 * @param routeInfo ���������� �����.
	 * @return ����, ��������������� ������� ���� ��������.
	 */
	public Gate getGate(String routeInfo) throws GateException
	{
		readLock.lock();
		try
		{
			Gate gate = gates.get(routeInfo);
			if (gate != null)
				return gate;
		}
		finally
		{
			readLock.unlock();
		}

		writeLock.lock();
		try
		{
			Gate gate = gates.get(routeInfo);
			if (gate != null)
				return gate;

			RefreshGateConfig config = new RefreshGateConfig(routeInfo);
			gate = new GateImpl(config);
			gate.initialize();
			gates.put(routeInfo, gate);
			return gate;
		}
		finally
		{
			writeLock.unlock();
		}
	}

	/**
	 * ��������� ������� �� ���������� � �������������
	 * @param info ���������� � �������������
	 * @param serviceInterface ��� �������.
	 * @return ������, � �������� �����.
	 * @throws GateException
	 */
	public <S extends Service> S getService(String info, Class<S> serviceInterface) throws GateException
	{
		return getGate(info).getFactory().service(serviceInterface);
	}
}
