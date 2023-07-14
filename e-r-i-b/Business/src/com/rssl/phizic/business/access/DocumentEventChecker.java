package com.rssl.phizic.business.access;

import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.statemachine.Event;
import com.rssl.phizic.business.statemachine.MachineState;
import com.rssl.phizic.business.statemachine.StateMachine;
import com.rssl.phizic.business.statemachine.documents.StateMachineService;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

/**
 * Отрисовка комманд-кнопок переходов для объекта относительно машины состояний
 *
 * @author khudyakov
 * @ created 15.01.2009
 * @ $Author$
 * @ $Revision$
 */
public class DocumentEventChecker
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * Проверить возможность выполнения события кнопки
	 * @param stateObject объект
	 * @param key ключ (событи)
	 * @return true - событие разрешено
	 * @throws BusinessException
	 */
	public static boolean checkEvent(StateObject stateObject, String key) throws BusinessException
	{
		try
		{
			StateMachine machine = StateMachineService.getInstance().getByStateObject(stateObject);
			MachineState machineState = machine.getObjectMachineState(stateObject);

			if (machineState == null)
			{
				return false;
			}

			String eventType = getEventType();
			String nextState = key.substring(7, key.length()).toUpperCase();

			for (Event event : machineState.getEvents())
			{
				if (event.getType().equals(eventType) && event.getName().equals(nextState))
				{
					return true;
				}
			}
			return false;
		}
		catch (Exception e)
		{
			log.error("Ошибка определения события в state machine", e);
			return false;
		}
	}

	private static String getEventType()
	{
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
        Application application = applicationConfig.getLoginContextApplication();
		switch (application)
		{
			case PhizIC:
				return "client";
			case PhizIA:
				return "employee";
			case atm:
				return "client";
			default:
				if(applicationConfig.getApplicationInfo().isMobileApi())
					return "client";
				throw new IllegalStateException("Недопустимое приложение " + application);
		}
	}
}
