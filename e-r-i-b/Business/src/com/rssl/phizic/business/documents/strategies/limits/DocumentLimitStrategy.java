package com.rssl.phizic.business.documents.strategies.limits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.strategies.ProcessDocumentStrategy;
import com.rssl.phizic.business.limits.Limit;
import com.rssl.phizic.common.types.Money;

/**
 * Интерфейс работы со стратегиями, учитывающими лимиты по операциям
 *
 * @author khudyakov
 * @ created 10.04.2012
 * @ $Author$
 * @ $Revision$
 */
public interface DocumentLimitStrategy extends CheckDocumentStrategy
{
	/**
	 * @return сработавший лимит
	 */
	Limit getCurrentLimit();

	/**
	 * @return сумма, которую можно оплатить на текущий момент с учетом лимита
	 */
	Money getAvailableAmount();

	/**
	 * @return сумма по лимиту, накопленная за 24 часа клиентом
	 * (значение возвращается только для лимитов по сумме операции)
	 */
	Money getAccumulatedAmount();
}
