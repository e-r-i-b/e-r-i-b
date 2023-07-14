package com.rssl.phizicgate.sbrf.ws;

import com.rssl.phizic.gate.messaging.MessageHead;
import com.rssl.phizic.utils.StringHelper;

/**
 * Генератор сообщений для София-ВМС.
 *
 * @author bogdanov
 * @ created 03.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class SofiaMessageGenerator extends CODMessageGenerator
{
	@Override
	protected String getMessageId(MessageHead messageHead)
	{
		if (StringHelper.isNotEmpty(messageHead.getMessageId()))
		{
			return messageHead.getMessageId();
		}
		else
		{
			return super.getMessageId(messageHead);
		}
	}
}
