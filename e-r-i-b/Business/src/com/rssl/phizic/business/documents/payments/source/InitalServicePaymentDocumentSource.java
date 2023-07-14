package com.rssl.phizic.business.documents.payments.source;

import com.rssl.common.forms.processing.CompositeFieldValuesSource;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.documents.metadata.source.DocumentFieldValuesSource;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.logging.operations.context.OperationContextUtil;

/**
 * @author krenev
 * @ created 05.10.2011
 * @ $Author$
 * @ $Revision$
 * �������� ��������� ��� ������� ���� ����������� ������.
 * ���������� ������������ ���������� ��� 1 ����.
 */
public class InitalServicePaymentDocumentSource extends PersonDocumentSourceBase implements DocumentSource
{
	private BusinessDocument businessDocument;
	private Metadata metadata;

	/**
	 * ���������� ��������� �������� ������� ���� ����������� ������
	 * @param originalSource �������� ������������� ���������/�������
	 * @param initialValuesSource ���������������� �������� ��� �������� ����. ����� ���� ������� ������������� � ���������� �� ������ ��������� �����
	 */
	public InitalServicePaymentDocumentSource(DocumentSource originalSource, FieldValuesSource initialValuesSource) throws BusinessException, BusinessLogicException
	{
		businessDocument = originalSource.getDocument();
		OperationContextUtil.synchronizeObjectAndOperationContext(businessDocument);

		metadata = businessDocument.isByTemplate() ?
				MetadataCache.getExtendedMetadata(null, TemplateDocumentService.getInstance().findById(businessDocument.getTemplateId())) :
				MetadataCache.getExtendedMetadata(businessDocument.getFormName(), new CompositeFieldValuesSource(initialValuesSource, new DocumentFieldValuesSource(originalSource.getMetadata(), businessDocument)));
	}

	public BusinessDocument getDocument()
	{
		return businessDocument;
	}

	public Metadata getMetadata()
	{
		return metadata;
	}
}
