package com.rssl.phizic.business.documents.strategies;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.limits.users.LimitDocumentInfo;

/**
 * Стратегия выполнения документа
 *
 * @author khudyakov
 * @ created 06.04.2012
 * @ $Author$
 * @ $Revision$
 */
public interface ProcessDocumentStrategy
{
	/**
	 * Обработать документ
	 */
	//TODO Убрать параммерты метода в конструктор
	void process(LimitDocumentInfo limitDocumentInfo, ClientAccumulateLimitsInfo amountInfo) throws BusinessException, BusinessLogicException;
}
