package com.rssl.phizic.business.documents.templates.validators;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.common.types.documents.templates.ActivityCode;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.gate.payments.template.TemplateInfo;

/**
 * Проверка использования шаблона в мАПИ
 *
 * @author khudyakov
 * @ created 16.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class MAPITemplateValidator implements TemplateValidator
{
	public void validate(TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		TemplateInfo templateInfo = template.getTemplateInfo();
		if (ActivityCode.REMOVED.name().equals(templateInfo.getState().getCode()))
		{
			throw new BusinessLogicException("Работа с данным шаблоном недоступна.");
		}

		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		if (applicationInfo.isMobileApi() && !templateInfo.isUseInMAPI())
		{
			throw new BusinessLogicException("Шаблон недоступен в мобильной версии.");
		}
	}
}