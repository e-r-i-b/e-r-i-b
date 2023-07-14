package com.rssl.phizic.business.documents.templates.stateMachine.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.OperationMessagesConfig;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.config.ConfigFactory;

/**
 * Хендлре проверки статуса билинговой системы для шаблонов.
 *
 * @author bogdanov
 * @ created 28.07.14
 * @ $Author$
 * @ $Revision$
 */

public class CheckBillingStatusHandler extends TemplateHandlerBase<TemplateDocument>
{
	public void process(TemplateDocument document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!DocumentHelper.isBillingExternalPaymentTemplateSupported(document))
			throw new DocumentLogicException(
					ConfigFactory.getConfig(OperationMessagesConfig.class).getOperationMessage(OperationMessagesConfig.NOT_ACTIVE_PROVIDER_MESSAGE));
	}
}
