package com.rssl.phizic.utils.jms;

import java.io.Serializable;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Конструктор jms сообщений
 * @author gladishev
 * @ created 30.10.13
 * @ $Author$
 * @ $Revision$
 */
public interface MessageCreator<T extends Message>
{
	/**
	 * Создать jms сообщение
	 * @param session - сессия
	 * @param object - отправляемый объект
	 * @return jms сообщение
	 */
	public T create(Session session, Serializable object) throws JMSException;
}
