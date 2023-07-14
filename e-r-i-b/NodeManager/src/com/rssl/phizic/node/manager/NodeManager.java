package com.rssl.phizic.node.manager;

import com.rssl.auth.csa.utils.CSAResponseUtils;
import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.phizic.gate.Gate;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author osminin
 * @ created 06.10.14
 * @ $Author$
 * @ $Revision$
 */
public class NodeManager
{
	private static volatile NodeManager instance = null;

	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private final Lock readLock = rwl.readLock();
	private final Lock writeLock = rwl.writeLock();

	private Map<Long, Gate> gates;

	private NodeManager() throws GateException
	{
		try
		{
			List<NodeInfo> nodesInfo = CSAResponseUtils.getNodesInfo();
			gates = new HashMap<Long, Gate>(nodesInfo.size());
			for (NodeInfo nodeInfo : nodesInfo)
			{
				buildGate(nodeInfo);
			}
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * @return экзкмпляр менеджера.
	 * @throws GateException
	 */
	public static NodeManager getInstance() throws GateException
	{
		if (instance == null)
		{
			synchronized (NodeManager.class)
			{
				if (instance == null)
				{
					instance = new NodeManager();
				}
			}
		}
		return instance;
	}

	/**
	 * Получение сервиса по информации о блоке
	 * @param nodeNumber номер блока
	 * @param serviceInterface интерфейс сервиса
	 * @param <S> generic
	 * @return сервис
	 * @throws GateException
	 */
	public <S extends Service> S getService(Long nodeNumber, Class<S> serviceInterface) throws GateException
	{
		return getGate(nodeNumber).getFactory().service(serviceInterface);
	}

	private void registerGate(Long nodeNumber, Gate gate)
	{
		writeLock.lock();
		try
		{
			gates.put(nodeNumber, gate);
		}
		finally
		{
			writeLock.unlock();
		}
	}

	private Gate getGate(Long nodeNumber)
	{
		readLock.lock();
		try
		{
			return gates.get(nodeNumber);
		}
		finally
		{
			readLock.unlock();
		}
	}

	private void buildGate(NodeInfo nodeInfo) throws GateException
	{
		Gate gate = new GateImpl(nodeInfo);
		gate.initialize();
		registerGate(nodeInfo.getId(), gate);
	}
}
