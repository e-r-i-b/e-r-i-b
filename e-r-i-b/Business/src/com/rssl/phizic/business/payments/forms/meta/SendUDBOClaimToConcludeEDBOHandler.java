package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.claims.sbnkd.ConcludeEDBODocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.esberibgate.claim.sbnkd.ConcludeEDBODocumentSender;

/**
 * @author basharin
 * @ created 19.12.14
 * @ $Author$
 * @ $Revision$
 */

public class SendUDBOClaimToConcludeEDBOHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		try
		{
			if (document instanceof ConcludeEDBODocument)
				new ConcludeEDBODocumentSender(GateSingleton.getFactory()).send((ConcludeEDBODocument) document);
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
		catch (GateLogicException e)
		{
			throw new DocumentLogicException(e);
		}
	}
}
