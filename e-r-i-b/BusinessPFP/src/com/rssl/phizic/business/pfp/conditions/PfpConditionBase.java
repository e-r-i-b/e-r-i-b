package com.rssl.phizic.business.pfp.conditions;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.business.statemachine.StateObjectCondition;

/**
 * @author komarov
 * @ created 08.08.13 
 * @ $Author$
 * @ $Revision$
 */

public abstract class PfpConditionBase implements StateObjectCondition
{
	/**
	 * проверяет условие перехода на состояние
	 * @param stateObject - объект, для которого дет проверка
	 * @param stateMachineEvent объект хранит сообщения которые неоюходимо вывести пользователю и список
	 * изменившихся полей
	 * @return true если выполнилось условие, false если нет
	 */
	public abstract boolean accepted(PersonalFinanceProfile stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException;

	public final boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if(!(stateObject instanceof PersonalFinanceProfile))
					throw new BusinessException("Неверный тип объекта стейт машины. Должен быть PersonalFinanceProfile");

		return accepted((PersonalFinanceProfile)stateObject, stateMachineEvent);
	}
}
