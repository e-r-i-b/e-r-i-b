package com.rssl.phizic.operations.documents.templates;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.common.types.documents.templates.ActivityCode;

/**
 * @author Evgrafov
 * @ created 14.12.2005
 * @ $Author: khudyakov $
 * @ $Revision: 61558 $
 */

public class RemoveClientTemplateOperation extends RemoveTemplateOperationBase
{
	@Transactional
	public void remove() throws BusinessException
	{
		template.getTemplateInfo().setState(new State(ActivityCode.REMOVED.name(), ActivityCode.REMOVED.getDescription()));
		TemplateDocumentService.getInstance().addOrUpdate(template);
	}
}
