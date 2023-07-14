package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.longoffer.ExecutionEventType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author osminin
 * @ created 17.09.2010
 * @ $Author$
 * @ $Revision$
 * Раппер для получения соответствующего ExecutionEventType по коду события
 */
public class ExecutionEventTypeWrapper
{
	private static final Map<String, ExecutionEventType> executionEventTypes = new HashMap<String, ExecutionEventType>();

	static
	{
		executionEventTypes.put("BY_ANY_RECEIPT",   ExecutionEventType.BY_ANY_RECEIPT);
		executionEventTypes.put("BY_DEBIT",         ExecutionEventType.BY_DEBIT);
		executionEventTypes.put("BY_CAPITAL",       ExecutionEventType.BY_CAPITAL);
		executionEventTypes.put("BY_SALARY",        ExecutionEventType.BY_SALARY);
		executionEventTypes.put("BY_PENSION",       ExecutionEventType.BY_PENSION);
		executionEventTypes.put("BY_PERCENT",       ExecutionEventType.BY_PERCENT);
		executionEventTypes.put("ONCE_IN_MONTH",    ExecutionEventType.ONCE_IN_MONTH);
		executionEventTypes.put("ONCE_IN_QUARTER",  ExecutionEventType.ONCE_IN_QUARTER);
		executionEventTypes.put("ONCE_IN_HALFYEAR", ExecutionEventType.ONCE_IN_HALFYEAR);
		executionEventTypes.put("ONCE_IN_YEAR",     ExecutionEventType.ONCE_IN_YEAR);
		executionEventTypes.put("ON_OVER_DRAFT",    ExecutionEventType.ON_OVER_DRAFT);
		executionEventTypes.put("ON_REMAIND",       ExecutionEventType.ON_REMAIND);
		executionEventTypes.put("ONCE_IN_WEEK",     ExecutionEventType.ONCE_IN_WEEK);
	}

	public static ExecutionEventType getExecutionEventType(String exeEventCode)
	{
		return executionEventTypes.get(exeEventCode);
	}

	/**
	 * преобразовать енум ExecutionEventType в строку для передачи на шину
	 * @param executionEventType тип тип события
	 * @return значение для шины
	 */
	public static String toESBValue(ExecutionEventType executionEventType)
	{
		return executionEventType.name();
	}
}
