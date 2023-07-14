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
 * Менеджер движков
 * Содержит коллекцию механизмов модуля
 */
public class EngineManager
{
	private final Module module;

	private final Map<LoadOrder, Engine> engines = new EnumMap<LoadOrder, Engine>(LoadOrder.class);

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param module - модуль, к которому относится менеджер
	 */
	public EngineManager(Module module)
	{
		this.module = module;
	}

	/**
	 * @return модуль
	 */
	public Module getModule()
	{
		return module;
	}

	/**
	 * Добавить движок
	 * @param engine - движок
	 */
	public void addEngine(Engine engine)
	{
		LoadOrder loadOrder = engine.getLoadOrder();
		if (engines.containsKey(loadOrder))
			throw new IllegalArgumentException("Движок с порядковым номером " + loadOrder + " уже добавлен");
		engines.put(loadOrder, engine);
	}

	/**
	 * Возвращает все движки
	 * @return перечень движков в порядке загрузки
	 */
	public Collection<Engine> getEngines()
	{
		return engines.values();
	}
}
