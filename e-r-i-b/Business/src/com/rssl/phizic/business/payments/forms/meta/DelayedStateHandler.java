package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.payments.forms.meta.conditions.DelayedStateCondition;

/**
 * »спользуетс€ дл€ он-лайн платежей, которые не могут быть выполнены в не операционное врем€
 *  и не могут быть отложены
 * @author egorova
 * @ created 02.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class DelayedStateHandler extends BusinessDocumentHandlerBase
{
	protected static String DEFAULT_ERROR_MESSAGE = "ќпераци€ не может быть выполнена по техническим причинам. ѕожалуйста, повторите операцию позже.";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		try
		{

			DelayedStateCondition delayedStateCondition = new DelayedStateCondition();
			//≈сли не операционное врем€ ничего сделать не можем.
			if (delayedStateCondition.accepted(document, stateMachineEvent))
				throw new DocumentLogicException(getMessage(document));
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

	protected String getMessage(StateObject stateObject) throws DocumentException, DocumentLogicException
	{
		return DEFAULT_ERROR_MESSAGE;
	}
}
