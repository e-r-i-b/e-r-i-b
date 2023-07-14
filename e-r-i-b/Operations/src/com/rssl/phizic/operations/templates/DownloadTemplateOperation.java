package com.rssl.phizic.operations.templates;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.template.DocTemplateService;
import com.rssl.phizic.business.template.DocTemplate;
import com.rssl.phizic.operations.OperationBase;

/**
 * Created by IntelliJ IDEA. User: Novikov_A Date: 30.01.2007 Time: 16:03:23 To change this template use File
 * | Settings | File Templates.
 */
public class DownloadTemplateOperation extends OperationBase
{
	private static final DocTemplateService service = new DocTemplateService();

	private DocTemplate document;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		document = service.getById(id);
	}

	public DocTemplate getTemplate()
	{
		return document;
	}
}
