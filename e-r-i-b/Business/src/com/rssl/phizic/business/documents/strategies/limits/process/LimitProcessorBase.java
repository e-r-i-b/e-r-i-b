package com.rssl.phizic.business.documents.strategies.limits.process;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.limits.*;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * Процессор, проверяющий попадание суммы документа в лимит
 *
 * @author khudyakov
 * @ created 04.11.13
 * @ $Author$
 * @ $Revision$
 */
public abstract class LimitProcessorBase implements LimitProcessor
{
	private static final LimitService limitService = new LimitService();

	Money getExternalProviderAccumulatedAmount(Limit limit, BusinessDocument document) throws BusinessException
	{
		if (LimitType.EXTERNAL_CARD == limit.getType())
		{
			return limitService.getExternalCardProviderAccumulatedAmount(limit, document, DateHelper.getPreviousDay());
		}

		if (LimitType.EXTERNAL_PHONE == limit.getType())
		{
			return limitService.getExternalPhoneProviderAccumulatedAmount(limit, document, DateHelper.getPreviousDay());
		}

		throw new IllegalArgumentException("Некорректный тип лимита.");
	}
}