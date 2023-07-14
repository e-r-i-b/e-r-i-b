package com.rssl.phizic.business.documents.templates.stateMachine.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.TemplateHelper;
import com.rssl.phizic.common.types.documents.FormType;

/**
 * @author khudyakov
 * @ created 26.02.14
 * @ $Author$
 * @ $Revision$
 */
public class DisallowExternalAccountTemplateHandler extends TemplateHandlerBase<TemplateDocument>
{
	public void process(TemplateDocument template, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		try
		{
			if (FormType.isExternalDocument(template.getFormType()) && TemplateHelper.isTemplateDisallowedFromAccount(template))
			{
				throw new DocumentLogicException(Constants.EXTERNAL_ACCOUNT_PAYMENT_TEMPLATE_ERROR_MESSAGE);
			}
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentException(e);
		}
		catch (BusinessException e)
		{
			throw new DocumentLogicException(e);
		}
	}
}
