package com.rssl.common.forms.state;

import com.rssl.phizic.common.types.documents.State;

/**
 * @author Omeliyanchuk
 * @ created 23.06.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * Интерфейс для объектов участвующих в механизме состояний
 */
public interface StateObject
{
	/**
	 * @return идентификатор объекта машины состояний
	 */
	Long getId();

	/**
	 * Получить информацию о машине состояний
	 * @return информация о машине состояний
	 */
	StateMachineInfo getStateMachineInfo();

	/**
	 * Получить текущее состояние
	 * @return текущее состояние
	 */
	State getState();

	/**
	 * изменить текущее состояние
	 * @param state новое состояние
	 */
	void setState(State state);

	/**
	 * Устанавливает имя системы в которой находится документ
	 * @param name имя системы
	 */
	void setSystemName(String name);

	/**
	 * Получить следующее состояние
	 * @return следующее состояние
	 */
	String getNextState();
}
