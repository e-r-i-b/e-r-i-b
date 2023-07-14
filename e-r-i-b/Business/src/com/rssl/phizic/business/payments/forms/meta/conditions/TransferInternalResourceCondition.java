package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.InternalTransfer;
import com.rssl.phizic.business.statemachine.StateObjectCondition;

/**
 * ѕри редактировании ранее сохраненных шаблонов определенных платежей не нужно повторно подтверждать сохранение,
 * поэтому в state-machine используем этот condition дл€ пропуска шага подтверждени€.
 * »спользуетс€ дл€ шаблонов платежей "ѕеревод между своими счетами", "ќбмен валюты" и "ѕокупка/продажа драг. металлов".
 * @author Dorzhinov
 * @ created 10.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class TransferInternalResourceCondition implements StateObjectCondition
{
	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if(!(stateObject instanceof InternalTransfer))
			return false;

		String formName = ((InternalTransfer)stateObject).getFormName();
		if(!FormConstants.INTERNAL_PAYMENT_FORM.equals(formName)
				&& !FormConstants.CONVERT_CURRENCY_PAYMENT_FORM.equals(formName)
				&& !FormConstants.IMA_PAYMENT_FORM.equals(formName))
			return false;

		return true;
	}
}
