package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;

/**
 * Всегда кидает исключение DocumentException с сообщением из параметра message
 * @author gladishev
 * @ created 21.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class RefuseHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		throw new DocumentException(getParameter("message"));
	}
}
