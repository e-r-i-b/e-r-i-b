package com.rssl.phizic.gate.cache;

import com.rssl.phizic.events.Event;

/**
 * Событие очистки кэша записей ресширенного логирования.
 *
 * @author gladishev
 * @ created 21.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class ExtendedLogClearCacheEvent implements Event
{
	private Long loginId; //идентификатор логина

	/**
	 * ctor
	 * @param loginId - идентификатор логина
	 */
	public ExtendedLogClearCacheEvent(Long loginId)
	{
		this.loginId = loginId;
	}

	/**
	 * @return идентификатор логина
	 */
	public Long getLoginId()
	{
		return loginId;
	}

	public String getStringForLog()
	{
		StringBuilder result = new StringBuilder(getClass().getSimpleName());
		result.append(" login = ").append(loginId);
		return result.toString();
	}
}
