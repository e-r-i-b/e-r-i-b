package com.rssl.phizic.business.documents.strategies.limits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.strategies.ClientAccumulateLimitsInfo;
import com.rssl.phizic.business.documents.strategies.ProcessDocumentStrategy;

/**
 * »нтерфейс стратегий проверки выполнени€ документа
 *
 * @author khudyakov
 * @ created 08.11.13
 * @ $Author$
 * @ $Revision$
 */
public interface CheckDocumentStrategy extends ProcessDocumentStrategy
{
	/**
	 * ѕопадает ли документ в диапазон лимита
	 *
	 * ќграничени€:
	 * 1. при проверке блокировка логина клиента, дл€ корректного подсчета результата в многопоточном режиме,
	 * не используетс€
	 * 2. логические исключени€ перехватываютс€
	 *
	 * @param limitsInfo доп. информаци€
	 * @return true - попадает
	 */
	boolean check(ClientAccumulateLimitsInfo limitsInfo) throws BusinessException, BusinessLogicException;

	/**
	 * ѕопадает ли документ в диапазон лимита
	 *
	 * ќграничени€:
	 * 1. при проверке блокировка логина клиента, дл€ корректного подсчета результата в многопоточном режиме,
	 * не используетс€
	 * 2. логические исключени€ не перехватываютс€
	 *
	 * @param limitsInfo доп. информаци€
	 * @return true - попадает
	 */
	boolean checkAndThrow(ClientAccumulateLimitsInfo limitsInfo) throws BusinessException, BusinessLogicException;
}
