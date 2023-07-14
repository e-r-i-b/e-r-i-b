package com.rssl.phizic.scheduler.jobs.messaging;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bogdanov
 * @ created 03.04.2015
 * @ $Author$
 * @ $Revision$
 */
public class MessageVariablesProvider implements com.rssl.phizic.messaging.mail.messagemaking.MessageVariablesProvider
{
	public Map getTemplateVariables()
	{
		return new HashMap();
	}
}
