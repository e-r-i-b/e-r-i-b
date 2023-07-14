package com.rssl.phizic.payment;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.limits.Limit;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.task.ExecutableTask;

/**
 * @author Erkin
 * @ created 10.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Платёжная задача
 */
public interface PaymentTask extends ExecutableTask
{
	/**
	 * получить редактируемый документ
	 * @param <T>
	 * @return редактируемый документ
	 */
	<T extends BusinessDocument> T getDocument();

	/**
	 * получить сработавший лимит (возвращает null, если ни один лимит не сработал)
	 * @return сработавший лимит
	 */
	Limit getCurrentLimit();

	/**
	 * получить накопленную сумму по сработавшему лимиту (возвращает null, если ни один лимит не сработал)
	 * @return накопленная сумма по сработавшему лимиту
	 */
	Money getAccumulatedAmount();
}
