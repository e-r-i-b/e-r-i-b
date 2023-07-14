package com.rssl.phizic.business.documents.templates.validators;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.gate.payments.template.TemplateInfo;

/**
 * Валидатор активности шаблона в канале
 *
 * @author khudyakov
 * @ created 25.05.14
 * @ $Author$
 * @ $Revision$
 */
public class ChannelActivityTemplateValidator implements TemplateValidator
{
	public void validate(TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		if (!accept(template))
		{
			throw new BusinessException("Оплата по шаблону id = " + template.getId() + " в данном канале запрещена.");
		}
	}

	private boolean accept(TemplateDocument template)
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		TemplateInfo templateInfo = template.getTemplateInfo();

		if (applicationInfo.isATM())
		{
			return templateInfo.isUseInATM();
		}
		if (applicationInfo.isMobileApi())
		{
			return templateInfo.isUseInMAPI();
		}
		if (applicationInfo.isWeb())
		{
			return templateInfo.isUseInERIB();
		}
		if (applicationInfo.isSMS())
		{
			return templateInfo.isUseInERMB();
		}

		throw new IllegalStateException("Некорректный канал оплаты по шаблону, application = " + applicationInfo.getApplication());
	}
}
