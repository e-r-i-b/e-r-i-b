package com.rssl.phizic.business.statemachine;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Erkin
 * @ created 19.04.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 *  онтейнер машин состо€ний
 */
public final class StateMachineContainer
{
	/**
	 * ћапа "им€ стейт-машины -> стейт-машина" (read-only!)
	 */
	private final Map<String, StateMachine> stateMachines;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param collection - перечень стейт-машин (never null nor empty)
	 */
	public StateMachineContainer(Collection<StateMachine> collection)
	{
		if (collection.isEmpty())
			throw new IllegalArgumentException("Ќе указаны стейт-машины");

		Map<String, StateMachine> map = new HashMap<String, StateMachine>(collection.size());
		for (StateMachine sm : collection)
			map.put(sm.getName(), sm);
		stateMachines = map;
	}

	/**
	 * Ќайти стейт-машину по еЄ имени
	 * @param name - им€ стейт-машины
	 * @return стейт-машина или null, если не найдена
	 */
	public StateMachine getStateMachine(String name)
	{
		return stateMachines.get(name);
	}
}
