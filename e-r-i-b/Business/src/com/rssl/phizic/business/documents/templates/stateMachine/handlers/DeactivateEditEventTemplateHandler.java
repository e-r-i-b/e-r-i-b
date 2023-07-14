package com.rssl.phizic.business.documents.templates.stateMachine.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.documents.templates.attributes.Attributable;

/**
 * Хендлер деактивирования режима редактирования шаблона документа
 *
 * @author khudyakov
 * @ created 30.07.14
 * @ $Author$
 * @ $Revision$
 */
public class DeactivateEditEventTemplateHandler extends TemplateHandlerBase<Attributable>
{
	public void process(Attributable attributable, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		attributable.removeAttribute(Constants.EDIT_EVENT_ATTRIBUTE_NAME);
	}
}
