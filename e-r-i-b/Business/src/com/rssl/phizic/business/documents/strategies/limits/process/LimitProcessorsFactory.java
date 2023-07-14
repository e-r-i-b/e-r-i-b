package com.rssl.phizic.business.documents.strategies.limits.process;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.strategies.limits.Constants;
import com.rssl.phizic.business.documents.strategies.limits.process.LimitProcessor;
import com.rssl.phizic.business.limits.Limit;
import com.rssl.phizic.business.limits.LimitType;
import com.rssl.phizic.business.limits.OperationType;
import com.rssl.phizic.business.limits.RestrictionType;

import java.util.*;

/**
 * Фабрика, возвращающая процессор учета лимита
 *
 * @author khudyakov
 * @ created 04.11.13
 * @ $Author$
 * @ $Revision$
 */
public class LimitProcessorsFactory
{
	private static final Map<OperationType, LimitProcessor> processors = new HashMap<OperationType, LimitProcessor>(3);
	static
	{
		processors.put(OperationType.IMPOSSIBLE_PERFORM_OPERATION,      new ImpossiblePerformLimitProcessor());
		processors.put(OperationType.NEED_ADDITIONAL_CONFIRN,           new RequireAdditionConfirmLimitProcessor());
		processors.put(OperationType.READ_SIM,                          new RequireAdditionCheckLimitProcessor());
	}

	/**
	 * Получить процессор обрабатывающий лимит
	 * @param limit лимит
	 * @return процессор
	 */
	public static LimitProcessor getProcessor(Limit limit)
	{
		LimitProcessor processor = processors.get(limit.getOperationType());
		if (processor == null)
		{
			throw new IllegalArgumentException(String.format(Constants.WRONG_OPERATION_LIMIT_TYPE_ERROR_MESSAGE, limit.getOperationType(), limit.getId()));
		}

		return processor;
	}

	/**
	 * Получить процессор, накладывающий ограничение на перевод
	 * @param document документ
	 * @return процессор
	 */
	public static LimitProcessor getProcessor(BusinessDocument document)
	{
		//пока такой случай только один, возвращаем как по умолчанию
		return new ByTemplateLimitProcessor();
	}
}