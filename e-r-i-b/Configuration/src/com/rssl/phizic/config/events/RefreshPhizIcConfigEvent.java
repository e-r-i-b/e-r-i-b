package com.rssl.phizic.config.events;

import com.rssl.phizic.events.Event;

/**
 * ������� ���������� �������� ��������� ����������.
 *
 * @author bogdanov
 * @ created 04.07.2013
 * @ $Author$
 * @ $Revision$
 */

public class RefreshPhizIcConfigEvent implements Event
{
	public String getStringForLog()
	{
		return "RefreshPhizIcConfigEvent";
	}
}
