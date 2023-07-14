package com.rssl.phizic.business.documents.payments.source;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.DocumentService;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.logging.operations.context.OperationContextUtil;

/**
 * Источник документа на отзыв платежа
 * @author gladishev
 * @ created 01.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class SystemWithdrawDocumentSource implements DocumentSource
{
	private static final DocumentService documentService = new DocumentService();

	private BusinessDocument businessDocument;
	private Metadata metadata;

	/**
	 * ctor
	 * @param formName - название формы
	 * @param initialValuesSource начальные параметры
	 * @param original отменяемый документ
	 */
	public SystemWithdrawDocumentSource(String formName, FieldValuesSource initialValuesSource, BusinessDocument original) throws DocumentException, DocumentLogicException, BusinessException, BusinessLogicException
	{
		metadata = MetadataCache.getExtendedMetadata(formName, initialValuesSource);
		businessDocument = documentService.createDocument(metadata, initialValuesSource, null);
		initDocument(original);
		businessDocument.setStateMachineName(metadata.getStateMachineInfo().getName());
	}

	public BusinessDocument getDocument()
	{
		return businessDocument;
	}

	public Metadata getMetadata()
	{
		return metadata;
	}

	private void initDocument(BusinessDocument original) throws BusinessException, BusinessLogicException
	{
		businessDocument.setDepartment(original.getDepartment());
		businessDocument.setOwner(original.getOwner());
		businessDocument.setCreationType(CreationType.internet);
		businessDocument.setState(new State("INITIAL"));
		businessDocument.setCreationSourceType(CreationSourceType.system);

		OperationContextUtil.synchronizeObjectAndOperationContext(businessDocument);
	}
}
