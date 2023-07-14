package com.rssl.phizic.business.documents.templates.stateMachine.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.TemplateHelper;

/**
 * @author khudyakov
 * @ created 10.02.14
 * @ $Author$
 * @ $Revision$
 */
public class CheckAccountTemplateForSomeTBHandler extends TemplateHandlerBase<TemplateDocument>
{
	public void process(TemplateDocument template, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		try
		{
			if (TemplateHelper.is40TBTemplateFromAccount(template))
			{
				throw new DocumentLogicException("По этому шаблону Вы можете совершать платежи только с Ваших карт.");
			}
		}
		catch (BusinessException ex)
		{
			throw new DocumentException(ex);
		}
	}
}
