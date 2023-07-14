package com.rssl.phizic.business.documents.templates.validators;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

/**
 * 
 *
 * @author khudyakov
 * @ created 17.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class ERIBTemplateValidator implements TemplateValidator
{
	public void validate(TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		if (ApplicationConfig.getIt().getApplicationInfo().isWeb() && !template.getTemplateInfo().isUseInERIB())
		{
			throw new BusinessLogicException("Ўаблон недоступен в основной версии приложени€.");
		}
	}
}
