package com.rssl.phizic.engine;

import com.rssl.phizic.module.Module;
import com.rssl.phizic.module.loader.LoadOrder;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

/**
 * @author Erkin
 * @ created 16.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� �������
 * �������� ��������� ���������� ������
 */
public class EngineManager
{
	private final Module module;

	private final Map<LoadOrder, Engine> engines = new EnumMap<LoadOrder, Engine>(LoadOrder.class);

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param module - ������, � �������� ��������� ��������
	 */
	public EngineManager(Module module)
	{
		this.module = module;
	}

	/**
	 * @return ������
	 */
	public Module getModule()
	{
		return module;
	}

	/**
	 * �������� ������
	 * @param engine - ������
	 */
	public void addEngine(Engine engine)
	{
		LoadOrder loadOrder = engine.getLoadOrder();
		if (engines.containsKey(loadOrder))
			throw new IllegalArgumentException("������ � ���������� ������� " + loadOrder + " ��� ��������");
		engines.put(loadOrder, engine);
	}

	/**
	 * ���������� ��� ������
	 * @return �������� ������� � ������� ��������
	 */
	public Collection<Engine> getEngines()
	{
		return engines.values();
	}
}
