package com.rssl.phizic.business.documents.templates.validators;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.OperationMessagesConfig;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.config.ConfigFactory;

/**
 * Провекра активности биллинговой системы для платежа с шаблона.
 *
 * @author bogdanov
 * @ created 29.07.14
 * @ $Author$
 * @ $Revision$
 */

public class CheckInactiveBillingValidator implements TemplateValidator
{
	public void validate(TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		if (!DocumentHelper.isBillingExternalPaymentTemplateSupported(template))
			throw new BusinessLogicException(
					ConfigFactory.getConfig(OperationMessagesConfig.class).getOperationMessage(OperationMessagesConfig.NOT_ACTIVE_PROVIDER_MESSAGE));
	}
}
