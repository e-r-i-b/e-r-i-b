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
 * ��������� ����� ���������
 */
public final class StateMachineContainer
{
	/**
	 * ���� "��� �����-������ -> �����-������" (read-only!)
	 */
	private final Map<String, StateMachine> stateMachines;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param collection - �������� �����-����� (never null nor empty)
	 */
	public StateMachineContainer(Collection<StateMachine> collection)
	{
		if (collection.isEmpty())
			throw new IllegalArgumentException("�� ������� �����-������");

		Map<String, StateMachine> map = new HashMap<String, StateMachine>(collection.size());
		for (StateMachine sm : collection)
			map.put(sm.getName(), sm);
		stateMachines = map;
	}

	/**
	 * ����� �����-������ �� � �����
	 * @param name - ��� �����-������
	 * @return �����-������ ��� null, ���� �� �������
	 */
	public StateMachine getStateMachine(String name)
	{
		return stateMachines.get(name);
	}
}
