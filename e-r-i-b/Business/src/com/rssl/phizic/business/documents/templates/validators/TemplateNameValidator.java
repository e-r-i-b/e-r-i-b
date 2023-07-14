package com.rssl.phizic.business.documents.templates.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.persons.PersonHelper;

/**
 * ѕроверка совпадени€ названи€ шаблона клиента с уже существующими
 *
 * ограничение: использует PersonContext - не работает в ј–ћ сотрудника
 *
 * @author khudyakov
 * @ created 08.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class TemplateNameValidator extends FieldValidatorBase
{
	private TemplateDocument current;


	public TemplateNameValidator()
	{}

	public TemplateNameValidator(TemplateDocument current)
	{
		this.current = current;
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		try
		{
			// провер€ем существует ли уже шаблон с таким именем
			TemplateDocument existing = TemplateDocumentService.getInstance().findByTemplateName(PersonHelper.getContextPerson().asClient(), value);
			if (existing == null)
			{
				return true;
			}

			if (current == null)
			{
				setMessage(String.format(Constants.DUPLICATE_TEMPLATE_NAME_ERROR_MESSAGE, value));
				return false;
			}

			if (!existing.getId().equals(current.getId()))
			{
				setMessage(String.format(Constants.DUPLICATE_TEMPLATE_NAME_ERROR_MESSAGE, value));
				return false;
			}

			return true;
		}
		catch (BusinessException e)
		{
			throw new TemporalDocumentException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new TemporalDocumentException(e);
		}
	}
}
