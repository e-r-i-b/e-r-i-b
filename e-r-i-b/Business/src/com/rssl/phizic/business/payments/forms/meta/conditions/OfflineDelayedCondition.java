package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.extendedattributes.AttributableBase;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.business.statemachine.StateObjectCondition;

/**
 * Условие для фиксации недоступности внешних систем
 * @author Pankin
 * @ created 16.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class OfflineDelayedCondition implements StateObjectCondition
{
	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if (!(stateObject instanceof AttributableBase))
			throw new BusinessException("Передан некорректный объект. Ожидается AttributableBase.");

		ExtendedAttribute offlineAttribute = ((AttributableBase) stateObject).getAttribute(BusinessDocumentBase.OFFLINE_DELAYED_ATTRIBUTE_NAME);
		if (offlineAttribute != null && (Boolean) offlineAttribute.getValue())
			return true;
		return false;
	}
}
