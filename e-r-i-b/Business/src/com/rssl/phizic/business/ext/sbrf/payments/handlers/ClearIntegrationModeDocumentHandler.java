package com.rssl.phizic.business.ext.sbrf.payments.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocument;

/**
 * @author akrenev
 * @ created 17.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Хендлер, очищающий информацию о режиме интеграции для документа
 */

public class ClearIntegrationModeDocumentHandler extends BusinessDocumentHandlerBase<BusinessDocument>
{
	public void process(BusinessDocument document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof GateExecutableDocument))
			return;

		GateExecutableDocument gateExecutableDocument = (GateExecutableDocument) document;
		gateExecutableDocument.setIntegrationMode(null);
	}
}
