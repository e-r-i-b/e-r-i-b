package com.rssl.phizic.business.documents.templates.stateMachine.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.documents.templates.TemplateDocument;


/**
 * @author vagin
 * @ created 11.04.14
 * @ $Author$
 * @ $Revision$
 * Устанавливает информацию о сотруднике создавшем шаблон.
 */
public class SetCreatedEmployeeInfoTemplateHandler extends EmployeeInfoTemplateHandlerBase<TemplateDocument>
{
	public void process(TemplateDocument template, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		template.setCreatedEmployeeInfo(getEmployeeInfo());
	}
}
