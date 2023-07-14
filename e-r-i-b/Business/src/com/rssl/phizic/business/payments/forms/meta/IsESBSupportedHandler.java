package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.ConvertableToGateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;

/**
 * @author egorova
 * @ created 11.04.2011
 * @ $Author$
 * @ $Revision$
 */

public class IsESBSupportedHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject stateObject, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		try
		{
			if (!ESBHelper.isESBSupported(((ConvertableToGateDocument) stateObject).asGateDocument()))
			{
				MessageCreatorHelper messageHelper = new MessageCreatorHelper();
				messageHelper.setParameter(MessageCreatorHelper.NEED_SPECAIL_KEY_FOR_TEXT_ERROR, getParameter(MessageCreatorHelper.NEED_SPECAIL_KEY_FOR_TEXT_ERROR));
				throw messageHelper.buildException(stateObject, this.getClass());
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
	}
}
