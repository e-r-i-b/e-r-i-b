package com.rssl.phizic.operations.templates;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.Transactional;

import com.rssl.phizic.business.template.BanksDocument;
import com.rssl.phizic.business.template.BanksDocumentService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;

/**
 * User: Novikov_A
 * Date: 03.02.2007
 * Time: 14:42:57
 */
public class RemoveBanksDocumentOperation extends OperationBase implements RemoveEntityOperation
{
	private static final BanksDocumentService bankDocumentsService = new BanksDocumentService();

	private BanksDocument banksDocument;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		banksDocument = bankDocumentsService.getById(id);
		if (banksDocument == null)
			throw new BusinessException("Документ банка не найден. id: " + id);
	}

	@Transactional
	public void remove() throws BusinessException
	{
		bankDocumentsService.remove(banksDocument);
	}

	public BanksDocument getEntity()
	{
		return banksDocument;
	}
}
