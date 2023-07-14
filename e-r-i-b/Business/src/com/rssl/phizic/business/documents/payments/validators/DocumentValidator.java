package com.rssl.phizic.business.documents.payments.validators;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author Evgrafov
 * @ created 13.12.2005
 * @ $Author: khudyakov $
 * @ $Revision: 49377 $
 */

public interface DocumentValidator
{
	/**
	 * Проверить документ
	 * @param document документ
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	void validate(BusinessDocument document) throws BusinessException, BusinessLogicException;
}
