package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.DocumentOfflineException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.payments.forms.meta.conditions.DelayedStateCondition;

/**
 * Используется для он-лайн платежей, которые не могут быть выполнены во внеоперационное время
 *  и не могут быть отложены
 * @author sergunin
 * @ created 21.02.2014
 * @ $Author$
 * @ $Revision$
 */

public class JobDelayedStateHandler extends BusinessDocumentHandlerBase
{

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		try
		{

			DelayedStateCondition delayedStateCondition = new DelayedStateCondition();
			//Если не операционное время останавливаем обработку документа.
			if (delayedStateCondition.accepted(document, stateMachineEvent))
				throw new DocumentOfflineException("Документ не может быть выполнены во внеоперационное время.");
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
