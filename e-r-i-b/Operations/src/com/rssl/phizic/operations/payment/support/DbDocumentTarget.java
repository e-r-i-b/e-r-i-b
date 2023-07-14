package com.rssl.phizic.operations.payment.support;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;

/**
 * @author Evgrafov
 * @ created 12.01.2006
 * @ $Author: khudyakov $
 * @ $Revision: 51431 $
 */

public class DbDocumentTarget implements DocumentTarget
{
	private static final SimpleService simpleService = new SimpleService();

	public void save(BusinessDocument document) throws BusinessException
	{
		simpleService.addOrUpdate(document);
	}
}
