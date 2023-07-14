package com.rssl.phizic.utils.jms;

import java.io.Serializable;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * ����������� jms ���������
 * @author gladishev
 * @ created 30.10.13
 * @ $Author$
 * @ $Revision$
 */
public interface MessageCreator<T extends Message>
{
	/**
	 * ������� jms ���������
	 * @param session - ������
	 * @param object - ������������ ������
	 * @return jms ���������
	 */
	public T create(Session session, Serializable object) throws JMSException;
}
