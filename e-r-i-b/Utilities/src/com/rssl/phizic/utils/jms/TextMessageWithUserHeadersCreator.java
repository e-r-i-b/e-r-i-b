package com.rssl.phizic.utils.jms;

import com.rssl.phizic.common.types.transmiters.Pair;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author vagin
 * @ created 22.08.14
 * @ $Author$
 * @ $Revision$
 * Конструктор jms text-сообщений с возможностью добавление пользовательских заголовков
 */
public class TextMessageWithUserHeadersCreator extends TextMessageCreator
{
	private Map<String, String> userHeaders = new HashMap<String, String>();

	TextMessageWithUserHeadersCreator(Pair<String, String>... usersHeaders)
	{
		if (usersHeaders == null)
			return;

		for (Pair<String, String> header : usersHeaders)
		{
			if (header != null)
				this.userHeaders.put(header.getFirst(), header.getSecond());
		}
	}

	public static TextMessageWithUserHeadersCreator getInstance(Pair<String, String>... usersHeaders)
	{
		return new TextMessageWithUserHeadersCreator(usersHeaders);
	}

	public TextMessage create(Session session, Serializable object) throws JMSException
	{
		if (!(object instanceof String))
			throw new JMSException("Некорректный тип объекта " + object.getClass());

		TextMessage message = session.createTextMessage((String) object);
		for (String key : userHeaders.keySet())
		{
			message.setStringProperty(key, userHeaders.get(key));
		}

		return message;
	}
}
