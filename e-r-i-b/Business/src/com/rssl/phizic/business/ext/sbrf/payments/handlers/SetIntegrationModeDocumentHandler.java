package com.rssl.phizic.business.ext.sbrf.payments.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.gate.config.ExternalSystemIntegrationMode;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizicgate.manager.services.selectors.way.WaySelectorHelper;

/**
 * @author akrenev
 * @ created 17.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Хендлер, устанавливающий режим интеграции для документа
 */

public class SetIntegrationModeDocumentHandler extends BusinessDocumentHandlerBase<BusinessDocument>
{
	public void process(BusinessDocument document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof GateExecutableDocument))
			return;

		try
		{
			GateExecutableDocument gateExecutableDocument = (GateExecutableDocument) document;
			ExternalSystemIntegrationMode mode = WaySelectorHelper.getIntegrationMode(gateExecutableDocument, WaySelectorHelper.DOCUMENT_SENDER);
			gateExecutableDocument.setIntegrationMode(mode);
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
	}
}
