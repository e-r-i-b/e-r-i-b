package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.loyaltyProgram.LoyaltyProgramHelper;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.logging.LogThreadContext;

/**
 * ѕри успешном исполнении за€вки на регистрацию в программе ло€льности заполн€ем соотв. LoyaltyProgramLink,
 * идентификатор сессии и тип стратегии подтверждени€
 * @author lukina
 * @ created 22.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class SetLoyaltyProgramLinkHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		BusinessDocument businessDocument = (BusinessDocument) document;
		businessDocument.setSessionId(LogThreadContext.getSessionId());
		businessDocument.setConfirmStrategyType(ConfirmStrategyType.none);
		try
		{
			LoyaltyProgramHelper.updateLoyaltyProgram();
		}
		catch (BusinessException e)
		{
			log.error(e.getMessage(), e);
		}
		catch (BusinessLogicException e)
		{
			log.error(e.getMessage(), e);
		}
	}
}
