package com.rssl.phizic.business.payments.forms.meta.creditlimit;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.ikfl.crediting.FeedbackType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.ChangeCreditLimitClaim;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.statemachine.StateObjectCondition;

/**
 * Согласие с  измененем кредитного лимита в основном приложении.
 *
 * @author bogdanov
 * @ created 29.06.15
 * @ $Author$
 * @ $Revision$
 */

public class AcceptChangeCreditLimitCondition implements StateObjectCondition<BusinessDocument>
{

	public boolean accepted(BusinessDocument stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if (!(stateObject instanceof ChangeCreditLimitClaim))
			return false;

		ChangeCreditLimitClaim changeCreditLimitClaim = (ChangeCreditLimitClaim) stateObject;
		return changeCreditLimitClaim.getCreationType() == CreationType.internet && FeedbackType.ACCEPT.name().equals(changeCreditLimitClaim.getFeedbackType());
	}
}
