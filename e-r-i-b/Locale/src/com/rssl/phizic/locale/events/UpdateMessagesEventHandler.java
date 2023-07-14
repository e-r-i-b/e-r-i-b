package com.rssl.phizic.locale.events;

import com.rssl.phizic.locale.ERIBMessageManager;
import com.rssl.phizic.events.EventHandler;

/**
 * Хендлер обновления текстовок
 * @author komarov
 * @ created 22.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class UpdateMessagesEventHandler implements EventHandler<UpdateMessagesEvent>
{
	public void process(UpdateMessagesEvent event) throws Exception
	{
		ERIBMessageManager.update(event);
	}
}
