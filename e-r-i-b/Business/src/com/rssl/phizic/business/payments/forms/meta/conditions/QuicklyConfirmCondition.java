package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.phizic.business.statemachine.StateObjectCondition;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.state.StateObject;
import com.rssl.common.forms.doc.StateMachineEvent;

/**
 * Кондишен, проверяющий возможность перевода шаблона в статус подтвержден в КЦ
 *
 * @author khudyakov
 * @ created 07.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class QuicklyConfirmCondition implements StateObjectCondition
{
	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if (new TransferExternalResourceCondition().accepted(stateObject, stateMachineEvent))
		{
			if(!(stateObject instanceof BusinessDocument))
			{
				throw new BusinessException("Ожидался BusinessDocument");
			}

			BusinessDocument document = (BusinessDocument) stateObject;
			return document.getAdditionalOperationChannel() != null;
		}
		return false;
	}
}
