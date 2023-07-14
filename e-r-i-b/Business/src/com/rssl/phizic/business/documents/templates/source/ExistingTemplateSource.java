package com.rssl.phizic.business.documents.templates.source;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.documents.templates.validators.CompositeTemplateValidator;
import com.rssl.phizic.business.documents.templates.validators.TemplateValidator;
import com.rssl.phizic.logging.operations.context.OperationContextUtil;

/**
 * »сточник данных по сужествующему шаблону платежа
 *
 * @author mihaylov
 * @ created 14.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class ExistingTemplateSource implements TemplateDocumentSource
{
	private Metadata metadata;
	private TemplateDocument template;


	/**
	 *  онструктор источника документа по существующему шаблону
	 *
	 * @param templateId ID шаблона
	 * @param validators валидаторы шаблона документа
	 */
	public ExistingTemplateSource(Long templateId, TemplateValidator ... validators) throws BusinessLogicException, BusinessException
	{
		if (templateId == null)
		{
			throw new BusinessException("Ўаблон не может быть null, templateId = " + templateId);
		}

		TemplateDocument source = TemplateDocumentService.getInstance().findById(templateId);
		if (source == null)
		{
			throw new BusinessException("Ўаблон не может быть null, templateId = " + templateId);
		}

		new CompositeTemplateValidator(validators).validate(source);

		this.template = source;
		this.metadata = MetadataCache.getExtendedMetadata(template);

		OperationContextUtil.synchronizeObjectAndOperationContext(template);
	}

	/**
	 *  онструктор источника документа по существующему шаблону
	 *
	 * @param source шаблон
	 * @param validators валидаторы шаблона документа
	 */
	public ExistingTemplateSource(TemplateDocument source, TemplateValidator ... validators) throws BusinessLogicException, BusinessException
	{
		if (source == null)
		{
			throw new BusinessException("Ўаблон не может быть null");
		}

		new CompositeTemplateValidator(validators).validate(source);

		this.template = source;
		this.metadata = MetadataCache.getExtendedMetadata(template);

		OperationContextUtil.synchronizeObjectAndOperationContext(template);
	}

	public TemplateDocument getTemplate()
	{
		return template;
	}

	public BusinessDocument getDocument()
	{
		throw new IllegalStateException("Ќе поддерживаетс€");
	}

	public Metadata getMetadata()
	{
		return metadata;
	}
}
