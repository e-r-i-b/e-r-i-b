package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.state.StateObject;
import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.payments.forms.meta.conditions.OfflineDelayedCondition;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

/**
 * ’эндлер дл€ подготовки платежей, учитывающий возможную оплату при недоступных внешних системах
 * @author Pankin
 * @ created 16.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class PrepareDocumentOfflineSupportedHandler extends PrepareDocumentHandler
{

	protected boolean isOfflineDelayedSystem(BusinessDocument document, StateMachineEvent stateMachineEvent)
			throws DocumentException, DocumentLogicException
	{
		try
		{
			return new OfflineDelayedCondition().accepted(document, stateMachineEvent);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e);
		}
	}
}
