package com.rssl.phizic.business.documents.templates.source;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.templates.TemplateDocument;

/**
 * @author osminin
 * @ created 01.08.14
 * @ $Author$
 * @ $Revision$
 *
 * Источник данных по существующему шаблону и мете. Никаких дополнительных действий не выполняется.
 */
public class SimpleExistingTemplateSource implements TemplateDocumentSource
{
	private TemplateDocument template;
	private Metadata metadata;

	/**
	 * ctor
	 * @param template шаблон
	 * @param metadata мета
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public SimpleExistingTemplateSource(TemplateDocument template, Metadata metadata) throws BusinessException, BusinessLogicException
	{
		if (template == null)
		{
			throw new IllegalArgumentException("Шаблон не может быть null");
		}

		this.template = template;
		this.metadata = metadata == null ? MetadataCache.getExtendedMetadata(template) : metadata;
	}

	public TemplateDocument getTemplate()
	{
		return template;
	}

	public BusinessDocument getDocument()
	{
		throw new IllegalStateException("Не поддерживается");
	}

	public Metadata getMetadata()
	{
		return metadata;
	}
}
