package com.rssl.phizic.business.documents.payments.source;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.claims.DocumentGuid;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.smsbanking.pseudonyms.NullPseudonymException;
import com.rssl.phizic.logging.operations.context.OperationContextUtil;

/**
 * @author hudyakov
 * @ created 27.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class GuidDocumentSource implements DocumentSource
{
	private static final BusinessDocumentService documentService = new BusinessDocumentService();

	private BusinessDocument document;
	private Metadata metadata;

	public GuidDocumentSource(String guid) throws BusinessException, NullPseudonymException, BusinessLogicException
	{
		AuthModule authModule = AuthModule.getAuthModule();
		DocumentGuid documentGuid = documentService.findIdByGuidAndPerson(guid, authModule.getPrincipal().getLogin().getId());

		if (documentGuid == null)
			throw new NullPseudonymException("Ошибка: неверный формат команды");

		document = documentService.findById(documentGuid.getDocumentId());
		OperationContextUtil.synchronizeObjectAndOperationContext(document);
		metadata = MetadataCache.getExtendedMetadata(document);
	}

	public BusinessDocument getDocument()
	{
		return document;
	}

	public Metadata getMetadata()
	{
		return metadata;
	}
}
