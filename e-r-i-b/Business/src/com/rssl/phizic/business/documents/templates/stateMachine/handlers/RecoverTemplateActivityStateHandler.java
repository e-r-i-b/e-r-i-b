package com.rssl.phizic.business.documents.templates.stateMachine.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.common.types.documents.templates.ActivityCode;

/**
 * Восстановление шаблона документа
 *
 * @author khudyakov
 * @ created 02.10.14
 * @ $Author$
 * @ $Revision$
 */
public class RecoverTemplateActivityStateHandler extends TemplateHandlerBase<TemplateDocument>
{
	public void process(TemplateDocument template, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		template.getTemplateInfo().setState(new State(ActivityCode.ACTIVE.name(), ActivityCode.ACTIVE.getDescription()));
	}
}
