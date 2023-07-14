package com.rssl.phizic.limits.handlers;

import com.rssl.phizic.common.types.limits.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author osminin
 * @ created 21.01.14
 * @ $Author$
 * @ $Revision$
 *
 * Маршрутизатор запросов
 */
public class RequestRouter
{
	private static final Map<String, TransactionProcessor> processors = new HashMap<String, TransactionProcessor>();

	static
	{
		processors.put(Constants.ADD_TRANSACTION_REQUEST_NAME,      new AddTransactionProcessor());
		processors.put(Constants.ROLLBACK_TRANSACTION_REQUEST_NAME, new RollbackTransactionProcessor());
		processors.put(Constants.SAVE_PERSON_SETTINGS_REQUEST_NAME, new SavePersonSettingsProcessor());
	}

	/**
	 * @param requestName наименование запроса
	 * @return процессор
	 */
	public static TransactionProcessor getProcessor(String requestName)
	{
		TransactionProcessor processor = processors.get(requestName);
		return processor == null ? new UnknownRequestProcessor() : processor;
	}
}
