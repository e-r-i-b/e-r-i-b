package com.rssl.phizic.business.documents.templates.stateMachine.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.documents.templates.TemplateDocument;

/**
 * @ author: Vagin
 * @ created: 21.05.2013
 * @ $Author
 * @ $Revision
 * Хендлер очистки информации о подтверждении шаблона.
 */
public class ClearEmployeeConfirmInfoHandler extends TemplateHandlerBase<TemplateDocument>
{
	public void process(TemplateDocument template, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		template.setConfirmedEmployeeInfo(null);
		template.setAdditionalOperationChannel(null);
		template.setAdditionalOperationDate(null);
	}
}
