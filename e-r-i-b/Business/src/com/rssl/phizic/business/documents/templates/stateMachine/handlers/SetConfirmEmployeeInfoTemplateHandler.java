package com.rssl.phizic.business.documents.templates.stateMachine.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.documents.templates.TemplateDocument;

import java.util.Calendar;

/**
 * Хендлер шаблона документа (устанавливаем информацию о сотруднике, подтвердившем шаблон)
 *
 * @author khudyakov
 * @ created 10.02.14
 * @ $Author$
 * @ $Revision$
 */
public class SetConfirmEmployeeInfoTemplateHandler extends EmployeeInfoTemplateHandlerBase<TemplateDocument>
{
	public void process(TemplateDocument template, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		template.setConfirmedEmployeeInfo(getEmployeeInfo());
		template.setAdditionalOperationDate(Calendar.getInstance());
		template.setAdditionalOperationChannel(CreationType.internet);     //всегда internet
	}
}
