package com.rssl.phizic.gate.operations;

import com.rssl.phizic.gate.templates.impl.TemplateDocument;
import com.rssl.phizic.gate.templates.services.CorrelationHelper;
import com.rssl.phizic.gate.templates.services.GateTemplateFactory;
import com.rssl.phizic.gate.templates.services.TemplateDocumentService;
import com.rssl.phizic.gate.templates.services.generated.GateTemplate;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.util.ArrayList;
import java.util.List;

import static com.rssl.phizic.logging.Constants.LOG_MODULE_CORE;

/**
 * Операция обновления шаблонов документов
 *
 * @author khudyakov
 * @ created 21.05.14
 * @ $Author$
 * @ $Revision$
 */
public class UpdateTemplatesOperation implements CorrelationOperation
{
	private static final Log log = PhizICLogFactory.getLog(LOG_MODULE_CORE);

	private List<Object> objects = new ArrayList<Object>();
	private List<TemplateDocument> templates = new ArrayList<TemplateDocument>();


	public UpdateTemplatesOperation(List objects) throws Exception
	{
		this.objects = objects;
	}

	/**
	 * Обновить список шаблонов
	 * @throws Exception
	 */
	public void update() throws Exception
	{
		try
		{
			for (Object object : objects)
			{
				GateTemplate generated = (GateTemplate) object;

				templates.add(getTemplate(generated));
			}
		}
		catch (Exception e)
		{
			log.error("Операция обновления шаблона(ов): ошибка поиска шаблонов клиента.");
			throw e;
		}

		try
		{
			TemplateDocumentService.getInstance().addOrUpdate(templates);
		}
		catch (Exception e)
		{
			log.error("Операция обновления шаблона(ов): ошибка обновления шаблонов клиента.");
			throw e;
		}
	}

	public List<GateTemplate> toGeneratedEntity() throws Exception
	{
		try
		{
			return CorrelationHelper.toGeneratedTemplates(templates);
		}
		catch (Exception e)
		{
			log.error("Операция обновления шаблона(ов): ошибка приведения шаблонов", e);
			throw e;
		}
	}

	private TemplateDocument getTemplate(GateTemplate generated) throws Exception
	{
		Long id = generated.getId();
		if (id == null)
		{
			return GateTemplateFactory.getInstance().build(generated);
		}

		return GateTemplateFactory.getInstance().build(TemplateDocumentService.getInstance().findById(id), generated);
	}
}
