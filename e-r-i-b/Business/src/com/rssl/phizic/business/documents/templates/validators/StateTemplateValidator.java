package com.rssl.phizic.business.documents.templates.validators;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.common.types.documents.StateCode;
import com.rssl.phizic.common.types.documents.templates.ActivityCode;

/**
 * Валидатор проверки статуса шаблона документа
 *
 * @author khudyakov
 * @ created 17.02.14
 * @ $Author$
 * @ $Revision$
 */
public class StateTemplateValidator implements TemplateValidator
{
	public void validate(TemplateDocument template) throws BusinessException
	{
		String state = template.getState().getCode();
		if (StateCode.DRAFTTEMPLATE.name().equals(state) || StateCode.SAVED_TEMPLATE.name().equals(state))
		{
			throw new BusinessException("Невозможно создать платеж по шаблону со стусами DRAFTTEMPLATE и SAVED_TEMPLATE");
		}

		String activityCode = template.getTemplateInfo().getState().getCode();
		if (!ActivityCode.ACTIVE.name().equals(activityCode))
		{
			throw new BusinessException("Невозможно создать платеж по шаблону, удаленному клиентом.");
		}
	}
}
