package com.rssl.phizic.context.node;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 19.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Контекст текущего блока
 */

public class NodeContext
{
	private static volatile Calendar kickTime = null;

	/**
	 * задать время для остановки работы клиентов
	 * @param kickTime время
	 */
	public static void setKickTime(Calendar kickTime)
	{
		NodeContext.kickTime = kickTime;
	}

	/**
	 * @return время для остановки работы клиентов
	 */
	public static Calendar getKickTime()
	{
		return kickTime;
	}
}
