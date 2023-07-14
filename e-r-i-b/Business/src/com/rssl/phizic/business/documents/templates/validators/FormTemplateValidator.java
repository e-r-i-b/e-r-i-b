package com.rssl.phizic.business.documents.templates.validators;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ¬алидатор проверки разрешенных к использованию форм
 *
 * @author khudyakov
 * @ created 09.02.14
 * @ $Author$
 * @ $Revision$
 */
public class FormTemplateValidator implements TemplateValidator
{
	private List<String> formNames = new ArrayList<String>();

	public FormTemplateValidator(String forms) throws BusinessException
	{
		if (StringHelper.isEmpty(forms))
		{
			throw new BusinessException("Ќе задан параметр supportedForms");
		}

		formNames = Arrays.asList(forms.split(","));
	}

	public void validate(TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		if (!formNames.contains(template.getFormType().getName()))
		{
			throw new BusinessLogicException("–абота с данным шаблоном недоступна.");
		}
	}
}
