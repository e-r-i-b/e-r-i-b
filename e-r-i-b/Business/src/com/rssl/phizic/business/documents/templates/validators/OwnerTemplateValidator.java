package com.rssl.phizic.business.documents.templates.validators;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.clients.ClientComparator;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * Проверяем владельца шаблона
 *
 * @author khudyakov
 * @ created 27.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class OwnerTemplateValidator implements TemplateValidator
{
	public void validate(TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		try
		{
			Client client = PersonHelper.getContextPerson().asClient();
			if (new ClientComparator().compare(client, template.getClientOwner()) != 0)
			{
				throw new BusinessLogicException("Выбранный шаблон не принадлежит вам.");
			}
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (BusinessException e)
		{
			throw new BusinessException(e);
		}
	}
}
