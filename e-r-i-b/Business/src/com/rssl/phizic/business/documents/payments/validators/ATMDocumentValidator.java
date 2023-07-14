package com.rssl.phizic.business.documents.payments.validators;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.UserPrincipal;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ApplicationConfig;

/**
 * @author khudyakov
 * @ created 16.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class ATMDocumentValidator implements DocumentValidator
{
	public void validate(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		UserPrincipal principal = AuthModule.getAuthModule().getPrincipal();
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();

		if (!"TEMPLATE".equals(document.getState().getCode()) && !"WAIT_CONFIRM_TEMPLATE".equals(document.getState().getCode())
				&& (applicationInfo.isATM() || principal.isMobileLightScheme() || principal.isMobileLimitedScheme()))
		{
			throw new BusinessLogicException("В данной версии приложения доступны только подтвержденные шаблоны");
		}
	}
}
