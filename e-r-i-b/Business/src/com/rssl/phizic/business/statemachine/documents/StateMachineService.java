package com.rssl.phizic.business.statemachine.documents;

import com.rssl.common.forms.state.StateMachineInfo;
import com.rssl.common.forms.state.StateObject;
import com.rssl.common.forms.state.Type;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachine;
import com.rssl.phizic.business.statemachine.documents.pfp.PFPStateMachineService;
import com.rssl.phizic.business.statemachine.documents.templates.TemplateStateMachineService;

import java.util.HashMap;
import java.util.Map;

/**
 * Сервис получения машины состояний
 *
 * @author khudyakov
 * @ created 31.07.14
 * @ $Author$
 * @ $Revision$
 */
public class StateMachineService
{
	private static final StateMachineService INSTANCE = new StateMachineService();


	private static final Map<Type, com.rssl.phizic.business.statemachine.StateMachineService> services =
										new HashMap<Type, com.rssl.phizic.business.statemachine.StateMachineService>();
	static
	{
		services.put(Type.PAYMENT_DOCUMENT,     new PaymentStateMachineService());
		services.put(Type.TEMPLATE_DOCUMENT,    new TemplateStateMachineService());
		services.put(Type.PFP_DOCUMENT,         new PFPStateMachineService());
	}

	public static StateMachineService getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Вернуть машину состояний по объекту
	 * @param stateObject объект
	 * @return машина состояний
	 */
	public StateMachine getByStateObject(StateObject stateObject)
	{
		return getByStateMachineInfo(stateObject.getStateMachineInfo());
	}

	/**
	 * Вернуть машину состояний по информации о машине состояний
	 * @param machineInfo информация о машине состояний
	 * @return машина состояний
	 */
	public StateMachine getByStateMachineInfo(StateMachineInfo machineInfo)
	{
		return services.get(machineInfo.getType()).getStateMachineByName(machineInfo.getName());
	}
}
