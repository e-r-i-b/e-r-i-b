package com.rssl.phizic.gate.monitoring.fraud.jms.senders;

import com.rssl.phizic.gate.monitoring.fraud.utils.ClientTransactionCompositeId;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.jms.MessageCreator;
import com.rssl.phizic.utils.jms.TextMessageCreator;

import java.io.Serializable;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Класс для формирования сообщений по ФМ
 *
 * @author khudyakov
 * @ created 04.07.15
 * @ $Author$
 * @ $Revision$
 */
public class FraudMonitoringMessageCreator implements MessageCreator<TextMessage>
{
	private String correlationId;

	public FraudMonitoringMessageCreator(String correlationId)
	{
		this.correlationId = correlationId;
	}

	public TextMessage create(Session session, Serializable object) throws JMSException
	{
		TextMessage message = TextMessageCreator.getInstance().create(session, object);
		appendGroupId(message);

		return message;
	}

	private void appendGroupId(TextMessage message) throws JMSException
	{
		String groupId = new ClientTransactionCompositeId(correlationId).getGroupId();
		if (StringHelper.isNotEmpty(groupId))
		{
			message.setStringProperty("JMSXGroupID", groupId);
			message.setBooleanProperty("JMS_IBM_Last_Msg_In_Group", true);
		}
	}
}
