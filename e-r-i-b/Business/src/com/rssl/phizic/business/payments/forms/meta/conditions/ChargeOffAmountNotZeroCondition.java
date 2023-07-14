package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.FieldValueChange;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.statemachine.StateObjectCondition;
import com.rssl.phizic.common.types.Money;

/**
 * User: Balovtsev
 * Date: 10.01.2012
 * Time: 8:05:58
 *
 * ѕровер€ет значение суммы списани€ при закрытии вклада с нулевым остатком
 */
public class ChargeOffAmountNotZeroCondition implements StateObjectCondition
{
	protected static final String MESSAGE_HAS_MONEY_ON_ACCOUNT = "Ќа этом вкладе остались денежные средства. ѕожалуйста, выберите счет или карту, на которую хотите перевести остаток средств.";

	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if (stateMachineEvent.hasChangedFields())
		{
			FieldValueChange chargeOffAmount = stateMachineEvent.getChangedField("chargeOffAmount");

			/*
			 * ≈сли сумма вообще не мен€лась уходим.
			 */
			if (chargeOffAmount == null)
			{
				return false;
			}
			                                                                    
			/*
			 * ≈сли сумма мен€лась, смотрим какой вклад закрываем нулевой или нет.
			 * ≈сли изменилась идем на страницу редактировани€
			 */
			if (((Money) chargeOffAmount.getOldValue()).isZero())
			{
				/*
				 * остаток был нулевым, а стал ненулевым
                 */
				stateMachineEvent.addMessage(MESSAGE_HAS_MONEY_ON_ACCOUNT);
				return true;
			}
		}
		
		return false;
	}
}
