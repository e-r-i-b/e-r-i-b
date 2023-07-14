package com.rssl.phizic.business.statemachine;

import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;

/**
 * @author Erkin
 * @ created 11.07.2014
 * @ $Author$
 * @ $Revision$
 */
public abstract class StateMachineService
{
	@SuppressWarnings("ProtectedField")
	protected final Log log = PhizICLogFactory.getLog(LogModule.Core);

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Возвращает готовый к использованию контейнер стейт-машин
	 * @return контейнер стейт-машин (never null)
	 */
	protected abstract StateMachineContainer getContainer();

	protected String getServiceName()
	{
		return getClass().getSimpleName();
	}

	/**
	 * Найти машину состояний по её имени
	 * @param stateMachineName - имя машины состояний (never null nor empty)
	 * @return машина состояний (never null)
	 * @throws IllegalStateException - служба ещё не запущена
	 * @throws IllegalArgumentException - не найдена машина состояний с указанным именем
	 */
	public StateMachine getStateMachineByName(String stateMachineName)
	{
		StateMachineContainer container = getContainer();
		StateMachine stateMachine = container.getStateMachine(stateMachineName);
		if (stateMachine == null)
			throw new IllegalArgumentException("Не найдена машина состояний " + stateMachineName + " в службе " + getServiceName());

		return stateMachine;
	}

	/**
	 * Получение машины состояний по объекту
	 * @param object - объект
	 * @return - машина состояний (never null)
	 * @throws IllegalStateException - служба ещё не запущена
	 * @throws IllegalArgumentException - не найдена машина состояний с указанным именем
	 */
	public StateMachine getStateMachineByObject(StateObject object)
	{
		return getStateMachineByName(object.getStateMachineInfo().getName());
	}
}
