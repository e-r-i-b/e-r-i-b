package com.rssl.phizic.operations.templates;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.Transactional;

import com.rssl.phizic.business.template.DocTemplate;
import com.rssl.phizic.business.template.DocTemplateService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;

/**
 * User: Novikov_A
 * Date: 03.02.2007
 * Time: 14:42:57
 */
public class RemoveTemplateDocumentOperation extends OperationBase implements RemoveEntityOperation
{
	private static final DocTemplateService service = new DocTemplateService();
	private DocTemplate docTemplate;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		docTemplate = service.getById(id);
		if (docTemplate == null)
			throw new BusinessException("Шаблон документа не найден. id: " + id);
	}

	@Transactional
	public void remove() throws BusinessException
	{
		service.remove(docTemplate);
	}

	public DocTemplate getEntity()
	{
		return docTemplate;
	}
}
