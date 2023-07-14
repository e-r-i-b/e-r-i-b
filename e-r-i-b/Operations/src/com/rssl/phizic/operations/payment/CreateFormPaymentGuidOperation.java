package com.rssl.phizic.operations.payment;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.claims.DocumentGuid;
import com.rssl.phizic.business.documents.BusinessDocumentService;

import java.util.UUID;

/**
 * @author eMakarov
 * @ created 18.09.2008
 * @ $Author$
 * @ $Revision$
 */
public class CreateFormPaymentGuidOperation extends CreateFormPaymentOperation
{
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private static final SimpleService simpleService = new SimpleService();
	private String guid;

	public Long save() throws BusinessException, BusinessLogicException
	{
		Long documentId = getDocumentId();
		Long result = super.save();

		if (documentId == null)
		{
			DocumentGuid documentGuid = new DocumentGuid();
			documentGuid.setDocumentId(result);
			documentGuid.setGuid(UUID.randomUUID().toString());

			guid = simpleService.addOrUpdate(documentGuid).getGuid();
		}
		else
		{
			guid = businessDocumentService.findGuidById(documentId);
		}

		return result;
	}

	public String getGuid()
	{
		return guid;
	}
}
