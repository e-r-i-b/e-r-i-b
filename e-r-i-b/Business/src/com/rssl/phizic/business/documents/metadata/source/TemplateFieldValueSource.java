package com.rssl.phizic.business.documents.metadata.source;

import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;

import java.util.Collections;
import java.util.Map;

/**
 * »сточник данных на основе шаблона
 *
 * @author khudyakov
 * @ created 20.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class TemplateFieldValueSource implements FieldValuesSource
{
	private Map<String, String> fieldsMap;

	public TemplateFieldValueSource(TemplateDocument template) throws BusinessException
	{
		fieldsMap = template.getFormData();
	}

	public TemplateFieldValueSource(BusinessDocument document) throws BusinessException
	{
		if (!document.isByTemplate())
		{
			throw new BusinessException("ƒокумент id = " + document.getId() + " создан не по шаблону.");
		}

		TemplateDocument template = TemplateDocumentService.getInstance().findById(document.getTemplateId());
		if (template == null)
		{
			throw new BusinessException("дл€ документа id = " + document.getId() + " не найден шаблон templateId = " + document.getTemplateId());
		}

		fieldsMap = template.getFormData();
	}

	public String getValue(String name)
	{
		return fieldsMap.get(name);
	}

	public Map<String, String> getAllValues()
	{
		return Collections.unmodifiableMap(fieldsMap);
	}

	public boolean isChanged(String name)
	{
		return false;
	}

	public boolean isEmpty()
	{
		return false;
	}

	public boolean isMasked(String name)
	{
		return false;
	}
}
