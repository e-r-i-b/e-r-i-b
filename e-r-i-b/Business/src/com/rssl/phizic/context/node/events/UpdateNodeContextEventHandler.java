package com.rssl.phizic.context.node.events;

import com.rssl.auth.csa.wsclient.NodeInfoConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.node.NodeContext;
import com.rssl.phizic.events.EventHandler;

/**
 * @author akrenev
 * @ created 19.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик события обновления контекста блока
 */

public class UpdateNodeContextEventHandler implements EventHandler<UpdateNodeContextEvent>
{
	public void process(UpdateNodeContextEvent event) throws Exception
	{
		switch (event.getType())
		{
			case STOP_CLIENT: NodeContext.setKickTime(event.getKickTime()); return;
			case REFRESH_CONFIG: ConfigFactory.getConfig(NodeInfoConfig.class).doRefresh(); return;
		}
	}
}
