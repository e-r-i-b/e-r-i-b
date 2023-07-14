package com.rssl.phizic.gate.reminder;

import com.rssl.phizic.gate.payments.template.ReminderInfo;
import com.rssl.phizic.gate.reminder.handlers.NullReminderTypeHandler;
import com.rssl.phizic.gate.reminder.handlers.OnceInMonthReminderHandler;
import com.rssl.phizic.gate.reminder.handlers.OnceInQuarterReminderHelper;
import com.rssl.phizic.gate.reminder.handlers.OnceReminderHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Фабрика обработчиков для напоминаний
 * @author niculichev
 * @ created 22.11.14
 * @ $Author$
 * @ $Revision$
 */
public class ReminderHandlerFactory
{
	private static final Map<ReminderType, ReminderTypeHandler> handlers = new HashMap<ReminderType, ReminderTypeHandler>();
	static
	{
		handlers.put(ReminderType.ONCE,             new OnceReminderHandler());
		handlers.put(ReminderType.ONCE_IN_MONTH,    new OnceInMonthReminderHandler());
		handlers.put(ReminderType.ONCE_IN_QUARTER,  new OnceInQuarterReminderHelper());
		handlers.put(null,                          new NullReminderTypeHandler());
	}

	/**
	 * Получить обработчик для напоминаний, подобных указанному
	 * @param info информация по напоминанию
	 * @return обработчик класса напоминаний
	 */
	public static ReminderTypeHandler getHandler(ReminderInfo info)
	{
		return handlers.get(info == null ? null : info.getType());
	}
}
