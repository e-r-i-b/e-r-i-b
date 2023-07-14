package com.rssl.phizic.gate.operations;

import com.rssl.phizic.gate.templates.impl.TemplateDocument;
import com.rssl.phizic.gate.templates.services.TemplateDocumentService;
import com.rssl.phizic.gate.templates.services.generated.GateTemplate;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.util.ArrayList;
import java.util.List;

import static com.rssl.phizic.logging.Constants.LOG_MODULE_CORE;

/**
 * Операция удаления шаблонов клиента
 *
 * @author khudyakov
 * @ created 21.05.14
 * @ $Author$
 * @ $Revision$
 */
public class RemoveTemplatesOperation implements Operation
{
	private static final Log log = PhizICLogFactory.getLog(LOG_MODULE_CORE);

	private List<Object> objects = new ArrayList<Object>();
	private List<TemplateDocument> templates = new ArrayList<TemplateDocument>();


	public RemoveTemplatesOperation(List objects) throws Exception
	{
		this.objects = objects;
	}

	/**
	 * Удалить список шаблонов
	 * @throws Exception
	 */
	public void remove() throws Exception
	{
		try
		{
			for (Object object : objects)
			{
				GateTemplate generated = (GateTemplate) object;

				TemplateDocument templateDocument = TemplateDocumentService.getInstance().findById(generated.getId());
				if (templateDocument == null)
				{
					log.error("Операция удаления шаблона(ов): шаблон id = " + generated.getId() + " не найден.");
					continue;
				}

				templates.add(templateDocument);
			}
		}
		catch (Exception e)
		{
			log.error("Операция удаления шаблона(ов): ошибка поиска шаблонов клиента.", e);
			throw e;
		}

		try
		{
			TemplateDocumentService.getInstance().remove(templates);
		}
		catch (Exception e)
		{
			log.error("Операция удаления шаблона(ов): ошибка удаления списка шаблонов.", e);
			throw e;
		}
	}
}
