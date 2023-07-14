package com.rssl.phizic.logging.messaging;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.LoggingAccessor;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Акцессор вычисления необходимости логирования сообщений
 *
 * @author gladishev
 * @ created 20.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class MessagingLoggingAccessor implements LoggingAccessor
{
	public boolean needNormalModeLogging(Object... parameters)
	{
		MessageLogConfig messageLogConfig = ConfigFactory.getConfig(MessageLogConfig.class);
		if (!messageLogConfig.getLogState((System)parameters[0]))
			return false;

		if(parameters[1]==null)
			return true;
		List<Pattern> excludedMessages = messageLogConfig.getExcludedMessages();
		boolean excludedMessage = false;
		for (Pattern pattern : excludedMessages)
		{
			if (pattern.matcher((CharSequence)parameters[1]).matches())
			{
				excludedMessage = true;
				break;
			}
		}
		return !excludedMessage;
	}

	public boolean needExtendedModeLogging(Object... parameters)
	{
		MessageLogConfig messageLogConfig = ConfigFactory.getConfig(MessageLogConfig.class);
		return messageLogConfig.getExtendedLogState((System)parameters[0]);
	}
}
