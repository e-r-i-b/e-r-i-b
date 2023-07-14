package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.payments.BusinessDocument;

/**
 * @author radionenko
 * @ created 17.01.14
 * @ $Author$
 * @ $Revision$
 * Хендлер сохраняет сообщение о сроке исполнения операции
 */

public class SetClientMessageKey extends BusinessDocumentHandlerBase<BusinessDocument>
{
	private static final String PARAMETER_NAME = "key";

	public void process(BusinessDocument document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		document.setEventMessageKey(getParameter(PARAMETER_NAME));
	}
}
