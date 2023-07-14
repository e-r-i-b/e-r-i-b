package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.templates.TemplateHelper;

/**
 * @author bogdanov
 * @ created 18.06.2012
 * @ $Author$
 * @ $Revision$
 *
 * Хендлер запрещающий выполнение платежа по шаблону в 40 ТБ по счету.
 */

public class CheckAccountTemplateForSomeTBHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof BusinessDocument))
			return;

		try
		{
			BusinessDocument doc = (BusinessDocument) document;
			if (TemplateHelper.isBy40TBTemplateFromAccount(doc))
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
