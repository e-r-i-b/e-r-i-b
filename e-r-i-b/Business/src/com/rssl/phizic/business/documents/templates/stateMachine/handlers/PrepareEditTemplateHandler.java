package com.rssl.phizic.business.documents.templates.stateMachine.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.documents.templates.TemplateDocument;

/**
 * Хендлер подготовки шаблона к редактированию
 *
 * @author khudyakov
 * @ created 10.02.14
 * @ $Author$
 * @ $Revision$
 */
public class PrepareEditTemplateHandler extends TemplateHandlerBase<TemplateDocument>
{
	public void process(TemplateDocument template, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		//удаляем информацию по комиссиям
		template.setCommission(null);

		//удяляем внешний идентификатор
		template.setExternalId(null);
	}
}
