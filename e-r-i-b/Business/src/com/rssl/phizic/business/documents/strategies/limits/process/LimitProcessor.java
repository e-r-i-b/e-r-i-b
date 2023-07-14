package com.rssl.phizic.business.documents.strategies.limits.process;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.strategies.ClientAccumulateLimitsInfo;
import com.rssl.phizic.business.limits.Limit;

/**
 * Интерфейс для процессоров, проверяющих попадание суммы документа в лимит
 *
 * @author khudyakov
 * @ created 04.11.13
 * @ $Author$
 * @ $Revision$
 */
public interface LimitProcessor
{
	/**
	 * Проверить лимит
	 * @param limit лимит
	 * @param document документ
	 */
	void process(Limit limit, BusinessDocument document, ClientAccumulateLimitsInfo amountInfo) throws BusinessException, BusinessLogicException;
}
