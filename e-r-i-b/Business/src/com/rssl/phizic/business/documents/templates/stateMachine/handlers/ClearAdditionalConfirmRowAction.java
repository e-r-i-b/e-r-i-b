package com.rssl.phizic.business.documents.templates.stateMachine.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.documents.templates.TemplateDocument;

/**
 * Хендлер удаляет поле о дополнительном подтерждении в КЦ или УС.
 * @author: vagin
 * @ created: 06.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class ClearAdditionalConfirmRowAction extends TemplateHandlerBase<TemplateDocument>
{
	public void process(TemplateDocument template, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		template.setAdditionalOperationChannel(null);
	}
}
