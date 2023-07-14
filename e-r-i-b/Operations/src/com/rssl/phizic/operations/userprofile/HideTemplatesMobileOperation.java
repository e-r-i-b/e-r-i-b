package com.rssl.phizic.operations.userprofile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.TemplateHelper;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.OperationBase;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Операция для скрытия шаблонов в мобильной версии
 * @author Pankin
 * @ created 08.10.2012
 * @ $Author$
 * @ $Revision$
 */

public class HideTemplatesMobileOperation extends OperationBase
{
	private List<TemplateDocument> templates;

	/**
	 * инициализация операции
	 * @param templateIds идентификаторы шаблонов, которые надо скрыть
	 */
	public void initialize(String... templateIds) throws BusinessException, BusinessLogicException
	{
		if (ArrayUtils.isEmpty(templateIds))
			return;

		List<Long> ids = new ArrayList<Long>(templateIds.length);
		for (String templateId : templateIds)
		{
			ids.add(Long.parseLong(templateId));
		}

		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		templates = TemplateDocumentService.getInstance().findByIds(person.asClient(), ids);
	}

	/**
	 * Скрыть продукты из мобильного приложения.
	 */
	public void hide() throws BusinessException
	{
		if (CollectionUtils.isEmpty(templates))
			return;

		for (TemplateDocument template : templates)
		{
			template.getTemplateInfo().setUseInMAPI(false);
		}

		TemplateDocumentService.getInstance().addOrUpdate(templates);
	}
}
