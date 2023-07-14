package com.rssl.phizic.operations.documents.templates;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.operations.Transactional;

/**
 * @author Evgrafov
 * @ created 14.12.2005
 * @ $Author: khudyakov $
 * @ $Revision: 58855 $
 */

public class RemoveBankTemplateOperation extends RemoveTemplateOperationBase
{
	@Transactional
	public void remove() throws BusinessException
	{
		TemplateDocumentService.getInstance().remove(getEntity());
	}
}
