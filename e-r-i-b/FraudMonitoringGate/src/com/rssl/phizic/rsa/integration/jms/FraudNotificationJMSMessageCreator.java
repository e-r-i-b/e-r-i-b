package com.rssl.phizic.rsa.integration.jms;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.jms.TextMessageCreator;

import java.io.Serializable;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author tisov
 * @ created 16.07.15
 * @ $Author$
 * @ $Revision$
 * Сущность, создающая JMS-сообщения с оповещением во фрод-мониторинг
 */
public class FraudNotificationJMSMessageCreator extends TextMessageCreator
{
	public TextMessage create(Session session, Serializable object) throws JMSException
	{
		TextMessage message = super.create(session, object);
		setGroupId(message);

		return message;
	}

	private void setGroupId(TextMessage message) throws JMSException
	{
		String groupId = ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getGroupId();
		if (StringHelper.isNotEmpty(groupId))
		{
			message.setStringProperty("JMSXGroupID", groupId);
			message.setBooleanProperty("JMS_IBM_Last_Msg_In_Group", true);
		}
	}
}
