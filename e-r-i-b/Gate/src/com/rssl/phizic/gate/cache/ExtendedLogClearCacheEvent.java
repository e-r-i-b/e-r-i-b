package com.rssl.phizic.gate.cache;

import com.rssl.phizic.events.Event;

/**
 * ������� ������� ���� ������� ������������ �����������.
 *
 * @author gladishev
 * @ created 21.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class ExtendedLogClearCacheEvent implements Event
{
	private Long loginId; //������������� ������

	/**
	 * ctor
	 * @param loginId - ������������� ������
	 */
	public ExtendedLogClearCacheEvent(Long loginId)
	{
		this.loginId = loginId;
	}

	/**
	 * @return ������������� ������
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
