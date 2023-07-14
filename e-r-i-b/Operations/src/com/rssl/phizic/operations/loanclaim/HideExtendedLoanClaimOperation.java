package com.rssl.phizic.operations.loanclaim;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author Nady
 * @ created 17.01.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Операция для блока "Статусы заявок" на странице Кредиты
 */
public class HideExtendedLoanClaimOperation extends OperationBase
{
	private static final BusinessDocumentService service = new BusinessDocumentService();

	/**
	 * Скрыть заявку из блока "Статусы заявки"
	 * @param id - идентификатор заявки
	 * @throws BusinessException
	 */

	public void hideLoanCliam(final Long id) throws BusinessException
	{
		ExtendedLoanClaim doc = (ExtendedLoanClaim)service.findById(id);
		doc.setHidden(true);
		service.addOrUpdate(doc);
	}
}
