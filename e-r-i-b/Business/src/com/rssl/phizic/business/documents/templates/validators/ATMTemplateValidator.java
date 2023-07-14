package com.rssl.phizic.business.documents.templates.validators;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.common.types.documents.templates.ActivityCode;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.gate.payments.template.TemplateInfo;

/**
 * Валидатор шаблонов ATM версии
 *
 * @author khudyakov
 * @ created 17.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class ATMTemplateValidator implements TemplateValidator
{
	public void validate(TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		TemplateInfo templateInfo = template.getTemplateInfo();
		if (applicationInfo.isATM() && !templateInfo.isUseInATM())
		{
			throw new BusinessLogicException("Шаблон недоступен в версии устройств самообслуживания.");
		}

		if (ActivityCode.REMOVED.name().equals(templateInfo.getState().getCode()))
		{
			throw new BusinessLogicException("Работа с данным шаблоном недоступна.");
		}
	}
}
