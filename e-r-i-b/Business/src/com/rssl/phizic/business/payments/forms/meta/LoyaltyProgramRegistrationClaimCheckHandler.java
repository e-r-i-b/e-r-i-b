package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.LoyaltyProgramLink;
import com.rssl.phizic.business.resources.external.LoyaltyProgramState;
import com.rssl.phizic.context.PersonContext;

/**
 * @author gladishev
 * @ created 14.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoyaltyProgramRegistrationClaimCheckHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		try
		{
			LoyaltyProgramLink loyaltyProgramLink = PersonContext.getPersonDataProvider().getPersonData().getLoyaltyProgram();
			if (loyaltyProgramLink != null && loyaltyProgramLink.getState() != LoyaltyProgramState.UNREGISTERED)
				throw new DocumentLogicException("Вы уже зарегистрированы в бонусной программе");
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
