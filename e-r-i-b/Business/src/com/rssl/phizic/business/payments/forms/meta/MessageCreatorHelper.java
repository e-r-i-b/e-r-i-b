package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.DocumentLogicMessageException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author egorova
 * @ created 11.04.2011
 * @ $Author$
 * @ $Revision$
 */

public class MessageCreatorHelper extends BusinessDocumentHandlerBase
{
	public static final String NEED_SPECAIL_KEY_FOR_TEXT_ERROR = "needSpecialKeyForTextError";
	private static final String MESSAGE_ERROR_BUNDLE = "paymentsBundle";

	private String buildMessageKey(GateExecutableDocument document, String className)
	{
		String textError = className;
		Class<? extends GateDocument> type = document.asGateDocument().getType();
		if (!StringHelper.isEmpty(getParameter(NEED_SPECAIL_KEY_FOR_TEXT_ERROR)) && compareList(getParameter(NEED_SPECAIL_KEY_FOR_TEXT_ERROR), type.getName()))
			textError += "." + type.getName();
		textError += ".message";
		return textError;
	}

	public DocumentLogicException buildException(StateObject stateObject, Class clazz)
	{
		return new DocumentLogicMessageException(buildMessageKey((GateExecutableDocument) stateObject, clazz.getName()), MESSAGE_ERROR_BUNDLE);
	}

	public void process(StateObject stateObject, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		throw new UnsupportedOperationException("Не поддерживается");
	}
}
