package com.rssl.phizic.business.documents.strategies;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.security.SecurityLogicException;

/**
 * @author niculichev
 * @ created 16.01.14
 * @ $Author$
 * @ $Revision$
 */
public interface DocumentAction
{
	/**
	 * Действие над документом
	 * @param document документ
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	void action(BusinessDocument document) throws BusinessLogicException, BusinessException;
}
