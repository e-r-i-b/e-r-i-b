package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.extendedattributes.AttributableBase;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;

/**
 * Хендлер для обнуления признака о необохдимости проводить документ офлайн
 * @author mihaylov
 * @ created 30.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class ClearOfflineDelayedRowHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject stateObject, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(stateObject instanceof AttributableBase))
			throw new DocumentException("Передан некорректный объект. Ожидается AttributableBase.");

		((AttributableBase) stateObject).setAttributeValue(ExtendedAttribute.BOOLEAN_TYPE,BusinessDocumentBase.OFFLINE_DELAYED_ATTRIBUTE_NAME,null);
	}
}
