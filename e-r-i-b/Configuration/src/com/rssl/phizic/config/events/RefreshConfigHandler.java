package com.rssl.phizic.config.events;

import com.rssl.phizic.events.EventHandler;
import com.rssl.phizic.config.ConfigFactory;

/**
 * ���������� ������� �� ���������� ��������.
 *
 * @author bogdanov
 * @ created 04.07.2013
 * @ $Author$
 * @ $Revision$
 */

public class RefreshConfigHandler implements EventHandler<RefreshPhizIcConfigEvent>
{
	public void process(RefreshPhizIcConfigEvent event) throws Exception
	{
		ConfigFactory.sendRefreshAllConfig();
	}
}
