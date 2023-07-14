package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.payments.ConvertableToGateDocument;
import com.rssl.phizic.business.extendedattributes.AttributableBase;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.DocumentService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;

/**
 * @author Gainanov
 * @ created 27.02.2009
 * @ $Author$
 * @ $Revision$
 */
public class DefaultGateValidationHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof ConvertableToGateDocument))
			throw new DocumentException("document должен реализовывать ConvertableToGateDocument");

		ExtendedAttribute offlineAttribute = ((AttributableBase) document).getAttribute(BusinessDocumentBase.OFFLINE_DELAYED_ATTRIBUTE_NAME);
		if (offlineAttribute != null && (Boolean) offlineAttribute.getValue())
			return;

		ConvertableToGateDocument convertable = (ConvertableToGateDocument) document;

		DocumentService documentService = GateSingleton.getFactory().service(DocumentService.class);
		try
		{
			documentService.validate(convertable.asGateDocument());
		}
		catch (TemporalGateException e)
		{
			throw new TemporalDocumentException(e);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
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
