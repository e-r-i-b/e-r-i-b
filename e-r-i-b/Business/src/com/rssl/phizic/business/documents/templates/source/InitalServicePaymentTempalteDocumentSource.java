package com.rssl.phizic.business.documents.templates.source;

import com.rssl.common.forms.processing.CompositeFieldValuesSource;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.documents.metadata.source.TemplateFieldValueSource;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.PersonDocumentSourceBase;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.logging.operations.context.OperationContextUtil;

/**
 * @author khudyakov
 * @ created 13.06.2012
 * @ $Author$
 * @ $Revision$
 */
public class InitalServicePaymentTempalteDocumentSource extends PersonDocumentSourceBase implements TemplateDocumentSource
{
	private BusinessDocument document;
	private Metadata metadata;

	public InitalServicePaymentTempalteDocumentSource(DocumentSource originalSource, FieldValuesSource initialValuesSource) throws BusinessException, BusinessLogicException
	{
		document = originalSource.getDocument();

		OperationContextUtil.synchronizeObjectAndOperationContext(document);

		FieldValuesSource source = new CompositeFieldValuesSource(initialValuesSource, new TemplateFieldValueSource(document));
		metadata = MetadataCache.getExtendedMetadata(document.getFormName(), source);
	}

	public BusinessDocument getDocument()
	{
		return document;
	}

	public Metadata getMetadata()
	{
		return metadata;
	}

	public TemplateDocument getTemplate()
	{
		return null;
	}
}
