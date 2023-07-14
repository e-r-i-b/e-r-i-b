package com.rssl.phizic.business.documents.templates.validators;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;

/**
 * @author khudyakov
 * @ created 17.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class CompositeTemplateValidator implements TemplateValidator
{
	private TemplateValidator[] validators;

	/**
	 * @param validators массив валидаторов
	 */
	public CompositeTemplateValidator(TemplateValidator ... validators)
	{
		this.validators = validators.clone();
	}

	public void validate(TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		for (int i = 0; i < validators.length; i++)
		{
			validators[i].validate(template);
		}
	}
}
