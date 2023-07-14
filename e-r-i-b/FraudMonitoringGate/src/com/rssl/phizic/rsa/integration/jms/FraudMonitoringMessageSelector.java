package com.rssl.phizic.rsa.integration.jms;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.StringHelper;

/**
 * Селектор сообщений
 *
 * @author khudyakov
 * @ created 14.06.15
 * @ $Author$
 * @ $Revision$
 */
public class FraudMonitoringMessageSelector
{
	private String groupId;
	private String correlationId;

	FraudMonitoringMessageSelector(String _correlationId)
	{
		groupId = ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getGroupId();
		correlationId = _correlationId;
	}

	String getSelector()
	{
		String selector = String.format("JMSCorrelationID = '%s'", correlationId);
		if (StringHelper.isNotEmpty(groupId))
		{
			selector += " and JMSXGroupID = '" + groupId + "'";
		}
		return selector;
	}
}
