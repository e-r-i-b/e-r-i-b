package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.DocumentOfflineException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.payments.forms.meta.conditions.OfflineDelayedCondition;
import com.rssl.phizic.common.types.exceptions.IKFLException;

/**
 * Хэндлер для фиксации факта недоступности внешних систем для проводки документа
 * @author Pankin
 * @ created 14.02.2013
 * @ $Author$
 * @ $Revision$
 */

public class CheckOfflineDelayedHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		boolean isOffline = false;
		try
		{
			isOffline = new OfflineDelayedCondition().accepted(document, stateMachineEvent);
		}
		catch (IKFLException e)
		{
			throw new DocumentException(e);
		}

		if (isOffline)
			throw new DocumentOfflineException("Документ не может быть обработан из-за недоступности внешних систем.");
	}
}
