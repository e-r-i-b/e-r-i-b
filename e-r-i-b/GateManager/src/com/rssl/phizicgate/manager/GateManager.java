package com.rssl.phizicgate.manager;

import com.rssl.phizic.gate.Gate;
import com.rssl.phizic.gate.JAXRPCClientSideService;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizicgate.manager.builder.routable.GateImpl;
import com.rssl.phizicgate.manager.routing.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Krenev
 * @ created 20.04.2009
 * @ $Author$
 * @ $Revision$
 */
public class GateManager
{
	private Map<Node, Gate> gates = new HashMap<Node, Gate>();
	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private final Lock readLock = rwl.readLock();
	private final Lock writeLock = rwl.writeLock();
	private static volatile GateManager instance = null;

	private static final NodeAndAdapterRelationService relationService = new NodeAndAdapterRelationService();
	private static final AdapterService adapterService = new AdapterService();
	private static final NodeService nodeService = new NodeService();

	private GateManager() throws GateException
	{
		List<Node> nodes = nodeService.getAllNodes();
		for (Node node : nodes)
		{
			buildGate(node);
		}
	}

	/**
	 * @return экзкмпляр менеджера.
	 */
	public static GateManager getInstance() throws GateException
	{
		if (instance == null)
		{
			synchronized (GateManager.class)
			{
				if (instance == null)
				{
					instance = new GateManager();
				}
			}
		}
		return instance;
	}

	/**
	 * Создать и загеристрировать гейт, соотетсвующий внешней системе
	 * @param node
	 */
	public Gate buildGate(Node node) throws GateException
	{
		Gate gate = new GateImpl(node);
		gate.initialize();
		registerGate(node, gate);
		return gate;
	}

	/**
	 * Убрать гейт из системы, соответсвующий внешней системе.
	 * @param node
	 */
	public void dropGate(Node node) throws GateException
	{
		writeLock.lock();
		try
		{
			gates.remove(node);
		}
		finally
		{
			writeLock.unlock();
		}
	}

	/**
	 * Получение гейта, соответствующего заданному узлу
	 * @param node
	 * @return
	 */
	public Gate getGate(Node node)
	{
		readLock.lock();
		try
		{
			return gates.get(node);
		}
		finally
		{
			readLock.unlock();
		}
	}

	/**
	 * Получение гейта по информации о маршрутизации
	 * @param info
	 * @return
	 * @throws GateException
	 */
	public Gate getGate(String info) throws GateException
	{
		Node node = getNode(info);
		readLock.lock();
		try
		{
			return gates.get(node);
		}
		finally
		{
			readLock.unlock();
		}
	}

	/**
	 * Регистрация гейта
	 * @param node
	 * @param gate
	 */
	public void registerGate(Node node, Gate gate)
	{
		writeLock.lock();
		try
		{
			gates.put(node, gate);
		}
		finally
		{
			writeLock.unlock();
		}
	}

	/**
	 * Получение сервиса по информации о маршрутизации
	 * @param info информация о маршрутизации
	 * @param serviceInterface
	 * @param <S>
	 * @return
	 * @throws GateException
	 */
	public <S extends Service> S getService(String info, Class<S> serviceInterface) throws GateException
	{
		return getGate(getNode(info)).getFactory().service(serviceInterface);
	}

	private Node getNode(String info) throws GateException
	{
		//TODO для схемы звезда
		Adapter adapter = adapterService.getAdapterByUUID(info);
		Node node = relationService.getNodes(adapter).get(0);
		if (node == null)
			throw new GateException("не удалось найти узел, соответствующий заданному адаптеру " + info);

		return node;
	}

	/**
	 * @param timeout - таймаут
	 * Обновление стабов
	 */
	public void updateStubs(int timeout)
	{
		for (Gate gate : gates.values())
		{
			for (Service service : gate.getFactory().services())
			{
				if (service instanceof JAXRPCClientSideService)
					((JAXRPCClientSideService) service).updateStub(timeout);
			}
		}
	}
}
