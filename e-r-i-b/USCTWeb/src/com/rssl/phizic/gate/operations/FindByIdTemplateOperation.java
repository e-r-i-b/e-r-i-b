package com.rssl.phizic.gate.operations;

import com.rssl.phizic.gate.templates.impl.TemplateDocument;
import com.rssl.phizic.gate.templates.services.CorrelationHelper;
import com.rssl.phizic.gate.templates.services.TemplateDocumentService;
import com.rssl.phizic.gate.templates.services.generated.GateTemplate;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import static com.rssl.phizic.logging.Constants.LOG_MODULE_CORE;

/**
 * Операция поиска шаблона документа по идентификатору шаблона
 *
 * @author khudyakov
 * @ created 20.05.14
 * @ $Author$
 * @ $Revision$
 */
public class FindByIdTemplateOperation implements FindEntityOperation, CorrelationOperation
{
	private static final Log log = PhizICLogFactory.getLog(LOG_MODULE_CORE);

	private TemplateDocument template;

	public FindByIdTemplateOperation(Long id) throws Exception
	{
		if (id == null)
		{
			log.error("Операция поиска шаблона по id: идентификатор шаблона не задан.");
			return;
		}

		try
		{
			template = TemplateDocumentService.getInstance().findById(id);
		}
		catch (Exception e)
		{
			log.error("Операция поиска шаблона по id: ошибка получений шаблона, id = " + id, e);
			return;
		}
	}

	public TemplateDocument getEntity()
	{
	 	return template;
	}

	public GateTemplate toGeneratedEntity() throws Exception
	{
		if (template == null)
		{
			return null;
		}

		try
		{
			return CorrelationHelper.toGenerated(getEntity());
		}
		catch (Exception e)
		{
			log.error("Операция поиска шаблона по id: ошибка приведения шаблона, id = " + template.getId(), e);
			return null;
		}
	}
}
